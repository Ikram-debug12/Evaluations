package ma.projet.services;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    @Override
    public boolean create(Femme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Femme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Femme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Femme findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Femme femme = session.get(Femme.class, id);
        session.close();
        return femme;
    }

    @Override
    public List<Femme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Femme> femmes = session.createQuery("from Femme", Femme.class).list();
        session.close();
        return femmes;
    }
    public List<Femme> findByNom(String nom) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createQuery("from Femme where nom = :nom", Femme.class);
            query.setParameter("nom", nom);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode pour le nombre d'enfants d'une femme entre deux dates (requête native)
    public int getNombreEnfantsEntreDates(int femmeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT SUM(m.nombreEnfants) FROM Mariage m " +
                    "WHERE m.femme.id = :femmeId " +
                    "AND m.dateDebut BETWEEN :debut AND :fin";

            Query query = session.createQuery(hql);
            query.setParameter("femmeId", femmeId);
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);

            Long result = (Long) query.getSingleResult();
            return result != null ? result.intValue() : 0;

        } catch (NoResultException e) {
            return 0;
        } finally {
            session.close();
        }
    }

    // Méthode pour les femmes mariées au moins deux fois (requête nommée)
    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Femme> query = session.createNamedQuery("Femme.marieeAuMoinsDeuxFois", Femme.class);
        List<Femme> femmes = query.list();
        session.close();
        return femmes;
    }

    // Méthode avec Criteria API pour afficher les hommes mariés à 4 femmes entre deux dates
    public void afficherHommesMarieesA4Femmes(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
        Root<Mariage> root = cq.from(Mariage.class);

        cq.multiselect(root.get("homme"), cb.count(root))
                .where(cb.between(root.get("dateDebut"), dateDebut, dateFin))
                .groupBy(root.get("homme"))
                .having(cb.equal(cb.count(root), 4));

        List<Object[]> results = session.createQuery(cq).getResultList();

        System.out.println("Hommes mariés à 4 femmes entre " + dateDebut + " et " + dateFin + " :");
        for (Object[] result : results) {
            Homme homme = (Homme) result[0];
            Long count = (Long) result[1];
            System.out.println("- " + homme.getPrenom() + " " + homme.getNom());
        }
        session.close();
    }
}

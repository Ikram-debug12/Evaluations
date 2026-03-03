package ma.projet.services;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override
    public boolean create(Homme o) {
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
    public boolean update(Homme o) {
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
    public boolean delete(Homme o) {
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
    public Homme findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Homme homme = session.get(Homme.class, id);
        session.close();
        return homme;
    }

    @Override
    public List<Homme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Homme> hommes = session.createQuery("from Homme", Homme.class).list();
        session.close();
        return hommes;
    }

    public List<Homme> findByNom(String nom) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Homme> query = session.createQuery("from Homme where nom = :nom", Homme.class);
            query.setParameter("nom", nom);
            return query.list();
        } finally {
            session.close();
        }
    }

    public Homme findByPrenomAndNom(String prenom, String nom) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Homme> query = session.createQuery(
                    "from Homme where prenom = :prenom and nom = :nom", Homme.class);
            query.setParameter("prenom", prenom);
            query.setParameter("nom", nom);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }

    // Méthode pour afficher les épouses d'un homme entre deux dates
    public void afficherEpousesEntreDates(int hommeId, Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Homme homme = session.get(Homme.class, hommeId);
        try {
            String hql = "select m from Mariage m where m.homme.id = :hommeId " +
                    "and m.dateDebut between :debut and :fin";
            Query<Mariage> query = session.createQuery(hql, Mariage.class);
            query.setParameter("hommeId", hommeId);
            query.setParameter("debut", dateDebut);
            query.setParameter("fin", dateFin);

            List<Mariage> mariages = query.list();

            System.out.println("Épouses de " + homme.getPrenom() + " " + homme.getNom() +
                    " entre " + dateDebut + " et " + dateFin + " :");
            for (Mariage m : mariages) {
                System.out.println("- " + m.getFemme().getPrenom() + " " + m.getFemme().getNom());
            }
        } finally {
            session.close();
        }
    }
}






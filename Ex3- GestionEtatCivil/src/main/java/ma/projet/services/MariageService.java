package ma.projet.services;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.List;

public class MariageService implements IDao<Mariage> {

    @Override
    public boolean create(Mariage o) {
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
    public boolean update(Mariage o) {
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
    public boolean delete(Mariage o) {
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
    public Mariage findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Mariage mariage = session.get(Mariage.class, id);
        session.close();
        return mariage;
    }

    @Override
    public List<Mariage> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Mariage> mariages = session.createQuery("from Mariage", Mariage.class).list();
        session.close();
        return mariages;
    }

    public List<Mariage> getMariagesByFemme(int femmeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Mariage> query = session.createQuery(
                    "from Mariage where femme.id = :femmeId", Mariage.class);
            query.setParameter("femmeId", femmeId);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode pour afficher les mariages d'un homme avec détails
    public void afficherMariagesHomme(int hommeId) {
        HommeService hommeService = new HommeService();
        Homme homme = hommeService.findById(hommeId);

        if (homme == null) {
            System.out.println("Homme non trouvé");
            return;
        }

        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Mariage> mariages = session.createQuery(
                        "from Mariage where homme.id = :hommeId", Mariage.class)
                .setParameter("hommeId", hommeId)
                .list();
        session.close();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println("\nNom : " + homme.getNom().toUpperCase() + " " + homme.getPrenom());

        // Mariages en cours
        System.out.println("\nMariages En Cours :");
        int index = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() == null) {
                System.out.println(index++ + ". Femme : " + m.getFemme().getPrenom() + " " +
                        m.getFemme().getNom() + "   Date Début : " +
                        sdf.format(m.getDateDebut()) + "    Nbr Enfants : " +
                        m.getNombreEnfants());
            }
        }

        // Mariages échoués
        System.out.println("\nMariages échoués :");
        index = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() != null) {
                System.out.println(index++ + ". Femme : " + m.getFemme().getPrenom() + " " +
                        m.getFemme().getNom() + "  Date Début : " +
                        sdf.format(m.getDateDebut()) + "    Date Fin : " +
                        sdf.format(m.getDateFin()) + "    Nbr Enfants : " +
                        m.getNombreEnfants());
            }
        }
    }
}

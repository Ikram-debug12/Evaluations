package ma.projet.services;

import ma.projet.classes.EmployeTache;
import ma.projet.classes.EmployeTachePK;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import ma.projet.util.HibernateUtil;


public class EmployeTacheService extends AbstractFacade<EmployeTache> {
    public EmployeTacheService() {
        super(EmployeTache.class);
    }

    public EmployeTache findById(EmployeTachePK id) {
        Session session = null;
        Transaction tx = null;
        EmployeTache obj = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            // Utilisation de l'ID composite directement
            obj = session.get(EmployeTache.class, id);

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return obj;
    }

    // Méthode utilitaire pour créer un ID composite facilement
    public EmployeTachePK createId(int employeId, int tacheId) {
        return new EmployeTachePK(employeId, tacheId);
    }

    // Méthode pour supprimer par clé composite
    public boolean deleteById(int employeId, int tacheId) {
        EmployeTachePK id = new EmployeTachePK(employeId, tacheId);
        EmployeTache et = findById(id);
        if (et != null) {
            return delete(et);
        }
        return false;
    }
}



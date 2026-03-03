package ma.projet.service;

import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class CommandeService implements IDao<Commande> {

    @Override
    public boolean create(Commande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Commande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Commande o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Commande getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Commande commande = (Commande) session.get(Commande.class, id);
        session.close();
        return commande;
    }

    @Override
    public List<Commande> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Commande> commandes = session.createQuery("from Commande").list();
        session.close();
        return commandes;
    }
}

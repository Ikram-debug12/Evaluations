package ma.projet.service;

import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @Override
    public boolean create(LigneCommandeProduit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(LigneCommandeProduit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(LigneCommandeProduit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public LigneCommandeProduit getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        LigneCommandeProduit ligne = (LigneCommandeProduit) session.get(LigneCommandeProduit.class, id);
        session.close();
        return ligne;
    }

    @Override
    public List<LigneCommandeProduit> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<LigneCommandeProduit> lignes = session.createQuery("from LigneCommandeProduit").list();
        session.close();
        return lignes;
    }
}

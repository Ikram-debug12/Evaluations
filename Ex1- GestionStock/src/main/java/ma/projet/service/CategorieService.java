package ma.projet.service;

import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import java.util.List;

public class CategorieService implements IDao<Categorie> {

    @Override
    public boolean create(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Categorie o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Categorie getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Categorie categorie = (Categorie) session.get(Categorie.class, id);
        session.close();
        return categorie;
    }

    @Override
    public List<Categorie> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Categorie> categories = session.createQuery("from Categorie").list();
        session.close();
        return categories;
    }
}

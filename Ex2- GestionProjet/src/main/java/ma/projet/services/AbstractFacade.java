package ma.projet.services;

import java.util.List;
import java.util.Date;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;  // ← IMPORT MANQUANT !

public abstract class AbstractFacade<T> implements IDao<T> {

    private final Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public boolean create(T o) {
        Session session = null;
        Transaction tx = null;
        boolean etat = false;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            etat = true;
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
        return etat;
    }

    @Override
    public boolean delete(T o) {
        Session session = null;
        Transaction tx = null;
        boolean etat = false;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            etat = true;
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
        return etat;
    }

    @Override
    public boolean update(T o) {
        Session session = null;
        Transaction tx = null;
        boolean etat = false;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            etat = true;
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
        return etat;
    }

    @Override
    public T findById(int id) {
        Session session = null;
        Transaction tx = null;
        T obj = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            obj = session.get(entityClass, id);
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

    @Override
    public List<T> findAll() {
        Session session = null;
        Transaction tx = null;
        List<T> list = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            list = session.createQuery("from " + entityClass.getSimpleName(), entityClass).list();
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
        return list;
    }

    // ===== MÉTHODES SPÉCIFIQUES =====

    // Pour ProjetService
    public List<Object[]> getTachesRealiseesAvecDatesReelles(int projetId) {
        Session session = null;
        Transaction tx = null;
        List<Object[]> result = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query<Object[]> query = session.createQuery(
                    "select t.id, t.nom, et.dateDebutReelle, et.dateFinReelle " +
                            "from Tache t " +
                            "left join t.employeTaches et " +
                            "where t.projet.id = :projetId", Object[].class);
            query.setParameter("projetId", projetId);
            result = query.list();

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
        return result;
    }

    public List<Object[]> getTachesPlanifieesParProjet(int projetId) {
        Session session = null;
        Transaction tx = null;
        List<Object[]> result = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query<Object[]> query = session.createQuery(
                    "select t.id, t.nom, t.dateDebut, t.dateFin " +
                            "from Tache t where t.projet.id = :projetId", Object[].class);
            query.setParameter("projetId", projetId);
            result = query.list();

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
        return result;
    }

    // Pour EmployeService
    public List<Object[]> getTachesRealiseesParEmploye(int employeId) {
        Session session = null;
        Transaction tx = null;
        List<Object[]> result = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query<Object[]> query = session.createQuery(
                    "select p.id, p.nom, p.DateDebut, p.DateFin " +
                            "from Projet p where p.employe.id = :employeId", Object[].class);
            query.setParameter("employeId", employeId);
            result = query.list();

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
        return result;
    }

    public List<Object[]> getProjetsGeresParEmploye(int employeId) {
        Session session = null;
        Transaction tx = null;
        List<Object[]> result = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query<Object[]> query = session.createQuery(
                    "select p.id, p.nom, p.DateDebut, p.DateFin " +
                            "from Projet p where p.employe.id = :employeId", Object[].class);
            query.setParameter("employeId", employeId);
            result = query.list();

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
        return result;
    }

    // Pour TacheService - Attention: retourne List<T> (donc List<Tache>)
    public List<T> getTachesPrixSuperieur1000() {
        Session session = null;
        Transaction tx = null;
        List<T> result = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query<T> query = session.createNamedQuery("findTachesPrixSuperieur1000", entityClass);
            result = query.list();

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
        return result;
    }

    public List<Object[]> getTachesRealiseesEntreDates(Date dateDebut, Date dateFin) {
        Session session = null;
        Transaction tx = null;
        List<Object[]> result = null;

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();

            Query<Object[]> query = session.createQuery(
                    "select t.id, t.nom from EmployeTache et join et.tache t " +
                            "where et.dateDebutReelle >= :dateDebut and et.dateFinReelle <= :dateFin",
                    Object[].class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            result = query.list();

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
        return result;
    }
}


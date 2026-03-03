package ma.projet.service;

import ma.projet.classes.*;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Query;
import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(o);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public Produit getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Produit produit = (Produit) session.get(Produit.class, id);
        session.close();
        return produit;
    }

    @Override
    public List<Produit> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createQuery("from Produit").list();
        session.close();
        return produits;
    }

    // Méthode 1: Produits par catégorie
    public List<Produit> getProduitsByCategorie(int categorieId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery("from Produit where categorie.id = :categorieId");
        query.setParameter("categorieId", categorieId);
        List<Produit> produits = query.list();
        session.close();
        return produits;
    }

    // Méthode 2: Produits commandés entre deux dates
    public List<Produit> getProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(
                "select distinct l.produit from LigneCommandeProduit l " +
                        "where l.commande.date between :dateDebut and :dateFin");
        query.setParameter("dateDebut", dateDebut);
        query.setParameter("dateFin", dateFin);
        List<Produit> produits = query.list();
        session.close();
        return produits;
    }

    // Méthode 3: Afficher les produits d'une commande (format demandé)
    public void afficherProduitsParCommande(int commandeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.createQuery(
                "select l from LigneCommandeProduit l " +
                        "join fetch l.produit where l.commande.id = :commandeId");
        query.setParameter("commandeId", commandeId);
        List<LigneCommandeProduit> lignes = query.list();

        if (!lignes.isEmpty()) {
            Commande commande = lignes.get(0).getCommande();
            System.out.println("Commande : " + commande.getId() +
                    "\t Date : " + commande.getDate());
            System.out.println("Liste des produits :");
            System.out.println("Référence\tPrix\tQuantité");

            for (LigneCommandeProduit l : lignes) {
                System.out.println(l.getProduit().getReference() + "\t\t" +
                        l.getProduit().getPrix() + " DH\t" + l.getQuantite());
            }
        }
        session.close();
    }

    // Méthode 4: Produits avec prix > 100 (requête nommée)
    public List<Produit> getProduitsPrixSuperieur100() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query query = session.getNamedQuery("produit.prixSuperieur100");
        List<Produit> produits = query.list();
        session.close();
        return produits;
    }
}

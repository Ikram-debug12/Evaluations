package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            // Création des services
            CategorieService categorieService = new CategorieService();
            ProduitService produitService = new ProduitService();
            CommandeService commandeService = new CommandeService();
            LigneCommandeService ligneService = new LigneCommandeService();

            // 1. Créer une catégorie
            System.out.println("=== Création d'une catégorie ===");
            Categorie info = new Categorie("INFO", "Informatique");
            categorieService.create(info);
            System.out.println("Catégorie créée avec ID: " + info.getId());

            // 2. Créer des produits
            System.out.println("\n=== Création de produits ===");
            Produit p1 = new Produit("ES12", 120, info);
            Produit p2 = new Produit("ZR85", 100, info);
            Produit p3 = new Produit("EE85", 200, info);

            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);
            System.out.println("3 produits créés");

            // 3. Créer une commande
            System.out.println("\n=== Création d'une commande ===");
            Commande cmd = new Commande(new Date());
            commandeService.create(cmd);
            System.out.println("Commande créée avec ID: " + cmd.getId());

            // 4. Ajouter des lignes de commande
            System.out.println("\n=== Ajout des lignes de commande ===");
            LigneCommandeProduit l1 = new LigneCommandeProduit(7, cmd, p1);
            LigneCommandeProduit l2 = new LigneCommandeProduit(14, cmd, p2);
            LigneCommandeProduit l3 = new LigneCommandeProduit(5, cmd, p3);

            ligneService.create(l1);
            ligneService.create(l2);
            ligneService.create(l3);
            System.out.println("3 lignes de commande ajoutées");

            // 5. TEST 1: Afficher les produits de la commande (format demandé)
            System.out.println("\n=== TEST 1: Affichage commande ===");
            produitService.afficherProduitsParCommande(cmd.getId());

            // 6. TEST 2: Produits par catégorie
            System.out.println("\n=== TEST 2: Produits de la catégorie Informatique ===");
            List<Produit> produits = produitService.getProduitsByCategorie(info.getId());
            for (Produit p : produits) {
                System.out.println(p.getReference() + " - " + p.getPrix() + " DH");
            }

            // 7. TEST 3: Produits avec prix > 100 DH (requête nommée)
            System.out.println("\n=== TEST 3: Produits avec prix > 100 DH ===");
            List<Produit> produitsChers = produitService.getProduitsPrixSuperieur100();
            for (Produit p : produitsChers) {
                System.out.println(p.getReference() + " - " + p.getPrix() + " DH");
            }

            System.out.println("\n=== Tous les tests sont terminés avec succès ===");

        } catch (Exception e) {
            System.err.println("Erreur lors des tests: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
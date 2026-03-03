package com.example;

import ma.projet.beans.*;
import ma.projet.services.*;
import ma.projet.util.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Création des services
            HommeService hommeService = new HommeService();
            FemmeService femmeService = new FemmeService();
            MariageService mariageService = new MariageService();

            System.out.println("=== DÉBUT DU TEST DE L'APPLICATION D'ÉTAT CIVIL ===\n");

            // 1. Créer 10 femmes et 5 hommes
            System.out.println("Étape 1: Création de 10 femmes et 5 hommes");
            System.out.println("-------------------------------------------");

            // Création des femmes
            Femme[] femmes = new Femme[10];
            String[] nomsFemmes = {"ALAMI", "RAMI", "BENANI", "CHAHID", "NAJI",
                    "FASSI", "ALAOUI", "BENJELLOUN", "TOUIMI", "ZIANI"};
            String[] prenomsFemmes = {"KARIMA", "SALIMA", "FATIMA", "NADIA", "SAMIRA",
                    "AMAL", "WAFA", "HIND", "LEILA", "SOUMIA"};

            for (int i = 0; i < 10; i++) {
                femmes[i] = new Femme(
                        nomsFemmes[i],
                        prenomsFemmes[i],
                        "06000000" + (i+10),
                        "Adresse Femme " + (i+1),
                        sdf.parse("01/0" + ((i%9)+1) + "/198" + (i+5)) // Dates variées
                );
                femmeService.create(femmes[i]);
                System.out.println("Femme créée: " + femmes[i].getPrenom() + " " + femmes[i].getNom());
            }

            // Création des hommes
            Homme[] hommes = new Homme[5];
            String[] nomsHommes = {"SAFI", "BENNANI", "CHERKAOUI", "TAZI", "BERRADA"};
            String[] prenomsHommes = {"SAID", "MOHAMED", "HASSAN", "RACHID", "KHALID"};

            for (int i = 0; i < 5; i++) {
                hommes[i] = new Homme(
                        nomsHommes[i],
                        prenomsHommes[i],
                        "07000000" + (i+20),
                        "Adresse Homme " + (i+1),
                        sdf.parse("15/0" + ((i%9)+1) + "/197" + (i+5))
                );
                hommeService.create(hommes[i]);
                System.out.println("Homme créé: " + hommes[i].getPrenom() + " " + hommes[i].getNom());
            }

            // 2. Créer des mariages pour les tests
            System.out.println("\nÉtape 2: Création des mariages pour les tests");
            System.out.println("----------------------------------------------");

            // Mariages pour SAFI SAID (homme 1) - exemple donné dans l'énoncé
            Mariage m1 = new Mariage(
                    hommes[0], femmes[1],  // SAFI SAID avec SALIMA RAMI
                    sdf.parse("03/09/1990"),
                    null,
                    4
            );
            mariageService.create(m1);
            System.out.println("Mariage: " + hommes[0].getPrenom() + " " + hommes[0].getNom() +
                    " + " + femmes[1].getPrenom() + " " + femmes[1].getNom() +
                    " (en cours, 4 enfants)");

            Mariage m2 = new Mariage(
                    hommes[0], femmes[5],  // SAFI SAID avec AMAL ALI (FASSI)
                    sdf.parse("03/09/1995"),
                    null,
                    2
            );
            mariageService.create(m2);
            System.out.println("Mariage: " + hommes[0].getPrenom() + " " + hommes[0].getNom() +
                    " + " + femmes[5].getPrenom() + " " + femmes[5].getNom() +
                    " (en cours, 2 enfants)");

            Mariage m3 = new Mariage(
                    hommes[0], femmes[6],  // SAFI SAID avec WAFA ALAOUI
                    sdf.parse("04/11/2000"),
                    null,
                    3
            );
            mariageService.create(m3);
            System.out.println("Mariage: " + hommes[0].getPrenom() + " " + hommes[0].getNom() +
                    " + " + femmes[6].getPrenom() + " " + femmes[6].getNom() +
                    " (en cours, 3 enfants)");

            // Mariage échoué pour SAFI SAID
            Mariage m4 = new Mariage(
                    hommes[0], femmes[0],  // SAFI SAID avec KARIMA ALAMI
                    sdf.parse("03/09/1989"),
                    sdf.parse("03/09/1990"),
                    0
            );
            mariageService.create(m4);
            System.out.println("Mariage: " + hommes[0].getPrenom() + " " + hommes[0].getNom() +
                    " + " + femmes[0].getPrenom() + " " + femmes[0].getNom() +
                    " (échoué, 0 enfants)");

            // Autres mariages pour les tests
            // Homme 2 avec plusieurs femmes (pour test des 4 mariages)
            for (int i = 2; i < 6; i++) {
                Mariage m = new Mariage(
                        hommes[1], femmes[i],
                        sdf.parse("01/01/200" + i),
                        (i == 3 ? sdf.parse("01/01/2005") : null),
                        i+1
                );
                mariageService.create(m);
                System.out.println("Mariage: " + hommes[1].getPrenom() + " " + hommes[1].getNom() +
                        " + " + femmes[i].getPrenom() + " " + femmes[i].getNom());
            }

            // Mariages supplémentaires pour que certaines femmes soient mariées plusieurs fois
            Mariage m5 = new Mariage(
                    hommes[2], femmes[0],  // KARIMA ALAMI avec un autre homme
                    sdf.parse("01/01/2000"),
                    sdf.parse("31/12/2005"),
                    2
            );
            mariageService.create(m5);

            Mariage m6 = new Mariage(
                    hommes[3], femmes[1],  // SALIMA RAMI avec un autre homme
                    sdf.parse("15/03/2001"),
                    sdf.parse("20/10/2004"),
                    1
            );
            mariageService.create(m6);

            System.out.println("\nToutes les données de test ont été créées avec succès!\n");

            // 3. Afficher la liste des femmes
            System.out.println("=== TEST 1: LISTE DES FEMMES ===");
            List<Femme> toutesFemmes = femmeService.findAll();
            for (Femme f : toutesFemmes) {
                System.out.println("ID: " + f.getId() + " | " + f.getPrenom() + " " +
                        f.getNom() + " | Née le: " + sdf.format(f.getDateNaissance()));
            }
            System.out.println();

            // 4. Afficher la femme la plus âgée
            System.out.println("=== TEST 2: FEMME LA PLUS ÂGÉE ===");
            Femme plusAgee = null;
            Date dateMin = new Date();
            for (Femme f : toutesFemmes) {
                if (f.getDateNaissance().before(dateMin)) {
                    dateMin = f.getDateNaissance();
                    plusAgee = f;
                }
            }
            if (plusAgee != null) {
                System.out.println(plusAgee.getPrenom() + " " + plusAgee.getNom() +
                        " | Née le: " + sdf.format(plusAgee.getDateNaissance()));
            }
            System.out.println();

            // 5. Afficher les épouses d'un homme donné (SAFI SAID - ID à récupérer)
            System.out.println("=== TEST 3: ÉPOUSES D'UN HOMME DONNÉ ===");
            Homme hommeTest = hommeService.findByNom("SAFI").get(0); // Récupère SAFI SAID
            System.out.println("Homme: " + hommeTest.getPrenom() + " " + hommeTest.getNom());

            Date dateDebut = sdf.parse("01/01/1980");
            Date dateFin = sdf.parse("31/12/2025");
            hommeService.afficherEpousesEntreDates(hommeTest.getId(), dateDebut, dateFin);
            System.out.println();

            // 6. Afficher le nombre d'enfants d'une femme entre deux dates
            System.out.println("=== TEST 4: NOMBRE D'ENFANTS D'UNE FEMME ENTRE DEUX DATES ===");
            Femme femmeTest = femmeService.findByNom("RAMI").get(0); // Récupère SALIMA RAMI
            System.out.println("Femme: " + femmeTest.getPrenom() + " " + femmeTest.getNom());

            int nbrEnfants = femmeService.getNombreEnfantsEntreDates(
                    femmeTest.getId(),
                    sdf.parse("01/01/1990"),
                    sdf.parse("31/12/2000")
            );
            System.out.println("Nombre d'enfants entre 1990 et 2000: " + nbrEnfants);
            System.out.println();

            // 7. Afficher les femmes mariées deux fois ou plus
            System.out.println("=== TEST 5: FEMMES MARIÉES AU MOINS DEUX FOIS ===");
            List<Femme> femmesMariees2Plus = femmeService.getFemmesMarieesAuMoinsDeuxFois();
            if (femmesMariees2Plus.isEmpty()) {
                System.out.println("Aucune femme mariée deux fois ou plus");
            } else {
                for (Femme f : femmesMariees2Plus) {
                    List<Mariage> mariagesFemme = mariageService.getMariagesByFemme(f.getId());
                    System.out.println("- " + f.getPrenom() + " " + f.getNom() +
                            " (" + mariagesFemme.size() + " mariages)");
                }
            }
            System.out.println();

            // 8. Afficher les hommes mariés à quatre femmes entre deux dates
            System.out.println("=== TEST 6: HOMMES MARIÉS À 4 FEMMES ENTRE DEUX DATES ===");
            femmeService.afficherHommesMarieesA4Femmes(
                    sdf.parse("01/01/1990"),
                    sdf.parse("31/12/2010")
            );
            System.out.println();

            // 9. Afficher les mariages d'un homme avec tous les détails (comme dans l'exemple)
            System.out.println("=== TEST 7: MARIAGES D'UN HOMME AVEC DÉTAILS ===");
            System.out.println("(Format exact demandé dans l'énoncé)\n");
            mariageService.afficherMariagesHomme(hommeTest.getId());

            System.out.println("\n=== FIN DES TESTS - TOUTES LES FONCTIONNALITÉS ONT ÉTÉ EXÉCUTÉES AVEC SUCCÈS ===");

        } catch (Exception e) {
            System.err.println("ERREUR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
            scanner.close();
        }
    }
}

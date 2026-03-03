package com.example;


import ma.projet.util.HibernateUtil;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import ma.projet.classes.*;
import ma.projet.services.*;

import java.text.SimpleDateFormat;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("\n=== DÉBUT DES TESTS =================================\n");

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // ===== 1. CRÉATION DES SERVICES =====
            System.out.println("Création des services...");
            EmployeService employeService = new EmployeService();
            ProjetService projetService = new ProjetService();
            TacheService tacheService = new TacheService();
            EmployeTacheService employeTacheService = new EmployeTacheService();
            System.out.println("Services créés avec succès\n");

            // ===== 2. CRÉATION DES EMPLOYÉS =====
            System.out.println("Création des employés...");

            Employe chef = new Employe();
            chef.setNom("El Alami");
            chef.setPrenom("Ahmed");
            chef.setTelephone("0612345678");
            employeService.create(chef);
            System.out.println("   ✓ Chef créé : " + chef.getPrenom() + " " + chef.getNom() + " (ID: " + chef.getId() + ")");

            Employe emp1 = new Employe();
            emp1.setNom("Benani");
            emp1.setPrenom("Fatima");
            emp1.setTelephone("0623456789");
            emp1.setChefDeProjet(chef);
            employeService.create(emp1);
            System.out.println("   ✓ Employé créé : " + emp1.getPrenom() + " " + emp1.getNom() + " (ID: " + emp1.getId() + ")");

            Employe emp2 = new Employe("Rami", "Youssef", "0634567890");
            emp2.setChefDeProjet(chef);
            employeService.create(emp2);
            System.out.println("   ✓ Employé créé : " + emp2.getPrenom() + " " + emp2.getNom() + " (ID: " + emp2.getId() + ")\n");

            // ===== 3. CRÉATION D'UN PROJET =====
            System.out.println("Création d'un projet...");

            Projet projet = new Projet("Gestion de stock",
                    sdf.parse("2013-01-14"),
                    sdf.parse("2013-05-30"),
                    chef);
            projetService.create(projet);
            System.out.println("   ✓ Projet créé : " + projet.getNom() + " (ID: " + projet.getId() + ")\n");

            // ===== 4. CRÉATION DES TÂCHES =====
            System.out.println("Création des tâches...");

            Tache tache1 = new Tache("Analyse",
                    sdf.parse("2013-01-14"),
                    sdf.parse("2013-02-20"),
                    1500, projet);
            tacheService.create(tache1);
            System.out.println("   ✓ Tâche créée : " + tache1.getNom() + " (ID: " + tache1.getId() + ")");

            Tache tache2 = new Tache("Conception",
                    sdf.parse("2013-02-21"),
                    sdf.parse("2013-03-15"),
                    2000, projet);
            tacheService.create(tache2);
            System.out.println("   ✓ Tâche créée : " + tache2.getNom() + " (ID: " + tache2.getId() + ")");

            Tache tache3 = new Tache("Développement",
                    sdf.parse("2013-03-16"),
                    sdf.parse("2013-04-25"),
                    3000, projet);
            tacheService.create(tache3);
            System.out.println("   ✓ Tâche créée : " + tache3.getNom() + " (ID: " + tache3.getId() + ")\n");

            // ===== 5. ASSIGNATION DES TÂCHES AUX EMPLOYÉS =====
            System.out.println(" Assignation des tâches...");

            EmployeTache et1 = new EmployeTache(emp1, tache1,
                    sdf.parse("2013-01-14"), sdf.parse("2013-02-20"));
            employeTacheService.create(et1);

            EmployeTache et2 = new EmployeTache(emp1, tache2,
                    sdf.parse("2013-02-21"), sdf.parse("2013-03-15"));
            employeTacheService.create(et2);

            EmployeTache et3 = new EmployeTache(emp2, tache3,
                    sdf.parse("2013-03-16"), sdf.parse("2013-04-25"));
            employeTacheService.create(et3);

            System.out.println("   ✓ 3 tâches assignées avec succès\n");

            // ===== 6. TEST 1: AFFICHAGE D'UN PROJET AVEC SES TÂCHES =====
            System.out.println(" TEST 1: Affichage du projet avec ses tâches");
            System.out.println("--------------------------------------------------");

            SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("Projet : " + projet.getId() + "\t Nom : " + projet.getNom() +
                    "\t Date début : " + displayFormat.format(projet.getDateDebut()));
            System.out.println("Liste des tâches:");
            System.out.println("Num\tNom\t\tDate Début Réelle\tDate Fin Réelle");

            List<Object[]> tachesReelles = projetService.getTachesRealiseesAvecDatesReelles(projet.getId());
            if (tachesReelles != null) {
                for (Object[] row : tachesReelles) {
                    String dateDebutStr = (row[2] != null) ? displayFormat.format(row[2]) : "N/A";
                    String dateFinStr = (row[3] != null) ? displayFormat.format(row[3]) : "N/A";

                    System.out.println(row[0] + "\t" + row[1] + "\t\t" +
                            dateDebutStr + "\t\t" + dateFinStr);
                }
            }

            // ===== 7. TEST 2: TÂCHES RÉALISÉES PAR UN EMPLOYÉ =====
            System.out.println(" TEST 2: Tâches réalisées par " + emp1.getPrenom() + " " + emp1.getNom());
            System.out.println("--------------------------------------------------");

            List<Object[]> tachesEmp1 = employeService.getTachesRealiseesParEmploye(emp1.getId());
            for (Object[] row : tachesEmp1) {
                System.out.println("   • Tâche " + row[0] + ": " + row[1] +
                        " du " + displayFormat.format(row[2]) + " au " + displayFormat.format(row[3]));
            }
            System.out.println();

            // ===== 8. TEST 3: PROJETS GÉRÉS PAR UN EMPLOYÉ =====
            System.out.println(" TEST 3: Projets gérés par " + chef.getPrenom() + " " + chef.getNom());
            System.out.println("--------------------------------------------------");

            List<Object[]> projetsChef = employeService.getProjetsGeresParEmploye(chef.getId());
            for (Object[] p : projetsChef) {
                System.out.println("   • Projet " + p[0] + ": " + p[1]);  // p[0]=id, p[1]=nom
            }

            // ===== 9. TEST 4: TÂCHES AVEC PRIX > 1000 DH =====
            System.out.println(" TEST 4: Tâches avec prix > 1000 DH");
            System.out.println("--------------------------------------------------");

            List<Tache> tachesCheres = tacheService.getTachesPrixSuperieur1000();
            for (Tache t : tachesCheres) {
                System.out.println("   • " + t.getNom() + " - " + t.getPrix() + " DH");
            }
            System.out.println();

            // ===== 10. TEST 5: TÂCHES RÉALISÉES ENTRE DEUX DATES =====
            System.out.println(" TEST 5: Tâches réalisées entre le 01/02/2013 et le 31/03/2013");
            System.out.println("--------------------------------------------------");

            List<Object[]> tachesPeriode = tacheService.getTachesRealiseesEntreDates(
                    sdf.parse("2013-02-01"), sdf.parse("2013-03-31"));
            for (Object[] row : tachesPeriode) {
                System.out.println("   • Tâche " + row[0] + ": " + row[1]);
            }
            System.out.println();

            System.out.println("=== TOUS LES TESTS SONT TERMINÉS AVEC SUCCÈS ===\n");

        } catch (Exception e) {
            System.err.println("\n ERREUR : " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}
package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ma.projet.beans.Personne;
import ma.projet.beans.Homme;
import ma.projet.beans.Femme;
import ma.projet.beans.Mariage;
import ma.projet.beans.MariagePK;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Créer la configuration programmatiquement (SANS fichier XML)
            Configuration configuration = new Configuration();

            // Configurer les propriétés de connexion
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/gestion_etatcivil");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "");

            // Configurer Hibernate
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");

            // Ajouter les classes d'entités
            configuration.addAnnotatedClass(Personne.class);
            configuration.addAnnotatedClass(Homme.class);
            configuration.addAnnotatedClass(Femme.class);
            configuration.addAnnotatedClass(Mariage.class);
            configuration.addAnnotatedClass(MariagePK.class);

            // Construire la SessionFactory
            return configuration.buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
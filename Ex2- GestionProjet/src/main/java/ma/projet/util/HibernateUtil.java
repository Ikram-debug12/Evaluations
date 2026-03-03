package ma.projet.util;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ma.projet.classes.*;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // 1. Charger application.properties
            Properties properties = new Properties();
            InputStream input = HibernateUtil.class.getClassLoader()
                    .getResourceAsStream("application.properties");
            properties.load(input);

            // 2. Créer configuration Hibernate
            Configuration configuration = new Configuration();
            configuration.setProperties(properties);

            // 3. AJOUTER TOUTES LES CLASSES ENTITÉS ICI !!!
            configuration.addAnnotatedClass(ma.projet.classes.Employe.class);
            configuration.addAnnotatedClass(ma.projet.classes.EmployeTache.class);
            configuration.addAnnotatedClass(ma.projet.classes.Projet.class);
            configuration.addAnnotatedClass(ma.projet.classes.Tache.class);

            // 4. Construire SessionFactory
            sessionFactory = configuration.buildSessionFactory();

        } catch (Exception e) {
            throw new ExceptionInInitializerError("Erreur: " + e.getMessage());
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
    }
}

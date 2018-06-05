package soft.accueil.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import soft.accueil.entities.Fonctionnalite;
import soft.accueil.utils.HibernateUtil;

public class FonctionnaliteDAO {
    
    public boolean ajouter(Fonctionnalite fonctionnalite) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(fonctionnalite);
            isGood = true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            isGood = false;
        }
        session.getTransaction().commit();
        session.close();
        return isGood;
    }

    public boolean modifier(Fonctionnalite fonctionnalite) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(fonctionnalite);
            isGood = true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            isGood = false;
        }
        session.getTransaction().commit();
        session.close();
        return isGood;
    }

    public boolean supprimer(Fonctionnalite fonctionnalite) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(fonctionnalite);
            isGood = true;
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            isGood = false;
        }
        session.getTransaction().commit();
        session.close();
        return isGood;
    }

    public Fonctionnalite get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Fonctionnalite fonctionnalite = (Fonctionnalite) session.get(Fonctionnalite.class, id);
        if (fonctionnalite == null) {
            throw new RuntimeException();
        } else {

        }

        session.getTransaction().commit();
        session.close();

        return fonctionnalite;
    }

    public List<Fonctionnalite> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Fonctionnalite> fonctionnalites = session.createCriteria(Fonctionnalite.class).list();

        session.getTransaction().commit();
        session.close();

        return fonctionnalites ;

    }
    
}

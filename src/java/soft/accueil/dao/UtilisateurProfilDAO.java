package soft.accueil.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import soft.accueil.entities.UtilisateurProfil;
import soft.accueil.utils.HibernateUtil;

public class UtilisateurProfilDAO {
    
    public boolean ajouter(UtilisateurProfil utilisateurProfil) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(utilisateurProfil);
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

    public boolean modifier(UtilisateurProfil utilisateurProfil) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(utilisateurProfil);
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

    public boolean supprimer(UtilisateurProfil utilisateurProfil) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(utilisateurProfil);
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

    public UtilisateurProfil get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        UtilisateurProfil utilisateurProfil = (UtilisateurProfil) session.get(UtilisateurProfil.class, id);
        if (utilisateurProfil == null) {
            throw new RuntimeException();
        } else {

        }

        session.getTransaction().commit();
        session.close();

        return utilisateurProfil;
    }

    public List<UtilisateurProfil> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<UtilisateurProfil> utilisateurProfils = session.createCriteria(UtilisateurProfil.class).list();

        session.getTransaction().commit();
        session.close();

        return utilisateurProfils ;

    }
    
}

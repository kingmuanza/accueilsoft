package soft.accueil.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import soft.accueil.entities.Utilisateur;
import soft.accueil.entities.UtilisateurProfil;
import soft.accueil.entities.UtilisateurProfilFonctionnalite;
import soft.accueil.utils.HibernateUtil;

public class UtilisateurProfilFonctionnaliteDAO {
    
    public boolean ajouter(UtilisateurProfilFonctionnalite utilisateurProfilFonctionnalite) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(utilisateurProfilFonctionnalite);
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

    public boolean modifier(UtilisateurProfilFonctionnalite utilisateurProfilFonctionnalite) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(utilisateurProfilFonctionnalite);
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

    public boolean supprimer(UtilisateurProfilFonctionnalite utilisateurProfilFonctionnalite) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(utilisateurProfilFonctionnalite);
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

    public UtilisateurProfilFonctionnalite get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        UtilisateurProfilFonctionnalite utilisateurProfilFonctionnalite = (UtilisateurProfilFonctionnalite) session.get(UtilisateurProfilFonctionnalite.class, id);
        if (utilisateurProfilFonctionnalite == null) {
            throw new RuntimeException();
        } else {

        }

        session.getTransaction().commit();
        session.close();

        return utilisateurProfilFonctionnalite;
    }

    public List<UtilisateurProfilFonctionnalite> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<UtilisateurProfilFonctionnalite> utilisateurProfilFonctionnalites = session.createCriteria(UtilisateurProfilFonctionnalite.class).list();

        session.getTransaction().commit();
        session.close();

        return utilisateurProfilFonctionnalites ;

    }
    public boolean hasAccess(Utilisateur u, String code) {
        UtilisateurProfil utilisateurProfil = u.getUtilisateurProfil();
        for(UtilisateurProfilFonctionnalite upd : utilisateurProfil.getUtilisateurProfilFonctionnalites()){
            if(code.equals(upd.getFonctionnalite().getCode())){
                return true ;
            }
        }
        return false ;

    }
    
}

package soft.accueil.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import soft.accueil.entities.Employe;
import soft.accueil.entities.Utilisateur;
import soft.accueil.entities.UtilisateurProfilFonctionnalite;
import soft.accueil.utils.HibernateUtil;

public class UtilisateurDAO {

    public boolean ajouter(Utilisateur utilisateur) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(utilisateur);
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

    public boolean modifier(Utilisateur utilisateur) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(utilisateur);
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

    public boolean supprimer(Utilisateur utilisateur) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(utilisateur);
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

    public Utilisateur get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Utilisateur utilisateur = (Utilisateur) session.get(Utilisateur.class, id);
        if (utilisateur == null) {
            throw new RuntimeException();
        } else {

        }
        session.getTransaction().commit();
        session.close();

        return utilisateur;
    }

    public Utilisateur get(String login, String passe) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        Utilisateur utilisateur = null;
        List<Utilisateur> utilisateurs = session.createCriteria(Utilisateur.class)
                .add(Restrictions.eq("login", login))
                .add(Restrictions.eq("passe", passe))
                .list();

        if (utilisateurs.isEmpty()) {

        } else {
            utilisateur = utilisateurs.get(0);
            initialiser(utilisateur);
        }

        session.getTransaction().commit();
        session.close();

        return utilisateur;
    }

    public List<Utilisateur> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Utilisateur> utilisateurs = session.createCriteria(Utilisateur.class).list();
        for( Utilisateur utilisateur : utilisateurs){
            initialiser(utilisateur);
        }

        session.getTransaction().commit();
        session.close();

        return utilisateurs;

    }
    
    public List<Employe> getEmployes(){
        List<Utilisateur> utilisateurs = getall();
        List<Employe> utilisateursEmployes = new ArrayList<Employe>();
        for(Utilisateur utilisateur : utilisateurs){
            if("EMPLOYE".equals(utilisateur.getUtilisateurProfil().getCode())){
                utilisateursEmployes.add(utilisateur.getEmploye());
            }
        }
        return utilisateursEmployes ;
    }
    public List<Employe> getAgents(){
        List<Utilisateur> utilisateurs = getall();
        List<Employe> utilisateursEmployes = new ArrayList<Employe>();
        for(Utilisateur utilisateur : utilisateurs){
            if("AGENT".equals(utilisateur.getUtilisateurProfil().getCode())){
                utilisateursEmployes.add(utilisateur.getEmploye());
            }
        }
        return utilisateursEmployes ;
    }

    void initialiser(Utilisateur utilisateur) {
        Hibernate.initialize(utilisateur.getUtilisateurProfil());
        if (utilisateur.getUtilisateurProfil() != null) {
            Hibernate.initialize(utilisateur.getUtilisateurProfil().getUtilisateurProfilFonctionnalites());
            for (UtilisateurProfilFonctionnalite upf : utilisateur.getUtilisateurProfil().getUtilisateurProfilFonctionnalites()) {
                Hibernate.initialize(upf.getFonctionnalite());
            }
        }
        Hibernate.initialize(utilisateur.getEmploye());
    }

}

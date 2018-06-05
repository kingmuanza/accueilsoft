package soft.accueil.dao;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import soft.accueil.entities.Urgence;
import soft.accueil.utils.HibernateUtil;

public class UrgenceDAO {
    
    public boolean ajouter(Urgence urgence) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(urgence);
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

    public boolean modifier(Urgence urgence) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(urgence);
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

    public boolean supprimer(Urgence urgence) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(urgence);
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

    public Urgence get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Urgence urgence = (Urgence) session.get(Urgence.class, id);
        if (urgence == null) {
            throw new RuntimeException();
        } else {
            initialiser(urgence);
        }

        session.getTransaction().commit();
        session.close();

        return urgence;
    }

    public List<Urgence> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Urgence> urgences = session.createCriteria(Urgence.class).list();
        for(Urgence urgence : urgences){
            initialiser(urgence);
        }

        session.getTransaction().commit();
        session.close();

        return urgences ;

    }
    
    public void initialiser(Urgence urgence){
        Hibernate.initialize(urgence.getEmploye());
        if(urgence.getEmploye()!=null){
            Hibernate.initialize(urgence.getEmploye().getBureau());
        }
    }
    
}

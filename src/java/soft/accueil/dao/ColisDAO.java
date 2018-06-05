package soft.accueil.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import soft.accueil.entities.Colis;
import soft.accueil.entities.Employe;
import soft.accueil.utils.HibernateUtil;

public class ColisDAO {
    
    public boolean ajouter(Colis colis) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(colis);
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

    public boolean modifier(Colis colis) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(colis);
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

    public boolean supprimer(Colis colis) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(colis);
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

    public Colis get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Colis colis = (Colis) session.get(Colis.class, id);
        if (colis == null) {
            throw new RuntimeException();
        } else {

        }

        session.getTransaction().commit();
        session.close();

        return colis;
    }

    public List<Colis> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Colis> coliss = session.createCriteria(Colis.class).list();
        for(Colis colis : coliss){
            initialiser(colis);
        }

        session.getTransaction().commit();
        session.close();

        return coliss ;

    }
    
    public List<Colis> getAllUnreceived(){
        List<Colis> coliss = new ArrayList<Colis>();
        for(Colis colis : getall()){
            if(colis.getStatut()==null){
                coliss.add(colis);
            }
        }
        return coliss;
    }
    
    
    
    public List<Colis> getAllByEmploye(Employe e){
        List<Colis> coliss = new ArrayList<Colis>();
        for(Colis colis : getall()){
            if(Objects.equals(colis.getEmploye().getIdemploye(), e.getIdemploye())){
                coliss.add(colis);
            }
        }
        return coliss;
    }
    public List<Colis> getAllUnreceivedByEmploye(Employe e){
        List<Colis> coliss = new ArrayList<Colis>();
        for(Colis colis : getAllByEmploye(e)){
            if(colis.getStatut()==null){
                coliss.add(colis);
            }
        }
        return coliss;
    }
    
    
    
    public void initialiser(Colis colis){
        Hibernate.initialize(colis.getEmploye());
        if(colis.getEmploye()!=null){
            Hibernate.initialize(colis.getEmploye().getBureau());
        }        
    }
    
}

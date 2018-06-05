/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soft.accueil.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import soft.accueil.entities.EntreeBadge;
import soft.accueil.utils.HibernateUtil;

/**
 *
 * @author muanz
 */
public class EntreeBadgeDAO {
    
    public boolean ajouter(EntreeBadge entreeBadge) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(entreeBadge);
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
    
    public boolean modifier(EntreeBadge entreeBadge) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(entreeBadge);
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
    
    public boolean supprimer(EntreeBadge entreeBadge) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(entreeBadge);
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
    
    public EntreeBadge get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        
        EntreeBadge alerteType = (EntreeBadge) session.get(EntreeBadge.class, id);
        if (alerteType == null) {
            throw new RuntimeException();
        } else {
            
        }
        
        session.getTransaction().commit();
        session.close();
        
        return alerteType;
    }
    
    public List<EntreeBadge> getall() {
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        
        List<EntreeBadge> entreeBadges = session.createCriteria(EntreeBadge.class).list();
        
        session.getTransaction().commit();
        session.close();
        
        return entreeBadges;
        
    }
    
    public List<EntreeBadge> getallNonUtilise() {
        
        List<EntreeBadge> entreeBadges = new ArrayList<EntreeBadge>();
        for (EntreeBadge eb : this.getall()) {
            if (!(eb.getStatut()!=null && eb.getStatut().equals("Utilisé"))) {
                entreeBadges.add(eb);
            }
        }
        
        return entreeBadges;
        
    }
}


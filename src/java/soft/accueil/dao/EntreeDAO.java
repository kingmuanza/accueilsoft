/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soft.accueil.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import soft.accueil.entities.Employe;
import soft.accueil.entities.Entree;
import soft.accueil.entities.Utilisateur;
import soft.accueil.utils.HibernateUtil;

/**
 *
 * @author muanz
 */
public class EntreeDAO {

    public boolean ajouter(Entree entree) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(entree);
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

    public boolean modifier(Entree entree) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(entree);
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

    public boolean supprimer(Entree entree) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(entree);
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

    public Entree get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Entree alerteType = (Entree) session.get(Entree.class, id);
        if (alerteType == null) {
            throw new RuntimeException();
        } else {

        }

        session.getTransaction().commit();
        session.close();

        return alerteType;
    }

    public List<Entree> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Entree> entrees = session.createCriteria(Entree.class).list();
        for (Entree entree : entrees) {
            Hibernate.initialize(entree.getBureau());
            Hibernate.initialize(entree.getEmploye());
            Hibernate.initialize(entree.getEntreeBadge());
        }

        session.getTransaction().commit();
        session.close();

        return entrees;

    }

    public List<Entree> getallof(Utilisateur utilisateur) {

        List<Entree> entrees = new ArrayList<>();
        if (utilisateur != null && utilisateur.getUtilisateurProfil().getIdutilisateurProfil() == 1) {
            for (Entree entree : this.getall()) {
                if (entree.getEmploye() != null && Objects.equals(entree.getEmploye().getIdemploye(), utilisateur.getEmploye().getIdemploye())) {
                    entrees.add(entree);
                }
            }
        } else {
            entrees = this.getall();
        }

        return entrees;

    }
    
    public List<Entree> getallof(Employe employe) {

        List<Entree> entrees = new ArrayList<>();
        if (employe != null ) {
            for (Entree entree : this.getall()) {
                if (entree.getEmploye() != null && Objects.equals(entree.getEmploye().getIdemploye(), employe.getIdemploye())) {
                    entrees.add(entree);
                }
            }
        } else {
            entrees = this.getall();
        }        
        return entrees;
    }
    
    public List<Entree> getAllOfActual(Employe employe){
        Date date = new Date();
        // Convert Date to a Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        Date oday = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date demain = calendar.getTime();

        List<Entree> entrees = this.getallof(employe);
        List<Entree> entreesActu = new ArrayList<Entree>();
        for (Entree entree : entrees) {
            if (entree.getHeureSortie() == null && entree.getHeureDentree().after(oday) && entree.getHeureDentree().before(demain)) {
                entreesActu.add(entree);
            }
        }

        return entreesActu;
    }

    public List<Entree> getActuAll() {

        Date date = new Date();
        // Convert Date to a Calendar
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        Date oday = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date demain = calendar.getTime();

        List<Entree> entrees = this.getall();
        List<Entree> entreesActu = new ArrayList<Entree>();
        for (Entree entree : entrees) {
            if (entree.getHeureSortie() == null && entree.getHeureDentree().after(oday) && entree.getHeureDentree().before(demain)) {
                entreesActu.add(entree);
            }
        }
        for (Entree entree : entreesActu) {
            Hibernate.initialize(entree.getBureau());
            Hibernate.initialize(entree.getEmploye());
            Hibernate.initialize(entree.getEntreeBadge());
        }

        return entreesActu;

    }

    public Date premiereEntree() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Entree> entrees = session.createCriteria(Entree.class, "entree")
                .addOrder(Order.asc("entree.heureDentree"))
                .list();
        session.getTransaction().commit();
        session.close();
        if (!entrees.isEmpty()) {
            System.out.println("Première entrée : " + entrees.get(0).getHeureDentree());
            return entrees.get(0).getHeureDentree();
        } else {
            return new Date();
        }

    }

    public Date derniereEntree() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Entree> entrees = session.createCriteria(Entree.class, "entree")
                .addOrder(Order.desc("entree.heureDentree"))
                .list();
        session.getTransaction().commit();
        session.close();

        if (!entrees.isEmpty()) {

            System.out.println("Dernière entrée : " + entrees.get(0).getHeureDentree());
            return entrees.get(0).getHeureDentree();
        } else {
            return new Date();
        }
    }
}


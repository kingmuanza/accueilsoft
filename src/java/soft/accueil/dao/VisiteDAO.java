package soft.accueil.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import soft.accueil.entities.Employe;
import soft.accueil.entities.Entree;
import soft.accueil.entities.Visite;
import soft.accueil.utils.HibernateUtil;


public class VisiteDAO {

    public boolean ajouter(Visite visite) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(visite);
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

    public boolean modifier(Visite visite) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(visite);
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

    public boolean supprimer(Visite visite) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(visite);
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

    public Visite get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Visite visite = (Visite) session.get(Visite.class, id);
        if (visite == null) {
            throw new RuntimeException();
        } else {
            Hibernate.initialize(visite.getEmploye());
            Hibernate.initialize(visite.getEntrees());
            if(visite.getEmploye()!=null){
                Hibernate.initialize(visite.getEmploye().getBureau());
            }

        }

        session.getTransaction().commit();
        session.close();

        return visite;
    }

    public List<Visite> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Visite> visites = session.createCriteria(Visite.class).list();
        for(Visite visite : visites){
            Hibernate.initialize(visite.getEmploye());
            Hibernate.initialize(visite.getEntrees());
            if(visite.getEmploye()!=null){
                Hibernate.initialize(visite.getEmploye().getBureau());
            }
        }

        session.getTransaction().commit();
        session.close();

        return visites ;

    }
    public List<Visite> getallbyEmploye(Employe employe) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Visite> visites = session.createCriteria(Visite.class, "visite")
                .createAlias("employe", "visite.employe")
                .add(Restrictions.eq("employe.idemploye", employe.getIdemploye()))
                .list();
        for(Visite visite : visites){
            Hibernate.initialize(visite.getEmploye());
            Hibernate.initialize(visite.getEntrees());
            if(visite.getEmploye()!=null){
                Hibernate.initialize(visite.getEmploye().getBureau());
            }
        }

        session.getTransaction().commit();
        session.close();

        return visites ;

    }
    
    public List<Visite> getAllOfActual(Employe employe){
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

        List<Visite> visites = this.getallbyEmploye(employe);
        List<Visite> visitesActu = new ArrayList<Visite>();
        for (Visite visite : visites) {
            if (!"Venu".equals(visite.getStatut()) && visite.getHeureAttendue().after(oday) && visite.getHeureAttendue().before(demain)) {
                visitesActu.add(visite);
            }
        }

        return visitesActu;
    }
    
    public List<Visite> getallActif() {

        List<Visite> visites = new ArrayList<>();
        this.getall().stream().filter((visite) -> (!visite.getStatut().equals("Venu"))).forEach((visite) -> {
            visites.add(visite);
        });

        return visites ;

    }
}

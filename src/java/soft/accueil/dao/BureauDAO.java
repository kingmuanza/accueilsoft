package soft.accueil.dao;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import soft.accueil.entities.Bureau;
import soft.accueil.utils.HibernateUtil;

public class BureauDAO {

    public boolean ajouter(Bureau bureau) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(bureau);
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

    public boolean modifier(Bureau bureau) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(bureau);
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

    public boolean supprimer(Bureau bureau) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(bureau);
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

    public Bureau get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Bureau bureau = (Bureau) session.get(Bureau.class, id);
        if (bureau == null) {
            throw new RuntimeException();
        } else {
            Hibernate.initialize(bureau.getBatiment());
        }

        session.getTransaction().commit();
        session.close();

        return bureau;
    }

    public List<Bureau> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Bureau> bureaus = session.createCriteria(Bureau.class).list();
        for(Bureau bureau : bureaus){
            Hibernate.initialize(bureau.getEntrees());
            Hibernate.initialize(bureau.getBatiment());
        }

        session.getTransaction().commit();
        session.close();

        return bureaus ;

    }
}


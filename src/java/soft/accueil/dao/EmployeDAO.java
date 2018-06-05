package soft.accueil.dao;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import soft.accueil.entities.Employe;
import soft.accueil.utils.HibernateUtil;

public class EmployeDAO {

    public boolean ajouter(Employe employe) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(employe);
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

    public boolean modifier(Employe employe) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(employe);
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

    public boolean supprimer(Employe employe) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(employe);
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

    public Employe get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Employe employe = (Employe) session.get(Employe.class, id);
        if (employe == null) {
            throw new RuntimeException();
        } else {
            Hibernate.initialize(employe.getPoste());
            Hibernate.initialize(employe.getBureau());
            if (employe.getBureau() != null) {
                Hibernate.initialize(employe.getBureau().getBatiment());
            }
            Hibernate.initialize(employe.getEntrees());
        }

        session.getTransaction().commit();
        session.close();

        return employe;
    }

    public List<Employe> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Employe> employes = session.createCriteria(Employe.class).list();
        for (Employe employe : employes) {
            initialiser(employe);
        }

        session.getTransaction().commit();
        session.close();

        return employes;

    }

    public void initialiser(Employe employe) {
        Hibernate.initialize(employe.getPoste());
        Hibernate.initialize(employe.getBureau());
        if (employe.getBureau() != null) {
            Hibernate.initialize(employe.getBureau().getBatiment());
        }
        Hibernate.initialize(employe.getEntrees());
    }

}

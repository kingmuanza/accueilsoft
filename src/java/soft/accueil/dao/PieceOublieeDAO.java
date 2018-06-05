package soft.accueil.dao;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import soft.accueil.entities.PieceOubliee;
import soft.accueil.utils.HibernateUtil;

public class PieceOublieeDAO {

    public boolean ajouter(PieceOubliee pieceOubliee) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(pieceOubliee);
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

    public boolean modifier(PieceOubliee pieceOubliee) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(pieceOubliee);
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

    public boolean supprimer(PieceOubliee pieceOubliee) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(pieceOubliee);
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

    public PieceOubliee get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        PieceOubliee pieceOubliee = (PieceOubliee) session.get(PieceOubliee.class, id);
        if (pieceOubliee == null) {
            throw new RuntimeException();
        } else {
            Hibernate.initialize(pieceOubliee.getEntree());
            if(pieceOubliee.getEntree()!=null){
                Hibernate.initialize(pieceOubliee.getEntree().getBureau());
            }
        }

        session.getTransaction().commit();
        session.close();

        return pieceOubliee;
    }

    public List<PieceOubliee> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<PieceOubliee> pieceOubliees = session.createCriteria(PieceOubliee.class).list();
        for(PieceOubliee pieceOubliee : pieceOubliees){
            Hibernate.initialize(pieceOubliee.getEntree());
            if(pieceOubliee.getEntree()!=null){
                Hibernate.initialize(pieceOubliee.getEntree().getBureau());
            }
        }

        session.getTransaction().commit();
        session.close();

        return pieceOubliees ;

    }
}


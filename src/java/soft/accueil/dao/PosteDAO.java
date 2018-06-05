package soft.accueil.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import soft.accueil.entities.Poste;
import soft.accueil.utils.HibernateUtil;

public class PosteDAO {

    private List<Poste> postesAclasser;

    public boolean ajouter(Poste poste) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(poste);
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

    public boolean modifier(Poste poste) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(poste);
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

    public boolean supprimer(Poste poste) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(poste);
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

    public Poste get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Poste poste = (Poste) session.get(Poste.class, id);
        if (poste == null) {
            throw new RuntimeException();
        } else {
            Hibernate.initialize(poste.getPoste());
            Hibernate.initialize(poste.getPostes());
        }

        session.getTransaction().commit();
        session.close();

        return poste;
    }

    public List<Poste> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Poste> postes = session.createCriteria(Poste.class).list();
        for (Poste poste : postes) {
            Hibernate.initialize(poste.getPoste());
            Hibernate.initialize(poste.getPostes());
        }

        session.getTransaction().commit();
        session.close();

        return postes;

    }

    public List<Poste> getFilsAll(int idparent) {
        System.out.println("Récupération  : " + postesAclasser);
        if (postesAclasser == null) {
            postesAclasser = new ArrayList<Poste>();
            System.out.println("La liste est null ####################################");
        }
        postesAclasser.add(this.get(idparent));

        if (this.get(idparent).getPostes() != null && !get(idparent).getPostes().isEmpty()) {
            for (Poste poste : this.get(idparent).getPostes()) {
                this.getFilsAll(poste.getIdposte());
            }
            return postesAclasser;

        } else {
            return null;
        }

    }
}

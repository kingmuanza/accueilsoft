package soft.accueil.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import soft.accueil.entities.Employe;
import soft.accueil.entities.Message;
import soft.accueil.utils.HibernateUtil;

public class MessageDAO {
    
    public boolean ajouter(Message message) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.save(message);
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

    public boolean modifier(Message message) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.update(message);
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

    public boolean supprimer(Message message) {
        boolean isGood = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        try {
            session.delete(message);
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

    public Message get(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        Message message = (Message) session.get(Message.class, id);
        if (message == null) {
            throw new RuntimeException();
        } else {

        }

        session.getTransaction().commit();
        session.close();

        return message;
    }

    public List<Message> getall() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();

        List<Message> messages = session.createCriteria(Message.class).list();
        for(Message message : messages){
            initialiser(message);
        }

        session.getTransaction().commit();
        session.close();

        return messages ;

    }
    
    public List<Message> getallMyMessagesReceive(Employe e) {
        List<Message> messages = getall();
        List<Message> myMessages = new ArrayList<>();
        messages.stream().filter((message) -> (Objects.equals(message.getEmployeByIdrecepteur().getIdemploye(), e.getIdemploye()))).forEachOrdered((e1) -> myMessages.add(e1));
        return myMessages ;
    }
    
    public List<Message> getallMyUnreadReceive(Employe e) {
        List<Message> messages = getallMyMessagesReceive(e);
        List<Message> myMessages = new ArrayList<>();
        for(Message m : messages){
            if(m.getDateReception()==null){
                myMessages.add(m);
            }
        }
        return myMessages ;
    }
    
    public List<Message> getallMyMessagesSent(Employe e) {
        List<Message> messages = getall();
        List<Message> myMessages = new ArrayList<Message>();
        for(Message message : messages){
            if(Objects.equals(message.getEmployeByIdrecepteur().getIdemploye(), e.getIdemploye())){
                myMessages.add(message);
            }
        }
        return myMessages ;
    }
    
    public void initialiser(Message message){
        Hibernate.initialize(message.getEmployeByIdemetteur());
        Hibernate.initialize(message.getEmployeByIdrecepteur());
    }
    
}

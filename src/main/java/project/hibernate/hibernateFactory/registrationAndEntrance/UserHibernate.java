package project.hibernate.hibernateFactory.registrationAndEntrance;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import project.hibernate.hibernateObjectFactory.User;
import project.hibernate.hibernateUtil.HibernateUtil;


public class UserHibernate {

    public static void registration(User user){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session=sessionFactory.openSession();

        Transaction transaction=session.beginTransaction();
        session.persist(user);
        transaction.commit();
        session.close();
    }

    public static void entrance(String phone, String password){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session=sessionFactory.openSession();

        Transaction transaction=session.beginTransaction();

    }
}

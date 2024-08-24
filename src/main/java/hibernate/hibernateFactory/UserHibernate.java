package hibernate.hibernateFactory;

import hibernate.hibernateObjectFactory.User;
import hibernate.hibernateUtil.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserHibernate {

    public static void Registration(User user){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session=sessionFactory.openSession();

        Transaction transaction=session.beginTransaction();
        session.persist(user);
        transaction.commit();
        session.close();
    }
}

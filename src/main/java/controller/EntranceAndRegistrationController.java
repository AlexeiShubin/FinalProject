package controller;

import hibernate.hibernateFactory.User;
import hibernate.hibernateUtil.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EntranceAndRegistrationController {
    @PostMapping("/startPageAfterRegistration")
    public String registrationController(@ModelAttribute User user) {

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session=sessionFactory.openSession();

        Transaction transaction=session.beginTransaction();
        session.persist(user);
        transaction.commit();
        session.close();

        return "startPageAfterEntrance";
    }

    @PostMapping("/startPageAfterEntrance")
    public String entranceController(@RequestParam(value = "username", required = false) String phone,
                                     @RequestParam(value = "password", required = false) String password) {
        return "startPageAfterEntrance";
    }
}


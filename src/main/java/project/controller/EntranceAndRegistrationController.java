package project.controller;

import project.hibernate.hibernateFactory.registrationAndEntrance.UserHibernate;
import project.hibernate.hibernateObjectFactory.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EntranceAndRegistrationController {
    @PostMapping("/startPageAfterRegistration")
    public String registrationController(@ModelAttribute User user) {
        UserHibernate.registration(user);

        return "startPageAfterEntrance";
    }

    @PostMapping("/startPageAfterEntrance")
    public String entranceController(@RequestParam(value = "username", required = false) String phone,
                                     @RequestParam(value = "password", required = false) String password) {
        return "startPageAfterEntrance";
    }
}


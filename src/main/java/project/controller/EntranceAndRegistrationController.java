package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import project.hibernate.hibernateFactory.registrationAndEntrance.UserHibernate;
import project.hibernate.hibernateObjectFactory.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.service.UserService;

@Controller
public class EntranceAndRegistrationController {
    @Autowired
    private UserService userService;
    @PostMapping("/startPageAfterRegistration")
    public String registrationController(@ModelAttribute User user, Model model) {
        String result=userService.registerUser(user);
        if ("phoneNumberError".equals(result)){
            model.addAttribute("phoneNumberError", "Пользователь с таким номером телефона уже существует");
            return "registration";
        }
        UserHibernate.registration(user);
        return "startPageAfterEntrance";
    }

    @PostMapping("/startPageAfterEntrance")
    public String entranceController(@RequestParam(value = "username", required = false) String phone,
                                     @RequestParam(value = "password", required = false) String password,
                                     Model model) {
        String result=userService.authenticate(phone, password);
        if ("userNotFound".equals(result)) {
            model.addAttribute("errorMessage", "Логин или пароль неверны");
            return "entrance";
        }
        return result;
    }
}


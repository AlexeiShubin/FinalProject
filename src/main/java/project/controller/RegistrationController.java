package project.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.hibernate.model.User;
import project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public String registrationController(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        String result=userService.registerUser(user);

        if ("phoneNumberError".equals(result)){
            redirectAttributes.addFlashAttribute("phoneNumberError", "Пользователь с таким номером телефона уже существует");
            return "registration";
        } else if ("BlockUserError".equals(result)) {
            redirectAttributes.addFlashAttribute("BlockUserError", "Ваш номер телефона заблокирован");
            return "registration";
        }

        return result;
    }
}

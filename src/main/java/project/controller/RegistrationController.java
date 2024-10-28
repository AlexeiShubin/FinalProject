package project.controller;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.hibernate.model.User;
import project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for handling user registration requests.
 * This controller manages the registration of new users, including validation and error handling.
 */
@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    /**
     * Handles POST requests for user registration.
     *
     * @param user the user object containing registration data
     * @param redirectAttributes attributes to pass to the redirect
     * @return the view name to render after registration, could indicate success or failure
     */

    @PostMapping("/registration")
    public String registrationController(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            String result = userService.registerUser(user);

            if ("phoneNumberError".equals(result)) {
                redirectAttributes.addFlashAttribute("phoneNumberError", "Пользователь с таким номером телефона уже существует");
                return "registration";
            } else if ("BlockUserError".equals(result)) {
                redirectAttributes.addFlashAttribute("BlockUserError", "Ваш номер телефона заблокирован");
                return "registration";
            }

            return result;
        } catch (Exception e) {
            logger.error("Ошибка при регистрации пользователя: {}", e.getMessage(), e);
            return "registration";
        }
    }
}

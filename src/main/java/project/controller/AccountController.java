package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for managing user account actions.
 * This controller handles requests related to user account pages.
 */

@Controller
public class AccountController {

    /**
     * Handles GET requests to display the user's account page.
     *
     * @return the name of the user account page template that will be returned
     *         to the client for rendering.
     */
    @GetMapping("/myUserPage")
    public String myPage() {
        return "myUserAccount";
    }
}

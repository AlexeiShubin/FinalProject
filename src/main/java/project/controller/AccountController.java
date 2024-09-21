package project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccountController {

    @GetMapping("/myUserPage")
    public String myPage() {
        return "myUserAccount";
    }
}

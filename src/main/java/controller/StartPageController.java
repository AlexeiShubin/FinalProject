package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("/")
public class StartPageController {
    @GetMapping("/")
    public String run(){
        return "startPage";
    }

    @GetMapping("/contactInformation")
    public String contactInformation(){
        return "contactInformation";
    }

    @GetMapping("/medicines")
    public String medicines(){
        return "medicines";
    }

    @GetMapping("/entrance")
    public String entrancePage(){
        return "entrance";
    }

    @GetMapping("/registration")
    public String registrationPage(){
        return "registration";
    }
}

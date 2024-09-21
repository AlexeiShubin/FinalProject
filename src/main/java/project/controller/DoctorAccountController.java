package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.hibernate.model.Recipe;
import project.service.GeneralTechnicalService;
import project.service.RecipeService;

@Controller
@RequestMapping("/doctor")
public class DoctorAccountController {

    @Autowired
    private GeneralTechnicalService generalTechnicalService;
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/users")
    public String users(Model model){
        generalTechnicalService.users(model);
        return "userList";
    }

    @GetMapping("/bid")
    public String bid(@RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "30") int size,
                      Model model){
        Page<Recipe> bidPage = recipeService.getAllBid(page, size);

        model.addAttribute("requests", bidPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bidPage.getTotalPages());
        model.addAttribute("totalItems", bidPage.getTotalElements());
        return "recipeRequests";
    }

    @PostMapping("/bid")
    public String extendRequest(@RequestParam int requestId, Model model) {
        boolean isExtended = recipeService.extendRequestById(requestId);

        if (isExtended) {
            model.addAttribute("message", "Рецепт успешно продлён на месяц.");
        } else {
            model.addAttribute("message", "Ошибка при продлении рецепта.");
        }
        return "redirect:/doctor/bid";
    }
}

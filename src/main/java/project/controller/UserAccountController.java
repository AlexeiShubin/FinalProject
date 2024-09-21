package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.hibernate.model.Medicine;
import project.hibernate.model.Recipe;
import project.hibernate.repository.MedicineRepository;
import project.hibernate.repository.RecipeRepository;
import project.config.securityConfig.UserInfo;
import project.service.MedicineService;
import project.service.OrderService.UserOrderService;
import project.service.RecipeService;
import project.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserAccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserOrderService orderService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private UserInfo userInfo;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;


    @GetMapping("/profile")
    public String profile(Model model) {
        userService.userInfo(model);
        return "myAccountInfo";
    }

    @GetMapping("/medicines")
    public String medicines(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        Page<Medicine> medicinePage = medicineService.getAllProducts(page, size);

        model.addAttribute("medicines", medicinePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", medicinePage.getTotalPages());
        model.addAttribute("totalItems", medicinePage.getTotalElements());
        return "medicine";
    }

    @PostMapping("/medicines")
    public String medicine(@RequestParam("medicineId") int medicineId, RedirectAttributes redirectAttributes) {
        orderService.addMedicineToCurrentOrder(userInfo.getUser(), medicineRepository.findById(medicineId), redirectAttributes);
        return "redirect:/user/medicines";
    }

    @PostMapping("/requestRecipe")
    public String requestRecipe(@RequestParam("medicineId") int id, RedirectAttributes redirectAttributes){
        recipeService.requestRecipe(id);
        redirectAttributes.addFlashAttribute("success", "Запрос отправлен");
        return "redirect:/user/medicines";
    }

    @GetMapping("/myRecipes")
    public String Recipe(Model model){
        model.addAttribute("recipes", recipeService.getRecipes());
        return "userRecipe";
    }

    @PostMapping("/myRecipes")
    public String Recipe(@RequestParam("recipeId") int id, RedirectAttributes redirectAttributes){
        Optional<Recipe> recipeOpt=recipeRepository.findById(id);
        recipeOpt.ifPresent(recipe -> recipeService.requestRecipe(recipe.getMedicine().getId()));
        return "redirect:/user/myRecipes";
    }

}

package project.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
/**
 * Controller for managing user account actions and functionalities.
 * This controller handles requests related to user profiles, medicine listings,
 * recipe requests, and user-specific operations within the application.
 */
@Controller
@RequestMapping("/user")
public class UserAccountController {

    private static final Logger logger = LogManager.getLogger(UserAccountController.class);

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

    /**
     * Handles GET requests to display the user's profile information.
     *
     * @param model the model to be passed to the view
     * @return the name of the profile view
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        userService.userInfo(model);
        return "myAccountInfo";
    }

    /**
     * Handles GET requests to retrieve and display a paginated list of medicines.
     *
     * @param page the current page number (default is 0)
     * @param size the number of items per page (default is 10)
     * @param model the model to be passed to the view
     * @return the name of the medicines view, or an error page in case of an exception
     */
    @GetMapping("/medicines")
    public String medicines(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        try {
            Page<Medicine> medicinePage = medicineService.getAllProducts(page, size);

            model.addAttribute("medicines", medicinePage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", medicinePage.getTotalPages());
            model.addAttribute("totalItems", medicinePage.getTotalElements());
            return "medicine";
        } catch (Exception e) {
            logger.error("Error retrieving medicines: {}", e.getMessage(), e);
            return "errorPage";
        }
    }

    /**
     * Handles POST requests to add a medicine to the current order.
     *
     * @param medicineId the ID of the medicine to be added
     * @param redirectAttributes attributes to pass to the redirect
     * @return a redirect to the medicines page
     */
    @PostMapping("/medicines")
    public String medicine(@RequestParam("medicineId") int medicineId, RedirectAttributes redirectAttributes) {
        try {
            orderService.addMedicineToCurrentOrder(userInfo.getUser(), medicineRepository.findById(medicineId), redirectAttributes);
            return "redirect:/user/medicines";
        } catch (Exception e) {
            logger.error("Error adding medicine to order: {}", e.getMessage(), e);
            return "redirect:/user/medicines";
        }
    }

    /**
     * Handles POST requests to request a recipe for a specific medicine.
     *
     * @param id the ID of the medicine for which the recipe is requested
     * @param redirectAttributes attributes to pass to the redirect
     * @return a redirect to the medicines page
     */
    @PostMapping("/requestRecipe")
    public String requestRecipe(@RequestParam("medicineId") int id, RedirectAttributes redirectAttributes) {
        try {
            recipeService.requestRecipe(id);
            redirectAttributes.addFlashAttribute("success", "Запрос отправлен");
        } catch (Exception e) {
            logger.error("Error requesting recipe for medicineId {}: {}", id, e.getMessage(), e);
            }
        return "redirect:/user/medicines";
    }

    /**
     * Handles GET requests to retrieve and display the user's recipes.
     *
     * @param model the model to be passed to the view
     * @return the name of the user's recipes view, or an error page in case of an exception
     */
    @GetMapping("/myRecipes")
    public String Recipe(Model model) {
        try {
            model.addAttribute("recipes", recipeService.getRecipes());
            return "userRecipe";
        } catch (Exception e) {
            logger.error("Error retrieving recipes: {}", e.getMessage(), e);
           return "errorPage";
        }
    }

    /**
     * Handles POST requests to request a recipe using a specific recipe ID.
     *
     * @param id the ID of the recipe to be requested
     * @param redirectAttributes attributes to pass to the redirect
     * @return a redirect to the user's recipes page
     */
    @PostMapping("/myRecipes")
    public String Recipe(@RequestParam("recipeId") int id, RedirectAttributes redirectAttributes) {
        try {
            Optional<Recipe> recipeOpt = recipeRepository.findById(id);
            recipeOpt.ifPresent(recipe -> recipeService.requestRecipe(recipe.getMedicine().getId()));
        } catch (Exception e) {
            logger.error("Error requesting recipe with recipeId {}: {}", id, e.getMessage(), e);
            }
        return "redirect:/user/myRecipes";
    }
}
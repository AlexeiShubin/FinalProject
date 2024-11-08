package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.hibernate.model.Recipe;
import project.service.GeneralTechnicalService;
import project.service.RecipeService;

/**
 * Controller for managing doctor-specific account actions.
 * This controller handles requests related to user management and recipe requests by doctors.
 */
@Controller
@RequestMapping("/doctor")
public class DoctorAccountController {

    private static final Logger logger = LoggerFactory.getLogger(DoctorAccountController.class);

    @Autowired
    private GeneralTechnicalService generalTechnicalService;

    @Autowired
    private RecipeService recipeService;

    /**
     * Handles GET requests to retrieve and display a list of users.
     *
     * @param model the model to be passed to the view
     * @return the name of the user list view, or an error page if an exception occurs
     */

    @GetMapping("/users")
    public String users(Model model) {
        try {
            generalTechnicalService.users(model);
            return "userList";
        } catch (Exception e) {
            logger.error("Ошибка при получении списка пользователей: ", e);
            return "errorPage";
        }
    }

    /**
     * Handles GET requests to retrieve and display recipe requests with pagination.
     *
     * @param page the current page number (default is 0)
     * @param size the number of items per page (default is 30)
     * @param model the model to be passed to the view
     * @return the name of the view that displays recipe requests, or an error page if an exception occurs
     */
    @GetMapping("/bid")
    public String bid(@RequestParam(defaultValue = "0") int page,
                      @RequestParam(defaultValue = "30") int size,
                      Model model) {
        try {
            Page<Recipe> bidPage = recipeService.getAllBid(page, size);

            model.addAttribute("requests", bidPage.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", bidPage.getTotalPages());
            model.addAttribute("totalItems", bidPage.getTotalElements());
            return "recipeRequests";
        } catch (Exception e) {
            logger.error("Ошибка при получении заявок на рецепты: ", e);
            return "errorPage";
        }
    }

    /**
     * Handles POST requests to extend the validity of a recipe request.
     *
     * @param requestId the ID of the recipe request to extend
     * @param redirectAttributes attributes to pass to the redirect
     * @return a redirect to the recipe requests page
     */

    @PostMapping("/bid")
    public String extendRequest(@RequestParam int requestId, RedirectAttributes redirectAttributes) {
        try {
            boolean isExtended = recipeService.extendRequestById(requestId);

            if (isExtended) {
                redirectAttributes.addFlashAttribute("message", "Рецепт успешно продлён на месяц.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Ошибка при продлении рецепта.");
            }
        } catch (Exception e) {
            logger.error("Ошибка при продлении рецепта с ID {}: ", requestId, e);
        }
        return "redirect:/doctor/bid";
    }
}
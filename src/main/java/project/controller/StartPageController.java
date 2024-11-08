package project.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.hibernate.model.Medicine;
import project.hibernate.repository.MedicineRepository;
import project.service.MedicineService;
import org.springframework.dao.DataAccessException;

/**
 * Controller for managing the start page and related functionalities of the application.
 * This controller handles requests for the start page, contact information, medicine listings,
 * and other navigation within the application.
 */

@Controller
public class StartPageController {

    private static final Logger logger = LogManager.getLogger(StartPageController.class);

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MessageSource messageSource;

    /**
     * Handles GET requests to the start page.
     *
     * @return the name of the start page view
     */

    @GetMapping("/")
    public String run() {
        return "startPage";
    }

    /**
     * Handles GET requests to the contact information page.
     *
     * @return the name of the contact information view
     */

    @GetMapping("/contactInformation")
    public String contactInformation() {
        return "contactInformation";
    }

    /**
     * Handles GET requests to retrieve and display a paginated list of medicines.
     *
     * @param page the current page number (default is 0)
     * @param size the number of items per page (default is 10)
     * @param model the model to be passed to the view
     * @return the name of the medicines view or an error page in case of an exception
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
        } catch (DataAccessException e) {
            logger.error("Database access error while fetching medicines: {}", e.getMessage());
            return "errorPage";
        } catch (Exception e) {
            logger.fatal("An unexpected error occurred: {}", e.getMessage(), e);
            return "errorPage";
        }
    }

    /**
     * Handles GET requests to the entrance page.
     *
     * @return the name of the entrance page view
     */
    @GetMapping("/entrance")
    public String entrancePage() {
        return "entrance";
    }

    /**
     * Handles GET requests to the registration page.
     *
     * @return the name of the registration page view
     */
    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }
}
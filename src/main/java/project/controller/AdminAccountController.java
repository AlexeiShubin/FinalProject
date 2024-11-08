package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.hibernate.model.Medicine;
import project.hibernate.repository.MedicineRepository;
import project.service.GeneralTechnicalService;
import project.service.adminService.BlockUser;
import project.service.adminService.MedicineCRUD;

/**
 * Controller for managing administrative tasks related to user accounts
 * and medicines in the application.
 * This controller handles requests specific to the admin functionalities,
 * including user management and medicine CRUD operations.
 */

@Controller
@RequestMapping("/admin")
public class AdminAccountController {

    private static final Logger logger = LoggerFactory.getLogger(AdminAccountController.class);

    @Autowired
    private GeneralTechnicalService generalTechnicalService;

    @Autowired
    private MedicineCRUD medicineCRUD;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private BlockUser blockUser;

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
            logger.error("Ошибка получения списка пользователей: ", e);
            return "errorPage";
        }
    }

    /**
     * Handles POST requests to block a user by their ID.
     *
     * @param id the ID of the user to be blocked
     * @param redirectAttributes attributes to pass to the redirect
     * @return a redirect to the user list page
     */

    @PostMapping("/blockUser/{id}")
    public String deleteUser(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        try {
            blockUser.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "Пользователь заблокирован успешно.");
        } catch (Exception e) {
            logger.error("Ошибка блокировки пользователя с ID {}: ", id, e);
            redirectAttributes.addFlashAttribute("message", "Не удалось заблокировать пользователя.");
        }
        return "redirect:/admin/users";
    }

    /**
     * Handles GET requests to display the add medicine page.
     *
     * @return the name of the add medicine view
     */
    @GetMapping("/addMedicine")
    public String interactionMedicines() {
        return "addMedicine";
    }

    /**
     * Handles POST requests to add a new medicine.
     *
     * @param medicine the medicine object to be added
     * @param redirectAttributes attributes to pass to the redirect
     * @return a redirect based on the outcome of the medicine addition
     */
    @PostMapping("/addMedicine")
    public String medicine(@ModelAttribute Medicine medicine, RedirectAttributes redirectAttributes) {
        try {
            return medicineCRUD.createMedicine(medicine, redirectAttributes);
        } catch (Exception e) {
            logger.error("Ошибка добавления лекарства: ", e);
            return "redirect:/admin/addMedicine";
        }
    }

    /**
     * Handles GET requests to retrieve the editing page for medicine.
     *
     * @return the name of the edit medicine view
     */
    @GetMapping("/getMedicine")
    public String getMedicine() {
        return "editMedicine";
    }

    /**
     * Handles GET requests to display the medicine edit page for a specified medicine name.
     *
     * @param name the name of the medicine to edit
     * @param model the model to be passed to the view
     * @return the name of the edit medicine view, or an error page if an exception occurs
     */
    @GetMapping("/editMedicine")
    public String editMedicine(@RequestParam String name, Model model) {
        try {
            medicineCRUD.medicineGet(name, model);
            return "editMedicine";
        } catch (Exception e) {
            logger.error("Ошибка получения лекарства с именем {}: ", name, e);
            return "errorPage";
        }
    }

    /**
     * Handles POST requests to update a specified medicine.
     *
     * @param name the name of the medicine to update
     * @param medicine the updated medicine object
     * @param redirectAttributes attributes to pass to the redirect
     * @return a redirect based on the outcome of the medicine update
     */

    @PostMapping("/editMedicine")
    public String editMedicine(@RequestParam String name, @ModelAttribute Medicine medicine, RedirectAttributes redirectAttributes) {
        try {
            return medicineCRUD.updateMedicinePost(medicine, name, redirectAttributes);
        } catch (Exception e) {
            logger.error("Ошибка обновления лекарства: ", e);
            return "redirect:/admin/editMedicine?name=" + name;
        }
    }

    /**
     * Handles GET requests to display the delete medicine page.
     *
     * @return the name of the delete medicine view
     */
    @GetMapping("deleteMedicine")
    public String deleteMedicine() {
        return "deleteMedicine";
    }

    /**
     * Handles POST requests to delete a specified medicine.
     *
     * @param name the name of the medicine to delete
     * @param redirectAttributes attributes to pass to the redirect
     * @param model the model to be passed to the view
     * @return a redirect based on the outcome of the medicine deletion
     */
    @PostMapping("/deleteMedicine")
    public String deleteMedicine(@RequestParam String name, RedirectAttributes redirectAttributes, Model model) {
        try {
            return medicineCRUD.deleteMedicine(name, redirectAttributes, model);
        } catch (Exception e) {
            logger.error("Ошибка удаления лекарства: ", e);
            return "redirect:/admin/deleteMedicine";
        }
    }
}
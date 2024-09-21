package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.hibernate.model.Medicine;
import project.hibernate.repository.MedicineRepository;
import project.service.GeneralTechnicalService;
import project.service.adminService.BlockUser;
import project.service.adminService.MedicineCRUD;

@Controller
@RequestMapping("/admin")
public class AdminAccountController {
    @Autowired
    private GeneralTechnicalService generalTechnicalService;

    @Autowired
    private MedicineCRUD medicineCRUD;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private BlockUser blockUser;

    @GetMapping("/users")
    public String users(Model model) {
        generalTechnicalService.users(model);
        return "userList";
    }

    @PostMapping("/blockUser/{id}")
    public String deleteUser(@PathVariable("id") int id){
        blockUser.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/addMedicine")
    public String interactionMedicines() {
        return "addMedicine";
    }

    @PostMapping("/addMedicine")
    public String medicine(@ModelAttribute Medicine medicine, RedirectAttributes redirectAttributes) {
        return medicineCRUD.createMedicine(medicine, redirectAttributes);
    }

    @GetMapping("/getMedicine")
    public String getMedicine() {
        return "editMedicine";
    }

    @GetMapping("/editMedicine")
    public String editMedicine(@RequestParam String name, Model model) {
        medicineCRUD.medicineGet(name, model);
        return "editMedicine";
    }

    @PostMapping("/editMedicine")
    public String editMedicine(@RequestParam String name, @ModelAttribute Medicine medicine, RedirectAttributes redirectAttributes) {
        return medicineCRUD.updateMedicinePost(medicine, name, redirectAttributes);
    }

    @GetMapping("deleteMedicine")
    public String deleteMedicine() {
        return "deleteMedicine";
    }

    @PostMapping("/deleteMedicine")
    public String deleteMedicine(@RequestParam String name, RedirectAttributes redirectAttributes, Model model) {
        return medicineCRUD.deleteMedicine(name, redirectAttributes, model);
    }
}

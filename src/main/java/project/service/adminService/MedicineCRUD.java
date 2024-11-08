package project.service.adminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.hibernate.model.Medicine;
import project.hibernate.repository.MedicineRepository;

/**
 * Service class for managing CRUD operations related to medicines.
 * This class provides methods to create, retrieve, update, and delete medicines in the system.
 */
@Service
public class MedicineCRUD {

    @Autowired
    private MedicineRepository medicineRepository;

    /**
     * Creates a new medicine entry in the system.
     *
     * @param medicine the medicine object to be created
     * @param redirectAttributes attributes for redirecting with a message
     * @return a redirect URL to the add medicine page
     */
    public String createMedicine(Medicine medicine, RedirectAttributes redirectAttributes) {
        medicine.setName(medicine.getName().toLowerCase());
        Medicine existingMedicine = medicineRepository.findByName(medicine.getName());

        if (existingMedicine!=null) {
            redirectAttributes.addFlashAttribute("message", "Данное лекарство уже занесено в список лекарств");
        } else {
            medicineRepository.save(medicine);
            redirectAttributes.addFlashAttribute("message", "лекарство добавлено");
        }
        return "redirect:/admin/addMedicine";
    }

    /**
     * Retrieves a medicine by its name.
     *
     * @param name the name of the medicine to retrieve
     * @param model model to add attributes for the view
     * @return the medicine object if found, null otherwise
     */
    public Medicine medicineGet(String name, Model model) {
        Medicine medicine = medicineRepository.findByName(name.toLowerCase());
        if (medicine == null) {
            model.addAttribute("GetMedicineError", "Лекарства с таким названием нет");
        } else {
            model.addAttribute("medicine", medicine);
        }
        return medicine;
    }

    /**
     * Updates an existing medicine entry in the system.
     *
     * @param medicine the updated medicine object
     * @param name the original name of the medicine
     * @param model model to add attributes for the view
     * @return the name of the view to display
     */
    public String updateMedicinePost(Medicine medicine, String name, Model model) {
        Medicine existingMedicine = medicineRepository.findByName(name);

        if (existingMedicine==null){
            model.addAttribute("message", "Лекарство не найдено");
        } else {
            existingMedicine.setName(medicine.getName());
            existingMedicine.setDescription(medicine.getDescription());
            existingMedicine.setDoctorPrescription(medicine.isDoctorPrescription());
            existingMedicine.setAvailability(medicine.isAvailability());
            existingMedicine.setPrice(medicine.getPrice());

            medicineRepository.save(existingMedicine);
            model.addAttribute("message", "Лекарство изменено");
        }
        return "editMedicine";
    }

    /**
     * Deletes a medicine by its name.
     *
     * @param name the name of the medicine to be deleted
     * @param redirectAttributes attributes for redirecting with a message
     * @param model model to add attributes for the view
     * @return a redirect URL to the delete medicine page
     */
    public String deleteMedicine(String name, RedirectAttributes redirectAttributes, Model model){
        Medicine medicine=medicineRepository.findByName(name.toLowerCase());
        if (medicine == null) {
            redirectAttributes.addFlashAttribute("message", "Лекарства с таким названием нет");
        } else {
            medicineRepository.delete(medicineGet(name, model));
            redirectAttributes.addFlashAttribute("message", "Лекарство удалено");
        }
        return "redirect:/admin/deleteMedicine";
    }

}

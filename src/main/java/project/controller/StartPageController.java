package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.hibernate.model.Medicine;
import project.hibernate.repository.MedicineRepository;
import project.service.MedicineService;

@Controller()
public class StartPageController {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/")
    public String run(){
        return "startPage";
    }

    @GetMapping("/contactInformation")
    public String contactInformation(){
        return "contactInformation";
    }

    @GetMapping("/medicines")
    public String medicines(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model){
        Page<Medicine> medicinePage = medicineService.getAllProducts(page, size);

        model.addAttribute("medicines", medicinePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", medicinePage.getTotalPages());
        model.addAttribute("totalItems", medicinePage.getTotalElements());
        return "medicine";
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


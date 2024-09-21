package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.DTO.OrderDTO;
import project.config.securityConfig.UserInfo;
import project.service.OrderService.UserOrderService;

@Controller
@RequestMapping("/user")
public class UserOrderController {

    @Autowired
    private UserOrderService orderService;

    @Autowired
    private UserInfo userInfo;

    @GetMapping("/order")
    public String order(Model model) {
        return orderService.orderStatus(model);
    }

    @PostMapping("/removeMedicine")
    public String deleteMedicine(@RequestParam("medicineId") int medicineId) {
        orderService.removeMedicineById(medicineId);
        return "redirect:/user/order";
    }

    @PostMapping("/quantity")
    @ResponseBody
    public ResponseEntity<String> updateQuantity(@RequestParam("medicineId") int medicineId, @RequestParam("quantity") Integer quantity) {
        orderService.updateMedicineQuantity(medicineId, quantity);
        return ResponseEntity.ok("Quantity updated successfully");
    }

    @PostMapping("/order")
    public String finishOrder(@RequestParam("address") String address,
                              @RequestParam("totalPrice") double totalPrice,
                              RedirectAttributes redirectAttributes) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAddress(address);
        orderService.placingOrder(orderDTO, totalPrice, redirectAttributes);
        return "redirect:/user/order";
    }

}


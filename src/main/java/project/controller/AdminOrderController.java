package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.service.OrderService.AdminOrderService;

@Controller
@RequestMapping("/admin")
public class AdminOrderController {

    @Autowired
    private AdminOrderService adminOrderService;

    @GetMapping("/getAllOrders")
    public String getAllOrders(Model model){
        model.addAttribute("orders", adminOrderService.getAllPlacingOrders("Ожидание заказа"));
        return "getAllOrders";
    }

    @GetMapping("/order/{id}")
    public String getOrderDetails(@PathVariable("id") int id, Model model) {
        model.addAttribute("orderItems", adminOrderService.getOrderDetails(id));
        model.addAttribute("amount", adminOrderService.getOrder(id).getAmount());
        model.addAttribute("address", adminOrderService.getOrder(id).getAddress());
        model.addAttribute("orderId", id);
        return "orderDetails";
    }

    @PostMapping("/order/{id}")
    public String redirectOrderStatus(@PathVariable("id") int id) {
        adminOrderService.redirectOrderStatus(id);
        return "redirect:/admin/getAllOrders";
    }
}

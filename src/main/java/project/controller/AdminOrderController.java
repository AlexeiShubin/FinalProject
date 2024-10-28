package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.service.OrderService.AdminOrderService;

/**
 * Controller for managing administrative tasks related to orders.
 * This controller handles requests for viewing and updating orders in the application.
 */
@Controller
@RequestMapping("/admin")
public class AdminOrderController {

    private static final Logger logger = LoggerFactory.getLogger(AdminOrderController.class);

    @Autowired
    private AdminOrderService adminOrderService;

    /**
     * Handles GET requests to retrieve and display all orders with the status "Ожидание заказа".
     *
     * @param model the model to be passed to the view
     * @return the name of the view that displays all orders, or an error page if an exception occurs
     */
    @GetMapping("/getAllOrders")
    public String getAllOrders(Model model) {
        try {
            model.addAttribute("orders", adminOrderService.getAllPlacingOrders("Ожидание заказа"));
            return "getAllOrders";
        } catch (Exception e) {
            logger.error("Ошибка получения всех заказов: ", e);
            return "errorPage";
        }
    }

    /**
     * Handles GET requests to retrieve details of a specific order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @param model the model to be passed to the view
     * @return the name of the view that displays the order details, or an error page if an exception occurs
     */
    @GetMapping("/order/{id}")
    public String getOrderDetails(@PathVariable("id") int id, Model model) {
        try {
            model.addAttribute("orderItems", adminOrderService.getOrderDetails(id));
            model.addAttribute("amount", adminOrderService.getOrder(id).getAmount());
            model.addAttribute("address", adminOrderService.getOrder(id).getAddress());
            model.addAttribute("orderId", id);
            return "orderDetails";
        } catch (Exception e) {
            logger.error("Ошибка получения деталей заказа с ID {}: ", id, e);
            return "errorPage";
        }
    }

    /**
     * Handles POST requests to update the status of a specific order by its ID.
     *
     * @param id the ID of the order to update
     * @return a redirect to the orders list page after updating the order status
     */
    @PostMapping("/order/{id}")
    public String redirectOrderStatus(@PathVariable("id") int id) {
        try {
            adminOrderService.redirectOrderStatus(id);
            return "redirect:/admin/getAllOrders";
        } catch (Exception e) {
            logger.error("Ошибка изменения статуса заказа с ID {}: ", id, e);
            return "redirect:/admin/getAllOrders?error=true";
        }
    }
}
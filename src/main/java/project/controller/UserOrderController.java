package project.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.DTO.OrderDTO;
import project.config.securityConfig.UserInfo;
import project.service.OrderService.UserOrderService;
/**
 * Controller for managing user orders and related actions.
 * This controller handles retrieving order status, updating order quantities,
 * placing orders, and removing items from the user's order.
 */

@Controller
@RequestMapping("/user")
public class UserOrderController {

    private static final Logger logger = LogManager.getLogger(UserOrderController.class);

    @Autowired
    private UserOrderService orderService;

    @Autowired
    private UserInfo userInfo;

    /**
     * Handles GET requests to retrieve and display the user's order status.
     *
     * @param model the model to be passed to the view
     * @return the name of the order status view or an error page in case of an exception
     */
    @GetMapping("/order")
    public String order(Model model) {
        try {
            return orderService.orderStatus(model);
        } catch (Exception e) {
            logger.error("Error retrieving order status: {}", e.getMessage(), e);
            return "errorPage";
        }
    }

    /**
     * Handles POST requests to remove a medicine from the user's order.
     *
     * @param medicineId the ID of the medicine to be removed
     * @param redirectAttributes attributes to pass to the redirect
     * @return a redirect to the order page
     */
    @PostMapping("/removeMedicine")
    public String deleteMedicine(@RequestParam("medicineId") int medicineId, RedirectAttributes redirectAttributes) {
        try {
            orderService.removeMedicineById(medicineId);
        } catch (Exception e) {
            logger.error("Error removing medicine with id {}: {}", medicineId, e.getMessage(), e);
        }
        return "redirect:/user/order";
    }

    /**
     * Handles POST requests to update the quantity of a specific medicine in the order.
     *
     * @param medicineId the ID of the medicine whose quantity is to be updated
     * @param quantity the new quantity for the medicine
     * @return a ResponseEntity indicating the success or failure of the update
     */
    @PostMapping("/quantity")
    @ResponseBody
    public ResponseEntity<String> updateQuantity(@RequestParam("medicineId") int medicineId, @RequestParam("quantity") Integer quantity) {
        try {
            orderService.updateMedicineQuantity(medicineId, quantity);
            return ResponseEntity.ok("Quantity updated successfully");
        } catch (Exception e) {
            logger.error("Error updating quantity for medicine with id {}: {}", medicineId, e.getMessage(), e);
            return ResponseEntity.status(500).body("Error updating quantity");
        }
    }

    /**
     * Handles POST requests to place an order with the specified address and total price.
     *
     * @param address the address for order delivery
     * @param totalPrice the total price of the order
     * @param redirectAttributes attributes to pass to the redirect
     * @return a redirect to the order page
     */
    @PostMapping("/order")
    public String finishOrder(@RequestParam("address") String address,
                              @RequestParam("totalPrice") double totalPrice,
                              RedirectAttributes redirectAttributes) {
        try {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setAddress(address);
            orderService.placingOrder(orderDTO, totalPrice, redirectAttributes);
        } catch (Exception e) {
            logger.error("Error placing order: {}", e.getMessage(), e);
            return "redirect:/user/order";
        }
        return "redirect:/user/order";
    }
}

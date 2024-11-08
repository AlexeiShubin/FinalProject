package project.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.DTO.OrderDTO;
import project.hibernate.model.*;
import project.hibernate.repository.MedicineRepository;
import project.hibernate.repository.OrderItemsRepository;
import project.hibernate.repository.OrderRepository;
import project.hibernate.repository.RecipeRepository;
import project.config.securityConfig.UserInfo;
import project.service.RecipeService;

import java.util.*;

/**
 * Service class for managing user order operations.
 * This class provides methods to create orders, add medicines to orders, and manage order items.
 */
@Service
public class UserOrderService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private UserInfo userInfo;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private  RecipeService recipeService;

    /**
     * Creates a new order for the current user.
     *
     * @return the created order object
     */
    @Transactional
    public Order createOrder() {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setUser(userInfo.getUser());
        order.setStatus("Оформление заказа");
        order.setAmount(0);
        return orderRepository.save(order);
    }

    /**
     * Adds a medicine to the specified order, incrementing the quantity if the item already exists.
     *
     * @param order   the order to which the medicine will be added
     * @param medicine the medicine to add to the order
     */
    @Transactional
    public void orderItems(Order order, Medicine medicine) {
        Optional<OrderItems> existingOrderItemOpt = orderItemsRepository.findByOrderAndMedicine(order, medicine);

        if (existingOrderItemOpt.isPresent()) {
            OrderItems existingOrderItem = existingOrderItemOpt.get();
            existingOrderItem.setQuantity(existingOrderItem.getQuantity() + 1);
            orderItemsRepository.save(existingOrderItem);
        } else {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(order);
            orderItem.setMedicine(medicine);
            orderItem.setQuantity(1);
            orderItemsRepository.save(orderItem);
        }
    }

    /**
     * Adds a medicine to the current order for the specified user, managing any existing orders.
     *
     * @param user              the user who is adding the medicine
     * @param medicine          the medicine to add to the order
     * @param redirectAttributes attributes for redirecting with a message
     */
    @Transactional
    public void addMedicineToCurrentOrder(User user, Medicine medicine, RedirectAttributes redirectAttributes) {
        try {
            if (orderRepository.findByUserIdAndStatus(user.getId(), "Ожидание заказа").isPresent()) {
                redirectAttributes.addFlashAttribute("error", "У вас есть заказ в обработке");
            } else {
                Optional<Order> currentOrderOpt = orderRepository.findByUserIdAndStatus(user.getId(), "Оформление заказа");

                Order currentOrder = currentOrderOpt.orElseGet(this::createOrder);

                orderItems(currentOrder, medicine);
                redirectAttributes.addFlashAttribute("success", "Лекарство успешно добавлено в заказ");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Произошла ошибка при добавлении лекарства: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Shows the order items for the specified user and status.
     *
     * @param user the user whose order items are to be displayed
     * @param str  the status of the orders to retrieve
     * @return a list of order items for the specified user and status
     */
    public List<OrderItems> showOrder(User user, String str) {
        Optional<Order> order = orderRepository.findByUserIdAndStatus(user.getId(), str);
        if (order.isPresent()) {
            return orderItemsRepository.findByOrderId(order.get().getId());
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves the order items for the current user based on the specified status.
     *
     * @param str the status of the orders to retrieve
     * @return a list of order items for the current user
     */
    public List<OrderItems> getOrder(String str) {
        return showOrder(userInfo.getUser(), str);
    }

    /**
     * Removes a medicine from the current user's order by medicine ID.
     *
     * @param medicineId the ID of the medicine to be removed
     */
    @Transactional
    public void removeMedicineById(int medicineId) {
        Optional<Order> currentOrderOpt = orderRepository.findByUserIdAndStatus(userInfo.getUser().getId(), "Оформление заказа");
        if (currentOrderOpt.isPresent()) {
            Order currentOrder = currentOrderOpt.get();
            Optional<OrderItems> orderItemOpt = orderItemsRepository.findByOrderAndMedicine(currentOrder, medicineRepository.findById(medicineId));
            orderItemOpt.ifPresent(orderItemsRepository::delete);
        }
    }

    /**
     * Places an order for the current user based on the provided OrderDTO and amount.
     * This method validates the order details, checks for prescription requirements,
     * and ensures that necessary order items are present before finalizing the order.
     *
     * @param orderDTO         the OrderDTO containing relevant order details
     * @param amount           the total amount for the order
     * @param redirectAttributes attributes for redirecting with messages
     */
    @Transactional
    public void placingOrder(@Validated OrderDTO orderDTO, double amount, RedirectAttributes redirectAttributes) {
        String address = sanitizeAddress(orderDTO.getAddress());

        Medicine missingRecipeMedicine = medicineHasRecipe();
        if (missingRecipeMedicine != null) {
            redirectAttributes.addFlashAttribute("message", "У вас нет рецепта к лекарству \"" + missingRecipeMedicine.getName() + "\"");
            return;
        }

        Optional<Order> optionalOrder = orderRepository.findByUserIdAndStatus(userInfo.getUser().getId(), "Оформление заказа");
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            List<OrderItems> orderItems = orderItemsRepository.findByOrderId(order.getId());

            if (orderItems.isEmpty()) {
                redirectAttributes.addFlashAttribute("message", "Ваш заказ пуст");
            } else {
                order.setOrderDate(new Date());
                order.setAddress(address);
                order.setAmount(amount);
                order.setStatus("Ожидание заказа");
                orderRepository.save(order);
            }
        }
    }

    /**
     * Sanitizes the given address by removing potentially dangerous characters.
     *
     * @param address the address string to sanitize
     * @return a sanitized address string
     */
    private String sanitizeAddress(String address) {
        return address.replaceAll("[<>\"'%;()&]", "");
    }

    /**
     * Updates the quantity of a specific medicine in the current user's order.
     *
     * @param medicineId the ID of the medicine to update
     * @param quantity   the new quantity for the medicine
     */
    @Transactional
    public void updateMedicineQuantity(int medicineId, Integer quantity) {
        Optional<Order> orderOpt = orderRepository.findByUserIdAndStatus(userInfo.getUser().getId(), "Оформление заказа");
        if (orderOpt.isPresent()) {
            Order order = orderOpt.get();
            Optional<OrderItems> orderItemOpt = orderItemsRepository.findByOrderAndMedicine(order, medicineRepository.findById(medicineId));
            if (orderItemOpt.isPresent()) {
                OrderItems orderItem = orderItemOpt.get();
                orderItem.setQuantity(quantity);
                orderItemsRepository.save(orderItem);
            }
        }
    }

    /**
     * Retrieves a list of order items that require a doctor's prescription.
     *
     * @return a list of order items with medicines requiring a prescription
     */
    private List<OrderItems> medicineWithRecipe() {
        List<OrderItems> medicineWithRecipe = new ArrayList<>();
        for (OrderItems item : getOrder("Оформление заказа")) {
            if (item.getMedicine().isDoctorPrescription()) {
                medicineWithRecipe.add(item);
            }
        }
        return medicineWithRecipe;
    }

    /**
     * Checks if there are any medicines in the current order that do not have a valid prescription.
     *
     * @return a Medicine object that does not have a valid recipe, or null if all have valid prescriptions
     */
    public Medicine medicineHasRecipe() {
        Medicine medicineWithoutRecipe = null;
        for (OrderItems item : medicineWithRecipe()) {
            Optional<Recipe> recipeOpt = recipeRepository.findByUserAndMedicineAndStatus(userInfo.getUser(), item.getMedicine(), "продлено");

            if (recipeOpt.isEmpty() || recipeService.checkingDeadlines(recipeOpt.get())) {
                medicineWithoutRecipe = item.getMedicine();
                break;
            }
        }
        return medicineWithoutRecipe;
    }
    /**
     * Retrieves the status of the user's order and updates the model accordingly.
     *
     * @param model the model to add attributes to
     * @return a String representing the name of the view to be rendered
     */
    public String orderStatus(Model model) {
        Optional<Order> orderOptOj = orderRepository.findByUserIdAndStatus(userInfo.getUser().getId(), "Ожидание заказа");
        Optional<Order> orderOptOf = orderRepository.findByUserIdAndStatus(userInfo.getUser().getId(), "Оформление заказа");
        if (orderOptOj.isPresent()) {
            model.addAttribute("orderItems", getOrder("Ожидание заказа"));
            return "finishOrderUser";
        } else if (orderOptOf.isPresent()) {
            model.addAttribute("orderItems", getOrder("Оформление заказа"));
            return "order";
        } else {
            createOrder();
            return "order";
        }
    }
}

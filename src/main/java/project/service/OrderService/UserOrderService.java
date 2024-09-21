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

    @Transactional
    public Order createOrder() {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setUser(userInfo.getUser());
        order.setStatus("Оформление заказа");
        order.setAmount(0);
        return orderRepository.save(order);
    }

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

    public List<OrderItems> showOrder(User user, String str) {
        Optional<Order> order = orderRepository.findByUserIdAndStatus(user.getId(), str);
        if (order.isPresent()) {
            return orderItemsRepository.findByOrderId(order.get().getId());
        }
        return Collections.emptyList();
    }

    public List<OrderItems> getOrder(String str) {
        return showOrder(userInfo.getUser(), str);
    }

    @Transactional
    public void removeMedicineById(int medicineId) {
        Optional<Order> currentOrderOpt = orderRepository.findByUserIdAndStatus(userInfo.getUser().getId(), "Оформление заказа");
        if (currentOrderOpt.isPresent()) {
            Order currentOrder = currentOrderOpt.get();
            Optional<OrderItems> orderItemOpt = orderItemsRepository.findByOrderAndMedicine(currentOrder, medicineRepository.findById(medicineId));
            orderItemOpt.ifPresent(orderItemsRepository::delete);
        }
    }

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

    private String sanitizeAddress(String address) {
        return address.replaceAll("[<>\"'%;()&]", "");
    }

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

    private List<OrderItems> medicineWithRecipe() {
        List<OrderItems> medicineWithRecipe = new ArrayList<>();
        for (OrderItems item : getOrder("Оформление заказа")) {
            if (item.getMedicine().isDoctorPrescription()) {
                medicineWithRecipe.add(item);
            }
        }
        return medicineWithRecipe;
    }

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

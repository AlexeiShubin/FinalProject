package project.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.hibernate.model.Order;
import project.hibernate.model.OrderItems;
import project.hibernate.repository.OrderItemsRepository;
import project.hibernate.repository.OrderRepository;

import java.util.List;

/**
 * Service class for managing administrative order operations.
 * This class provides methods to retrieve orders, retrieve order details, and
 * update the status of an order.
 */
@Service
public class AdminOrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;

    /**
     * Retrieves all orders with the specified status.
     *
     * @param status the status of the orders to retrieve
     * @return a list of orders with the given status
     */
    public List<Order> getAllPlacingOrders(String status){
        return orderRepository.findByStatus(status);
    }

    /**
     * Retrieves the details of the order items belonging to a specified order.
     *
     * @param id the ID of the order whose items are to be retrieved
     * @return a list of order items for the specified order
     */
    public List<OrderItems>  getOrderDetails(int id){
        return orderItemsRepository.findByOrderId(id);
    }

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @return the order object if found, null otherwise
     */
    public Order getOrder(int id){
        return orderRepository.findById(id);
    }

    /**
     * Updates the status of an order to "Completed".
     *
     * @param id the ID of the order to update
     */
    public void redirectOrderStatus(int id){
        Order order=getOrder(id);
        order.setStatus("Выполнено");
        orderRepository.save(order);
    }
}

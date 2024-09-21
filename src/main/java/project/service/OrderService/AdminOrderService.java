package project.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.hibernate.model.Order;
import project.hibernate.model.OrderItems;
import project.hibernate.repository.OrderItemsRepository;
import project.hibernate.repository.OrderRepository;

import java.util.List;

@Service
public class AdminOrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;

    public List<Order> getAllPlacingOrders(String status){
        return orderRepository.findByStatus(status);
    }

    public List<OrderItems>  getOrderDetails(int id){
        return orderItemsRepository.findByOrderId(id);
    }

    public Order getOrder(int id){
        return orderRepository.findById(id);
    }

    public void redirectOrderStatus(int id){
        Order order=getOrder(id);
        order.setStatus("Выполнено");
        orderRepository.save(order);
    }
}

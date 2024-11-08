package project.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.hibernate.model.Medicine;
import project.hibernate.model.Order;
import project.hibernate.model.OrderItems;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
    Optional<OrderItems>findByOrderAndMedicine(Order order, Medicine medicine);
    List<OrderItems> findByOrderId(Integer orderId);
}

package project.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.hibernate.model.Order;
import project.hibernate.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByUserIdAndStatus(int userId, String status);
    Optional<Order> findByUser(User user);
    List<Order> findByStatus(String status);
    Order findById(int id);
}

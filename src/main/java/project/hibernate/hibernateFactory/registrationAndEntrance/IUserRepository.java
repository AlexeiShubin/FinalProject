package project.hibernate.hibernateFactory.registrationAndEntrance;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hibernate.hibernateObjectFactory.User;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByPhone(String phone);
}

package project.hibernate.hibernateFactory.registrationAndEntrance;

import project.hibernate.hibernateObjectFactory.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

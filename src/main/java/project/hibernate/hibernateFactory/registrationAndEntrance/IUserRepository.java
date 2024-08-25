package project.hibernate.hibernateFactory.registrationAndEntrance;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hibernate.hibernateObjectFactory.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
    User findByPhone(String phone);
}

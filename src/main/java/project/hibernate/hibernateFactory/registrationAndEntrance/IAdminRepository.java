package project.hibernate.hibernateFactory.registrationAndEntrance;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hibernate.hibernateObjectFactory.Administrator;

import java.util.Optional;

public interface IAdminRepository extends JpaRepository<Administrator, Integer> {
    Optional<Administrator> findByPhone(String phone);
}

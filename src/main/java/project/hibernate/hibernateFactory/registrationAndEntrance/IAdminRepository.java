package project.hibernate.hibernateFactory.registrationAndEntrance;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hibernate.hibernateObjectFactory.Administrator;

public interface IAdminRepository extends JpaRepository<Administrator, Integer> {
    Administrator findByPhone(String phone);
}

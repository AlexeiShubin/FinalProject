package project.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hibernate.model.Blacklist;
import project.hibernate.model.User;

import java.util.Optional;

public interface BlacklistRepository extends JpaRepository<Blacklist, Integer> {
    Optional<Blacklist> findByPhone(String phone);
}

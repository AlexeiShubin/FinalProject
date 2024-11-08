package project.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.hibernate.model.Role;
import project.hibernate.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.phone = :phone")
    User findByPhone(String phone);
    @Query("SELECT u FROM User u WHERE u.role.role = :roleName")
    List<User> findByRoleName(@Param("roleName") String roleName);
    User findById(int id);
}
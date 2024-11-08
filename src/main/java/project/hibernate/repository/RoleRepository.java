package project.hibernate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.hibernate.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findById(int id);
}

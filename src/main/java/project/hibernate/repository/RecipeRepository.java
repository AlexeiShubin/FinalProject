package project.hibernate.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.hibernate.model.Medicine;
import project.hibernate.model.Recipe;
import project.hibernate.model.User;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByUser(User user);
    Optional<Recipe> findByUserAndMedicineAndStatus(User user, Medicine medicine, String status);
    Page<Recipe> findByStatusIn(List<String> statuses, Pageable pageable);
    Optional<Recipe> findByUserAndMedicine(User user, Medicine medicine);
}

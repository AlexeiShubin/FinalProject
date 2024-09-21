package project.hibernate.repository;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.hibernate.model.Medicine;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine,Integer> {
    Medicine findByName(String medicine);
    Medicine findById(int id);

    Page<Medicine> findAll(Pageable pageable);


}

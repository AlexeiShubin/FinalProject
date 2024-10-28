package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.hibernate.model.Medicine;
import project.hibernate.repository.MedicineRepository;

/**
 * Service class for managing operations related to medicines.
 * This class provides methods for retrieving medicine products and managing them.
 */
@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    /**
     * Retrieves a paginated list of all medicine products, sorted by name in ascending order.
     *
     * @param page the page number to retrieve
     * @param size the number of items per page
     * @return a Page object containing the list of medicine products
     */
    public Page<Medicine> getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return medicineRepository.findAll(pageable);
    }
}

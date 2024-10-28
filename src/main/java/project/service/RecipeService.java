package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import project.hibernate.model.Medicine;
import project.hibernate.model.Recipe;
import project.hibernate.repository.MedicineRepository;
import project.hibernate.repository.RecipeRepository;
import project.config.securityConfig.UserInfo;

import java.util.*;

/**
 * Service class for managing recipes related to medicines.
 * This class provides functionality for retrieving, creating, and managing recipes for the current user.
 */
@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserInfo userInfo;
    @Autowired
    private MedicineRepository medicineRepository;

    /**
     * Retrieves a list of recipes associated with the current user.
     *
     * @return a list of recipes for the current user
     */
    public List<Recipe> getRecipes() {
        return recipeRepository.findByUser(userInfo.getUser());
    }

    /**
     * Retrieves a paginated list of recipes that are either "creating" or "extending".
     *
     * @param page the page number to retrieve
     * @param size the number of items per page
     * @return a Page object containing the list of recipes
     */
    public Page<Recipe> getAllBid(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return recipeRepository.findByStatusIn(Arrays.asList("создание", "продление"), pageable);
    }

    /**
     * Adds a new recipe for the specified medicine.
     *
     * @param medicine the medicine to associate with the new recipe
     */
    private void addRecipe(Medicine medicine) {
        Recipe recipe = new Recipe();
        recipe.setUser(userInfo.getUser());
        recipe.setMedicine(medicine);
        recipe.setStatus("создание");
        recipeRepository.save(recipe);
    }

    /**
     * Requests a recipe for a specified medicine identified by its ID.
     * If an existing recipe is found and it is expired, it changes the status to "extending".
     * If no existing recipe is found, it creates a new recipe.
     *
     * @param medicineId the ID of the medicine for which to request a recipe
     */
    public void requestRecipe(int medicineId) {
        Optional<Recipe> recipeOpt = recipeRepository.findByUserAndMedicine(userInfo.getUser(), medicineRepository.findById(medicineId));
        if (recipeOpt.isPresent()) {
            Recipe recipe = recipeOpt.get();
            checkingDeadlines(recipe);
            if (recipe.getStatus().equals("прострочено")) {
                if (recipe.getExpirationDate() != null) {
                    recipe.setStatus("продление");
                    recipeRepository.save(recipe);
                }
            }
        } else {
            addRecipe(medicineRepository.findById(medicineId));
        }

    }

    /**
     * Extends the request for a recipe identified by its ID.
     * Updates the recipe status and sets new issue and expiration dates.
     *
     * @param id the ID of the recipe to extend
     * @return true if the recipe was successfully extended, false otherwise
     */
    public boolean extendRequestById(int id) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(id);
        if (recipeOpt.isPresent()) {
            Recipe recipe = recipeOpt.get();

            recipe.setStatus("продлено");
            recipe.setIssueDate(new Date());
            recipe.setExpirationDate(new Date());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(recipe.getExpirationDate());
            calendar.add(Calendar.MONTH, 1);
            recipe.setExpirationDate(calendar.getTime());

            recipeRepository.save(recipe);
            return true;
        }
        return false;
    }

    /**
     * Checks the deadlines for the given recipe and updates its status accordingly.
     * If the current date is after the expiration date, the status is set to "прострочено".
     *
     * @param recipe the Recipe object to check
     * @return false if the recipe is not expired
     */
    public boolean checkingDeadlines(Recipe recipe){
        Date dateNow=new Date();
        if (recipe.getExpirationDate()!=null) {
            if (!dateNow.before(recipe.getExpirationDate())) {
                recipe.setStatus("просрочено");
            }
        }
        return false;
    }
}

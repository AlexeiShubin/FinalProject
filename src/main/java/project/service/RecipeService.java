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

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserInfo userInfo;
    @Autowired
    private MedicineRepository medicineRepository;

    public List<Recipe> getRecipes() {
        return recipeRepository.findByUser(userInfo.getUser());
    }

    public Page<Recipe> getAllBid(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return recipeRepository.findByStatusIn(Arrays.asList("создание", "продление"), pageable);
    }

    private void addRecipe(Medicine medicine) {
        Recipe recipe = new Recipe();
        recipe.setUser(userInfo.getUser());
        recipe.setMedicine(medicine);
        recipe.setStatus("создание");
        recipeRepository.save(recipe);
    }

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

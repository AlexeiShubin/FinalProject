package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.hibernate.model.User;
import project.hibernate.repository.UserRepository;

import java.util.List;

/**
 * Service class for general technical operations related to users.
 * This class provides methods for retrieving user information and adding it to the model.
 */
@Service
public class GeneralTechnicalService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a list of users with the role "ROLE_USER" and adds it to the provided model.
     *
     * @param model the model to which the user list will be added
     */
    public void users(Model model) {
        List<User> userList = userRepository.findByRoleName("ROLE_USER");
        model.addAttribute("userList", userList);
    }
}

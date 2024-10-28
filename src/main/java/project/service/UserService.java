package project.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import project.hibernate.model.Role;
import project.hibernate.model.User;
import project.hibernate.repository.RoleRepository;
import project.hibernate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.config.securityConfig.UserInfo;
import project.service.adminService.BlockUser;

/**
 * Service class for managing user operations such as registration and information retrieval.
 * This class encapsulates the logic for user authentication, registration,
 * and retrieving user information.
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserInfo userInfo;
    @Autowired
    private BlockUser blockUser;

    /**
     * Registers a new user in the system.
     * Validates the user's phone number, checks if the user is blocked,
     * and verifies the password strength before saving.
     *
     * @param user the user object containing registration details
     * @return a String indicating the result of the registration (success or error identifier)
     */
    public String registerUser(User user) {
        User oldUser = userRepository.findByPhone(user.getPhone());

        if (oldUser != null) {
            return "phoneNumberError";
        } else if (!blockUser.blockUser(user.getPhone())) {
            return "BlockUserError";
        } else {
            String rawPassword = user.getPassword();
            if (!isValidPassword(rawPassword)) {
                return "invalidPassword";
            }

            user.setPassword(passwordEncoder.encode(rawPassword));

            Role role = roleRepository.findById(1);
            user.setRole(role);

            userRepository.save(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getPhone(), rawPassword);
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/myUserPage";
        }
    }
    /**
     * Validates the strength of the given password.
     * The password must be at least 8 characters long.
     *
     * @param password the password to validate
     * @return true if the password is valid; false otherwise
     */
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    /**
     * Adds the current user's information to the given model.
     * This is used for displaying user details in the UI.
     *
     * @param model the model to which user information will be added
     */
    public void userInfo(Model model) {
        User currentUser = userInfo.getUser();
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("surname", currentUser.getSurname());
        model.addAttribute("phone", currentUser.getPhone());
    }
}

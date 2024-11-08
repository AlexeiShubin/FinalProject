package project.config.securityConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project.hibernate.model.User;
import project.hibernate.repository.UserRepository;

/**
 * Component that provides user-related information from the security context.
 * This class retrieves the current authenticated user and their details from the UserRepository.
 */
@Component
public class UserInfo {

    private static final Logger logger = LoggerFactory.getLogger(UserInfo.class);

    @Autowired
    private UserRepository userRepository;
    /**
     * Retrieves the currently authenticated user from the security context.
     *
     * @return the User object representing the current authenticated user, or null if the user is not authenticated
     */
    public User getUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                logger.warn("Authentication object is null or the user is not authenticated.");
                return null;
            }

            String phone = authentication.getName();
            User user = userRepository.findByPhone(phone);

            if (user == null) {
                logger.warn("User with phone number {} not found.", phone);
                return null;
            }

            return user;
        } catch (Exception e) {
            logger.error("An error occurred while retrieving user information: ", e);
            return null;
        }
    }
}

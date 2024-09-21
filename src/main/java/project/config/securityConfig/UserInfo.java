package project.config.securityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import project.hibernate.model.User;
import project.hibernate.repository.UserRepository;

@Component
public class UserInfo {

    @Autowired
    private UserRepository userRepository;

    public User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String phone = authentication.getName();
        return userRepository.findByPhone(phone);
    }
}

package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hibernate.hibernateFactory.registrationAndEntrance.IAdminRepository;
import project.hibernate.hibernateFactory.registrationAndEntrance.IUserRepository;
import project.hibernate.hibernateObjectFactory.Administrator;
import project.hibernate.hibernateObjectFactory.User;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IAdminRepository adminRepository;

    public String authenticate(String phoneNumber, String password) {
        Optional<User> optionalUser = userRepository.findByPhone(phoneNumber);
        Optional<Administrator> optionalAdmin = adminRepository.findByPhone(phoneNumber);

        if (optionalAdmin.isPresent() && password.equals(optionalAdmin.get().getPassword())) {
            return "startPageForAdmin";
        } else if (optionalUser.isPresent() && password.equals(optionalUser.get().getPassword())) {
            return "startPageAfterEntrance";
        } else if (optionalAdmin.isEmpty() && optionalUser.isEmpty()) {
            return "userNotFound";
        }
        return "entrance";
    }

    public String registerUser(User user) {
        boolean userExists = userRepository.findByPhone(user.getPhone()).isPresent();
        boolean adminExists =adminRepository.findByPhone(user.getPhone()).isPresent();

        if (userExists || adminExists) {
            return "phoneNumberError";
        }
        return "startPageAfterRegistration";
    }
}
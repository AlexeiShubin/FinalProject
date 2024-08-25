package project.service.entranceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hibernate.hibernateFactory.registrationAndEntrance.IAdminRepository;
import project.hibernate.hibernateFactory.registrationAndEntrance.IUserRepository;
import project.hibernate.hibernateObjectFactory.Administrator;
import project.hibernate.hibernateObjectFactory.User;

@Service
public class Entrance {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAdminRepository adminRepository;

    public String authenticate(String phoneNumber, String password) {
        User user = userRepository.findByPhone(phoneNumber);
        Administrator admin = adminRepository.findByPhone(phoneNumber);

        if (admin != null && password.equals(admin.getPassword())) {
            return "startPageForAdmin";
        } else if (user != null && password.equals(user.getPassword())) {
            return "startPageAfterEntrance";
        }
        return "entrance";
    }
}
package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import project.hibernate.model.User;
import project.hibernate.repository.UserRepository;

import java.util.List;

@Service
public class GeneralTechnicalService {

    @Autowired
    private UserRepository userRepository;

    public void users(Model model) {
        List<User> userList = userRepository.findByRoleName("ROLE_USER");
        model.addAttribute("userList", userList);
    }
}

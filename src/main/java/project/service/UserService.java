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

    public String registerUser(User user) {
        if (!isValidPhoneNumber(user.getPhone())) {
            return "invalidPhoneNumber";
        }

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
    private boolean isValidPhoneNumber(String phone) {
        return phone != null && phone.matches("\\+?[0-9]{10,13}");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }

    public void userInfo(Model model) {
        User currentUser = userInfo.getUser();
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("surname", currentUser.getSurname());
        model.addAttribute("phone", currentUser.getPhone());
    }
}

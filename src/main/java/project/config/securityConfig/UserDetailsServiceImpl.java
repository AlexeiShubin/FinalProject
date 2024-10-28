package project.config.securityConfig;

import project.hibernate.model.User;
import project.hibernate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashSet;
import java.util.Set;
/**
 * Implementation of Spring Security's {@link UserDetailsService} interface.
 * This class is responsible for loading user-specific data during the authentication process.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    /**
     * Loads a user by their phone number.
     *
     * @param phone the phone number of the user to be loaded
     * @return the UserDetails object containing user information
     * @throws UsernameNotFoundException if the user with the specified phone number is not found
     */

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User user = userRepository.findByPhone(phone);
        if (user != null) {
            Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
            grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole().getRole()));
            return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), grantedAuthoritySet);
        } else {
            throw new UsernameNotFoundException("Пользователь с телефоном " + phone + "не найден!");
        }
    }
}

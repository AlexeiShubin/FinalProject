package project.service.adminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hibernate.model.Blacklist;
import project.hibernate.model.User;
import project.hibernate.repository.BlacklistRepository;
import project.hibernate.repository.UserRepository;

import java.util.Optional;

/**
 * Service class for managing user blocking and blacklisting operations.
 * This class provides methods to add users to a blacklist, delete users from the system,
 * and check if a user is already blocked.
 */
@Service
public class BlockUser {
    @Autowired
    private BlacklistRepository blacklistRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Adds a user to the blacklist using their phone number.
     *
     * @param phone the phone number of the user to be added to the blacklist
     */
    private void addUserInBlackList(String phone){
        Blacklist blacklist=new Blacklist();
        blacklist.setPhone(phone);
        blacklistRepository.save(blacklist);
    }
    /**
     * Deletes a user from the system and adds them to the blacklist.
     *
     * @param id the ID of the user to be deleted
     */
    public void deleteUser(int id){
        User user=userRepository.findById(id);
        addUserInBlackList(user.getPhone());
        userRepository.delete(user);
    }

    /**
     * Checks if a user is already blocked by phone number.
     *
     * @param phone the phone number of the user to check
     * @return true if the user is not blocked, false if the user is already in the blacklist
     */
    public boolean blockUser(String phone){
        Optional<Blacklist> blacklist=blacklistRepository.findByPhone(phone);
        return blacklist.isEmpty();
    }
}

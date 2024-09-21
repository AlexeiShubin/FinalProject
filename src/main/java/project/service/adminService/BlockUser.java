package project.service.adminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hibernate.model.Blacklist;
import project.hibernate.model.User;
import project.hibernate.repository.BlacklistRepository;
import project.hibernate.repository.UserRepository;

import java.util.Optional;

@Service
public class BlockUser {
    @Autowired
    private BlacklistRepository blacklistRepository;
    @Autowired
    private UserRepository userRepository;

    private void addUserInBlackList(String phone){
        Blacklist blacklist=new Blacklist();
        blacklist.setPhone(phone);
        blacklistRepository.save(blacklist);
    }
    public void deleteUser(int id){
        User user=userRepository.findById(id);
        addUserInBlackList(user.getPhone());
        userRepository.delete(user);
    }

    public boolean blockUser(String phone){
        Optional<Blacklist> blacklist=blacklistRepository.findByPhone(phone);
        return blacklist.isEmpty();
    }
}

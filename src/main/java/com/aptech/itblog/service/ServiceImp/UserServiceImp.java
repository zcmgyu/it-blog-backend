package com.aptech.itblog.service.ServiceImp;

import com.aptech.itblog.collection.User;
import com.aptech.itblog.exception.ConflictEmailException;
import com.aptech.itblog.repository.UserRepository;
import com.aptech.itblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class UserServiceImp {

    @Autowired
    private UserRepository userRepository;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }
        return new User(user);
    }

    public User addUser(User user) {
        if(checkEmailExists(user.getEmail())) {
            return null;
        } else {
            User registereddUser = userRepository.save(user);
            return registereddUser;
        }

    }

    public User loadUserByEmail(String email) throws ConflictEmailException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new ConflictEmailException(String.format("Email %s is already existed", email));
        }
        return user;
    }


    public List<User> getUserByUserName(String userName) {
        return null;
    }


    public List<User> getUserByName(String name) {
        return null;
    }


    public boolean checkEmailExists(String email) {
        User user = new User();
        user.setEmail(email);
//        userRepository.exists(user,
//                              new ExampleMatcher().withMatcher("email")
//                                                  .equals(user));
        return false;
    }


    public User getUserById(String user_id) {

        return userRepository.findOne(user_id);
    }


    public boolean updateUserDetail(User user) {

        userRepository.save(user);
        return true;
    }


    public boolean lockUser(String user_id) {

        User user = getUserById(user_id);
        user.setLocked(false);
        userRepository.save(user);
        return true;
    }


    public boolean activeUser(String user_id) {
        return false;
    }


    public void changePassword(String oldPass, String newPass) {

    }


    public void forgotPassword() {

    }
}

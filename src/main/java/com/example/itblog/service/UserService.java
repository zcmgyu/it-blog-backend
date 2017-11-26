package com.example.itblog.service;

import com.example.itblog.exception.ConflictEmailException;
import com.example.itblog.collection.User;
import com.example.itblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }
        return new User(user);
    }

    public User addUser(User user) {
        User registereddUser = userRepository.save(user);
        return registereddUser;
    }

    public User loadUserByEmail(String email) throws ConflictEmailException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new ConflictEmailException(String.format("Email %s is already existed", email));
        }
        return user;
    }
}
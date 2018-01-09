package com.aptech.itblog.service;

import com.aptech.itblog.collection.Role;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.exception.ConflictEmailException;
import com.aptech.itblog.repository.RoleRepository;
import com.aptech.itblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User %s does not exist!", username));
        }
        return new User(user);
    }

    /**
     * Fetch all pageable user list
     *
     * @param pageable
     * @return
     */
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    /**
     * Create a user
     *
     * @param user
     * @return
     */
    public User registerUser(User user) {
        // Setting roles for user
        if (user.getRoles() == null) {
            Role role = roleRepository.findByAuthority("USER");
            user.setRoles(new ArrayList() {
                {
                    add(role.getId());
                }
            });
            user.setAuthorities(new ArrayList() {
                {
                    add(role);
                }
            });
        } else {
            List<Role> roleList = roleRepository.findAllByIdIn(user.getRoles());
            user.setAuthorities(roleList);
        }

        // Encode password
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);


        // Set default enabled
        user.setEnabled(true);

        // Set created date
        user.setCreateAt(new Date());

        User registereddUser = userRepository.save(user);

        return registereddUser;
    }


    public User getUser(String userId) {
        User currentUser = fetchCurrentUser(userId);
        return currentUser;
    }

    /**
     * Update user info
     *
     * @param userId
     * @param user
     * @return
     */
    public User updateUser(String userId, User user) {
        User currentUser = fetchCurrentUser(userId);

        // Set update properties
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        List<String> roleIdList = user.getRoles();
        // Update Authorities
        currentUser.setRoles(roleIdList);
        List<Role> roleList = roleRepository.findAllByIdIn(roleIdList);
        currentUser.setAuthorities(roleList);
        currentUser.setEnabled(user.isEnabled());
        currentUser.setModifiedAt(new Date());
        // Save to DB
        userRepository.save(currentUser);

        return currentUser;
    }

    /**
     * Reset password
     * @param resetUser
     * @param password
     * @return
     */
    public User updatePassword(User resetUser, String password) {
        // Set new password
        // Encode password
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passEncoder.encode(password);
        resetUser.setPassword(hashedPassword);

        // Set update properties
        resetUser.setModifiedAt(new Date());
        // Save to DB
        userRepository.save(resetUser);

        return resetUser;
    }


    /**
     * Delete user
     *
     * @param userId
     * @return
     */
    public void deleteUser(String userId) {
        User currentUser = fetchCurrentUser(userId);
        userRepository.delete(currentUser);
    }


    public User loadUserByEmail(String email) throws ConflictEmailException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            throw new ConflictEmailException(String.format("Email %s is already existed", email));
        }
        return user;
    }


    private User fetchCurrentUser(String userId) {
        User currentUser;
        if ("self".equals(userId)) {
            currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } else {
            currentUser = userRepository.findById(userId);
        }
        return currentUser;
    }


}
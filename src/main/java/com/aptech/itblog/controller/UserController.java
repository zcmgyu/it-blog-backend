package com.aptech.itblog.controller;

import com.aptech.itblog.collection.Post;
import com.aptech.itblog.collection.Role;
import com.aptech.itblog.collection.User;
import com.aptech.itblog.exception.ConflictEmailException;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.repository.RoleRepository;
import com.aptech.itblog.repository.UserRepository;
import com.aptech.itblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.Valid;
import java.util.*;

import static com.aptech.itblog.common.CollectionLink.*;

@RestController
@RequestMapping(value = API)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserDetailsService userDetailsService; //Service which will do all data retrieval/manipulation work

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        System.out.println(name + " parameter is missing");
        // Actual exception handling
    }

    @SuppressWarnings("Duplicates")
    @PostMapping(value = USERS)
    @ResponseBody
    public ResponseEntity<?> register(@Valid @RequestBody User data) throws MissingServletRequestPartException {
        User user;
        try {
            user = (User) userDetailsService.loadUserByUsername(data.getUsername());

            if (user != null) {
                return new ResponseEntity<Object>(
                        new CommonResponseBody("RegisteredUser",
                                HttpStatus.CONFLICT.value(),
                                new HashMap() {
                                    {
                                        put("data", data.getId());
                                        put("message", "This username is already registered.");
                                    }
                                }),
                        HttpStatus.CONFLICT);
            }
        } catch (UsernameNotFoundException e) {
        }

        try {
            userService.loadUserByEmail(data.getEmail());
        } catch (ConflictEmailException e) {
            return new ResponseEntity<Object>(
                    new CommonResponseBody("RegisteredEmail",
                            HttpStatus.CONFLICT.value(),
                            new HashMap() {
                                {
                                    put("data", data.getId());
                                    put("message", "This email is already registered.");
                                }
                            }),
                    HttpStatus.CONFLICT);
        }

        // Encode password
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passEncoder.encode(data.getPassword());
        data.setPassword(hashedPassword);

        // Setting roles for user
        List<Role> roles = new ArrayList() {
            {
                add(roleRepository.findByAuthority("USER"));
            }
        };
        data.setAuthorities(roles);

        // Set default enabled
        data.setEnabled(true);

        // Set created date
        data.setCreateAt(new Date());

        // Save user to DB
        userService.addUser(data);

        return new ResponseEntity<Object>(new CommonResponseBody("OK", HttpStatus.OK.value(),
                new HashMap() {
                    {
                        put("data", data);
                        put("message", "You've been successfully registered.");
                    }
                }),
                HttpStatus.OK);
    }

    @GetMapping(value = USERS)
    public ResponseEntity<?> getUserList(@RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "25") Integer size) {
        // Create pageable
        Pageable pageable = new PageRequest(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        // Init a headers and add Content-Range
        HttpHeaders headers = new HttpHeaders() {
            {
                add("Access-Control-Expose-Headers", "Content-Range");
                add("Content-Range", String.valueOf(userPage.getTotalElements()));
            }
        };

        return new ResponseEntity<>(
                new CommonResponseBody("OK", 200,
                        new LinkedHashMap() {
                            {
                                put("data", userPage.getContent());
                            }
                        }), headers, HttpStatus.OK);

    }


    @GetMapping(value = USERS_ID)
    public ResponseEntity<?> getCurrentUser(@PathVariable("id") String userId) {
        // Define user
        User user = fetchCurrentUser(userId);

        return new ResponseEntity<Object>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", user);
            }
        }), HttpStatus.OK);
    }

    @PutMapping(value = USERS_ID)
    public ResponseEntity<?> updateUser(@PathVariable("id") String userId, @RequestBody User user) {
        User currentUser = fetchCurrentUser(userId);

        // Set update properties
        currentUser.setName(user.getName());
        currentUser.setEmail(user.getEmail());
        currentUser.setAuthorities((List<Role>) user.getAuthorities());
        currentUser.setEnabled(user.isEnabled());
        currentUser.setModifiedAt(new Date());
        // Save to DB
        userRepository.save(currentUser);

        return new ResponseEntity<Object>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", currentUser.getId());
            }
        }), HttpStatus.OK);
    }


    @DeleteMapping(value = USERS_ID)
    public ResponseEntity<?> deleteUser(@PathVariable("id") String userId) {
        User currentUser = fetchCurrentUser(userId);

        // Set update properties
        currentUser.setEnabled(false);
        currentUser.setModifiedAt(new Date());
        // Save to DB
        userRepository.save(currentUser);

        return new ResponseEntity<Object>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("data", currentUser.getId());
            }
        }), HttpStatus.OK);
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
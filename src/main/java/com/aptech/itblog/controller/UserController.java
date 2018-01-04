package com.aptech.itblog.controller;

import com.aptech.itblog.collection.User;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.aptech.itblog.common.CollectionLink.*;

@RestController
@RequestMapping(value = API)
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping(value = USERS)
    public ResponseEntity<?> getUserList() {
        List<User> userList = userRepository.findAll();

        return new ResponseEntity<Object>(
                new CommonResponseBody("OK", 200,
                        new HashMap() {
                            {
                                put("result", userList);
                            }
                        }), HttpStatus.OK);
    }

    @GetMapping(value = USERS_USER_ID)
    public ResponseEntity<?> getCurrentUser(@PathVariable("user_id") String user_id) {
        User user;
        if("self".equals(user_id)) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } else {
            user = userRepository.findBy_id(user_id);
        }
        return new ResponseEntity<Object>(new CommonResponseBody("OK", 200, new LinkedHashMap() {
            {
                put("_id", user.get_id());
                put("name", user.getName());
                put("username", user.getUsername());
                put("email", user.getEmail());
            }
        }), HttpStatus.OK);

    }
}
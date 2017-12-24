package com.aptech.itblog.controller;

import com.aptech.itblog.collection.User;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

import static com.aptech.itblog.common.CollectionLink.API;
import static com.aptech.itblog.common.CollectionLink.USERS;

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
}
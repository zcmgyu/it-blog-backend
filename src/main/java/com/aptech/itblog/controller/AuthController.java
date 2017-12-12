package com.aptech.itblog.controller;

import com.aptech.itblog.collection.User;
import com.aptech.itblog.exception.ConflictEmailException;
import com.aptech.itblog.model.CommonResponseBody;
import com.aptech.itblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    UserDetailsService userDetailsService; //Service which will do all data retrieval/manipulation work

    @Autowired
    UserService userService;

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public void handleMissingParams(MissingServletRequestParameterException ex) {
        String name = ex.getParameterName();
        System.out.println(name + " parameter is missing");
        // Actual exception handling
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
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
                                    put("message", "This email is already registered.");
                                }
                            }),
                    HttpStatus.CONFLICT);
        }

        // Encode password
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passEncoder.encode(data.getPassword());
        data.setPassword(hashedPassword);

        // Set default enabled
        data.setEnabled(true);
        userService.addUser(data);
//        return new ResponseEntity<Object>(
//                new CommonResponseBody("OK",
//                        HttpStatus.OK.value(), data,
//                        new CommonResult("You've been successfully registered.")),
//                HttpStatus.OK);


        return new ResponseEntity<Object>(new CommonResponseBody("OK", HttpStatus.OK.value(),
                new HashMap() {
                    {
                        put("message", "You've been successfully registered.");
                    }
                }),
                HttpStatus.OK);
    }
}

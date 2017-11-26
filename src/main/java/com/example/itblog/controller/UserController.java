package com.example.itblog.controller;

import com.example.itblog.collection.User;
import com.example.itblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping(value = "/hello")
    public String home() {
        return "home";
    }

}
package com.aptech.itblog.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class SecuredController {

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("USER")
    public String sayHello() {
        return "Secure Hello!";
    }

}

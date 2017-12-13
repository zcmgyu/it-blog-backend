package com.aptech.itblog.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    @RequestMapping(value = "/debug", method = RequestMethod.GET)
    @PreAuthorize("USER")
    public String home() {
        return "YOU HAVE ACCESSED TO DEBUG PAGE";
    }
}

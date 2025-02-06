package com.bookclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {

    //this loads the html page
    @RequestMapping("/register")
    public String signUp() {
        return "register";
    }

    @PostMapping("/register/confirmation")
    public String confirmation() {
        return "confirmation";
    }



}

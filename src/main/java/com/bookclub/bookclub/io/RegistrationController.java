package com.bookclub.bookclub.io;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {

    @RequestMapping("/register")
    public String signUp() {
        return "register";
    }

}

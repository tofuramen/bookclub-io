package com.bookclub.controller;

import com.bookclub.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {

    //this loads the html page and also adds a new User object to my model
    @RequestMapping("/register")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String confirmation(@Valid @ModelAttribute("user") User user, BindingResult result) {
        return "redirect:/confirmation";
    }



}

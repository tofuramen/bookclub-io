package com.bookclub.controller;

import com.bookclub.model.User;
import com.bookclub.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrationController {

    UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    //this loads the html page and also adds a new User object to my model
    @RequestMapping("/register")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @PostMapping("/register")
    public String confirmation(@Valid @ModelAttribute("user") User user, BindingResult result) {

        if(result.hasErrors()){
            return "register";
        }try {
            userService.registerUser(user);
        } catch (IllegalArgumentException e) {
            // Associate the error with the "username" field (or globally)
            result.rejectValue("username", "error.user", e.getMessage());
            return "register";
        }

        return "redirect:/confirmation";
    }

    @GetMapping("/confirmation")
    public String confirmationPage() {
        return "confirmation"; // Make sure you have a confirmation.html view.
    }



}

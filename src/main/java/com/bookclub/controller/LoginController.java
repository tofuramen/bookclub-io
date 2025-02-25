package com.bookclub.controller;

import com.bookclub.dto.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }

        model.addAttribute("loginForm", new LoginForm());

        return "login"; // This returns login.html from your templates folder
    }

}

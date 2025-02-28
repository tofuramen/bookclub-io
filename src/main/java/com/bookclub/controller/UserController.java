package com.bookclub.controller;

import com.bookclub.model.User;
import com.bookclub.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/profile")
    public String userProfile(Model model, Authentication authentication) {
        User currentUser = userService.findByUsername(authentication.getName());
        model.addAttribute("user", currentUser);
        return "user/profile"; // This corresponds to src/main/resources/templates/user/profile.html
    }


    @PostMapping("/user/updateBio")
    public String updateBio(@ModelAttribute("user") User updatedUser, Authentication authentication) {
        String currentUsername = authentication.getName();
        User existingUser = userService.findByUsername(currentUsername);
        existingUser.setBio(updatedUser.getBio());
        userService.save(existingUser);
        return "redirect:/user/profile";
    }
}

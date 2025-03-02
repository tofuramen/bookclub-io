package com.bookclub.controller;

import com.bookclub.dto.UserSettingsDTO;
import com.bookclub.model.User;
import com.bookclub.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
        model.addAttribute("currentlyReading", currentUser.getCurrentlyReading());
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

    @GetMapping("/settings")
    public String settings(Model model, Authentication authentication) {
        User currentUser = userService.findByUsername(authentication.getName());
        UserSettingsDTO settings = new UserSettingsDTO();
        settings.setBio(currentUser.getBio());
        model.addAttribute("settings", settings);
        return "user/settings";
    }

    // New endpoint for updating settings, including file upload
    @PostMapping(value = "/settings", consumes = {"multipart/form-data"})
    public String updateSettings(@ModelAttribute("settings") UserSettingsDTO settingsDto,
                                 @RequestParam(value = "profilePicture", required = false) MultipartFile profilePicture,
                                 Authentication authentication) {
        User currentUser = userService.findByUsername(authentication.getName());

        // Update the bio from the DTO
        currentUser.setBio(settingsDto.getBio());

        // Handle profile picture upload if a file was provided
        if (profilePicture != null && !profilePicture.isEmpty()) {
            // Implement your file upload logic, e.g., save the file and get a URL
            String url = userService.uploadProfilePicture(profilePicture);
            currentUser.setProfilePicUrl(url);
        }

        userService.save(currentUser);
        return "redirect:/user/profile";
    }


}

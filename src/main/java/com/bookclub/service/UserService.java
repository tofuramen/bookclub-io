package com.bookclub.service;

import com.bookclub.model.Role;
import com.bookclub.model.User;
import com.bookclub.repository.RoleRepository;
import com.bookclub.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerUser(User user) {
        // Optionally, check if the username or email is already taken.
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Encode the plain-text password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role defaultRole = roleRepository.findByName("ROLE_USER");
        if (defaultRole == null) {
            // Optionally, you can create and save it if it doesn't exist, but ideally it should already be in your db.
            throw new IllegalStateException("Default role not found in database");
        }
        user.getRoles().add(defaultRole);
        // Save the user entity and return the persisted instance
        return userRepository.save(user);

    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public String uploadProfilePicture(MultipartFile profilePicture) {
        String folder = "uploads/profile_pictures/";

        // Create a unique filename based on a UUID and the original file name
        String uniqueFileName = UUID.randomUUID().toString() + "_" + profilePicture.getOriginalFilename();
        File destinationFile = new File(folder + uniqueFileName);

        // Ensure the destination folder exists
        destinationFile.getParentFile().mkdirs();

        try {
            // Transfer the file data to the destination file
            profilePicture.transferTo(destinationFile);
            // Return a URL (or relative path) to the uploaded file
            return "/uploads/profile_pictures/" + uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

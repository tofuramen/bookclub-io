package com.bookclub.service;

import com.bookclub.model.Role;
import com.bookclub.model.User;
import com.bookclub.repository.RoleRepository;
import com.bookclub.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
}

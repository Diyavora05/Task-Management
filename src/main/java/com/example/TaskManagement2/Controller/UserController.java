package com.example.TaskManagement2.Controller;

import com.example.TaskManagement2.Service.UserService;
import com.example.TaskManagement2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public User getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return userService.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @PutMapping("/me")
    public User updateCurrentUser(@RequestBody User updatedUser, Authentication authentication) {
        String username = authentication.getName();
        return userService.updateUser(username, updatedUser);
    }

}

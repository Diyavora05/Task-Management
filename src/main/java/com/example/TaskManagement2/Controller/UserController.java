package com.example.TaskManagement2.Controller;

import com.example.TaskManagement2.Service.UserService;
import com.example.TaskManagement2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }
    @PostMapping("/create")
    public User registerUser(@RequestBody User user) {
       return userService.registerUser(user);
    }
    @PutMapping("/update/{username}")
    public User updateUser(@PathVariable String username, @RequestBody User user) {
        return userService.updateUser(username, user);
    }
    @DeleteMapping("/delete/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }



}

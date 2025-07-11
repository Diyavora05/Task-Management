package com.example.TaskManagement2.Controller;

import com.example.TaskManagement2.Service.TaskService;
import com.example.TaskManagement2.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
        String username = getCurrentUsername();
        return taskService.createTask(task, username);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        String username = getCurrentUsername();
        return taskService.getAllTasks(username);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) {
        String username = getCurrentUsername();
        return taskService.getTaskById(id, username);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task) {
        String username = getCurrentUsername();
        return taskService.updateTask(id, task, username);
    }

    @PatchMapping("/{id}/status")
    public Task updateStatus(@PathVariable String id, @RequestParam String status) {
        String username = getCurrentUsername();
        return taskService.updateStatus(id, status, username);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        String username = getCurrentUsername();
        taskService.deleteTask(id, username);
    }
}


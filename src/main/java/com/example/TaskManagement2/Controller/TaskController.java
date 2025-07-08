package com.example.TaskManagement2.Controller;

import com.example.TaskManagement2.Service.TaskService;
import com.example.TaskManagement2.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // username from JWT token
    }

    // GET SINGLE TASK by id for logged-in user
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable String id) {
        String username = getCurrentUsername();
        return taskService.getTaskById(id, username);
    }

    // GET ALL TASKS for logged-in user
    @GetMapping
    public List<Task> getAllTasks() {
        String username = getCurrentUsername();
        return taskService.getAllTasks(username);
    }

    // GET TASK BY TITLE for logged-in user
    @GetMapping("/title/{title}")
    public Task getTaskByTitle(@PathVariable String title) {
        String username = getCurrentUsername();
        return taskService.getTaskByTitle(title, username)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // CREATE TASK for logged-in user
    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
        String username = getCurrentUsername();
        return taskService.createTask(task, username);
    }

    // UPDATE TASK for logged-in user
    @PutMapping("/updatetask/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task task) {
        String username = getCurrentUsername();
        return taskService.updateTask(id, task, username);
    }

    // UPDATE STATUS for logged-in user
    @PutMapping("/updatestatus/{id}")
    public Task updateStatus(@PathVariable String id, @RequestParam String status) {
        String username = getCurrentUsername();
        return taskService.updateStatus(id, status, username);
    }

    // DELETE TASK for logged-in user
    @DeleteMapping("/deletetask/{id}")
    public void deleteTask(@PathVariable String id) {
        String username = getCurrentUsername();
        taskService.deleteTask(id, username);
    }
}

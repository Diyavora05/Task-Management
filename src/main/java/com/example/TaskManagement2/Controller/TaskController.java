//package com.example.TaskManagement2.Controller;
//
//import com.example.TaskManagement2.Service.TaskService;
//import com.example.TaskManagement2.model.Task;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//// TaskController.java
//@RestController
//@RequestMapping("/api/tasks")
//public class TaskController {
//
//    @Autowired
//    private TaskService taskService;
//
//    @PostMapping
//    public ResponseEntity<?> createTask(@RequestBody Task task) {
//
//        return ResponseEntity.ok(taskService.createTask(task));
//    }
//
//    @GetMapping
//    public ResponseEntity<?> getAllTasks(Authentication authentication) {
//        if (authentication == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
//        }
//        String username = authentication.getName();
//        return ResponseEntity.ok(taskService.getAllTasks(username));
//    }
//}


package com.example.TaskManagement2.Controller;

import com.example.TaskManagement2.Service.TaskService;
import com.example.TaskManagement2.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    // GET SINGLE TASK
    @GetMapping("/user/{userid}/{id}")
    public Task getTaskById(@PathVariable String userid, @PathVariable String id) {
        return taskService.getTaskById(id, userid);
    }
    //GET ALL TASKS
    @GetMapping("/user/{userid}")
    public List<Task> getAllTasks(@PathVariable String userid) {
        return taskService.getAllTasks(userid);
    }
    // GET TASK BY TITLE
    @GetMapping("/title/{title}")
    public Task getTaskByTitle(@PathVariable String title) {
        return taskService.getTaskByTitle(title).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    // CREATE TASK
    @PostMapping("/create")
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }
    // UPDATE TASK
    @PutMapping("/updatetask/{userid}/{id}")
    public Task updateTask(@PathVariable String id , @RequestBody Task task , @PathVariable String userid) {
        return taskService.updateTask(id, task, userid);
    }
    // UPDATE STATUS
    @PutMapping("/updatestatus/{userid}/{id}")
    public Task updateStatus(@PathVariable String id, @RequestParam String status, @PathVariable String userid) {
        return taskService.updateStatus(id,status,userid);
    }

    // DELETE TASK
    @DeleteMapping("/deletetask/{userid}/{id}")
    public void deleteTask(@PathVariable String userid, @PathVariable String id) {
        taskService.deleteTask(id, userid);
    }

}

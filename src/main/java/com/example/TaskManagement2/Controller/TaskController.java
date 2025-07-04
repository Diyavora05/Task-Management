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

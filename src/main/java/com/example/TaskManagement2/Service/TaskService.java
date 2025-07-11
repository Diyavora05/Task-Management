package com.example.TaskManagement2.Service;

import com.example.TaskManagement2.model.Task;
import com.example.TaskManagement2.model.TaskStatus;
import com.example.TaskManagement2.model.User;
import com.example.TaskManagement2.repository.TaskRepository;
import com.example.TaskManagement2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    private User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Task getTaskById(String taskId, String username) {
        User user = getUserByUsername(username);
        Task task = taskRepository.findByIdAndUserid(taskId, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found or unauthorized"));

        return task;
    }

    public List<Task> getAllTasks(String username) {
        User user = getUserByUsername(username);
        return taskRepository.findAllByUserid(user.getId());
    }

    public Optional<Task> getTaskByTitle(String title, String username) {
        User user = getUserByUsername(username);
        return taskRepository.findByTitleAndUserid(title, user.getId());
    }

    public Task createTask(Task task, String username) {
        User user = getUserByUsername(username);
        task.setUserid(user.getId());
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        task.setCreated_at(now);
        task.setUpdated_at(now);
        return taskRepository.save(task);
    }

    public Task updateTask(String taskId, Task updatedTask, String username) {
        User user = getUserByUsername(username);
        Task task = taskRepository.findByIdAndUserid(taskId, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found or unauthorized"));

        task.setTitle(updatedTask.getTitle());
        task.setDescription(updatedTask.getDescription());
        task.setDue_date(updatedTask.getDue_date());
        task.setUpdated_at(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        return taskRepository.save(task);
    }
    public Task updateStatus(String taskId, String statusStr, String username) {
        User user = getUserByUsername(username);
        Task task = taskRepository.findByIdAndUserid(taskId, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found or unauthorized"));


        TaskStatus status;
        try {
            status = TaskStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + statusStr);
        }

        task.setStatus(status);
        task.setUpdated_at(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        return taskRepository.save(task);
    }

    public void deleteTask(String taskId, String username) {
        User user = getUserByUsername(username);
        Task task = taskRepository.findByIdAndUserid(taskId, user.getId())
                .orElseThrow(() -> new RuntimeException("Task not found or unauthorized"));

        taskRepository.delete(task);
    }
}


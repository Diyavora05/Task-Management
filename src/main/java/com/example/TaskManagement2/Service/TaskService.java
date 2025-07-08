package com.example.TaskManagement2.Service;

import com.example.TaskManagement2.model.Task;
import com.example.TaskManagement2.model.User;
import com.example.TaskManagement2.repository.TaskRepository;
import com.example.TaskManagement2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
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
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Unauthorized access");
        }
        return task;
    }

    public List<Task> getAllTasks(String username) {
        User user = getUserByUsername(username);
        return taskRepository.findByUser(user);
    }

    public Optional<Task> getTaskByTitle(String title, String username) {
        User user = getUserByUsername(username);
        return taskRepository.findByTitleAndUser(title, user);
    }

    public Task createTask(Task task, String username) {
        User user = getUserByUsername(username);
        task.setUser(user);
        return taskRepository.save(task);
    }

    public Task updateTask(String taskId, Task updatedTask, String username) {
        Task existingTask = getTaskById(taskId, username); 

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        

        return taskRepository.save(existingTask);
    }

    public Task updateStatus(String taskId, String status, String username) {
        Task existingTask = getTaskById(taskId, username); 

        existingTask.setStatus(status);
        return taskRepository.save(existingTask);
    }

    public void deleteTask(String taskId, String username) {
        Task existingTask = getTaskById(taskId, username); 
        taskRepository.delete(existingTask);
    }
}

package com.example.TaskManagement2.Service;

import com.example.TaskManagement2.model.Task;
import com.example.TaskManagement2.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Task getTaskById(String id, String userid) {
        return taskRepository.findByIdAndUserid(id, userid)
                .orElseThrow(() -> new RuntimeException("Task not found or unauthorized"));
    }

    public List<Task> getAllTasks(String userid) {
        return taskRepository.findAllByUserid(userid);
    }

    public Optional<Task> getTaskByTitle(String title) {
        return taskRepository.findByTitle(title);
    }

    public Task createTask(Task task) {
        Date now = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        task.setCreated_at(now);
        task.setUpdated_at(now);
        task.setStatus("TODO");
        return taskRepository.save(task);
    }


    public Task updateTask(String id, Task updatedTask, String userid) {
        Optional<Task> existing = taskRepository.findByIdAndUserid(id, userid);
        if (existing.isPresent()) {
            Task task = existing.get();
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setDue_date(updatedTask.getDue_date());
            task.setUpdated_at(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found or unauthorized");
        }
    }

    public void deleteTask(String id, String userid) {
        Optional<Task> task = taskRepository.findByIdAndUserid(id, userid);
        if (task.isPresent()) {
            taskRepository.deleteById(id);
        } else {
            throw new RuntimeException("Task not found or unauthorized");
        }
    }

    public Task updateStatus(String id, String status, String userid) {
        Optional<Task> taskOpt = taskRepository.findByIdAndUserid(id, userid);
        if (taskOpt.isPresent()) {
            Task task = taskOpt.get();
            task.setStatus(status);
            task.setUpdated_at(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            return taskRepository.save(task);
        } else {
            throw new RuntimeException("Task not found or unauthorized");
        }
    }
}

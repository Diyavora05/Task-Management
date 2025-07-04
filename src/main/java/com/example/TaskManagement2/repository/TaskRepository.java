package com.example.TaskManagement2.repository;

import com.example.TaskManagement2.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends MongoRepository<Task, String> {

    List<Task> findAllByUserid(String userid);
    Optional<Task> findByIdAndUserid(String id, String userid);
    Optional<Task> findByTitle(String title);
}
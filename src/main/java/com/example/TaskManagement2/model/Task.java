package com.example.TaskManagement2.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document("Task")
public class Task {

    @Id
    private String id;
    private String title;
    private String description;
    private TaskStatus status;
    private Date due_date;
    private String userid;
    private Date created_at;
    private Date updated_at;
}

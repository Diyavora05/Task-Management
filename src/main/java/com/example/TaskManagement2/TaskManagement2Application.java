package com.example.TaskManagement2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TaskManagement2Application {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagement2Application.class, args);
	}
}

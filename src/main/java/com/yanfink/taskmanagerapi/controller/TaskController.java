package com.yanfink.taskmanagerapi.controller;

import com.yanfink.taskmanagerapi.kafka.TaskEventProducer;
import com.yanfink.taskmanagerapi.model.Task;
import com.yanfink.taskmanagerapi.repository.TaskRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(origins = "http://localhost:5173")
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskEventProducer taskEventProducer;

    public TaskController(TaskRepository taskRepository, TaskEventProducer taskEventProducer) {
        this.taskRepository = taskRepository;
        this.taskEventProducer = taskEventProducer;
    }

    @GetMapping
    public List<Task> listAll() {
        return taskRepository.findAll();
    }

    @PostMapping
    public Task create(@RequestBody Task task) {
        Task saved = taskRepository.save(task);
        taskEventProducer.publishTaskCreated(saved.getId(), saved.getTitle());
        return saved;
    }
}
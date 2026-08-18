package com.yanfink.taskmanagerapi.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskEventProducer {

    private static final String TOPIC = "task-events";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public TaskEventProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishTaskCreated(Long taskId, String title) {
        String message = "Tarefa criada -> id: " + taskId + ", titulo: " + title;
        kafkaTemplate.send(TOPIC, message);
    }
}
package com.yanfink.taskmanagerapi.kafka;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TaskEventConsumer {

    private static final Logger logger = LogManager.getLogger(TaskEventConsumer.class);

    @KafkaListener(topics = "task-events", groupId = "task-manager-group")
    public void listen(String message) {
        logger.info("[NOTIFICACAO SIMULADA] Recebido do Kafka: {}", message);
    }
}
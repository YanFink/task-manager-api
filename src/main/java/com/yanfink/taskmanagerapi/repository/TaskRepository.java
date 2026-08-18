package com.yanfink.taskmanagerapi.repository;

import com.yanfink.taskmanagerapi.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
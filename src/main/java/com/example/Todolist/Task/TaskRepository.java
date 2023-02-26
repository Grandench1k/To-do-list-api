package com.example.Todolist.Task;

import com.example.Todolist.Task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, String> {
    Task findByUuid(String uuid);

    Task findByName(String name);
}

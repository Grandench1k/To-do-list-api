package com.example.Todolist.Task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, String> {
    Task findByUuid(String uuid);

    Task findByName(String name);

    List<Task> findAllByUserid(Integer userid);
}

package com.example.Todolist.Task;

import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import com.example.Todolist.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    public List<Task> findAllByUser(Authentication authentication) throws NotFound {
        User user = (User) authentication.getPrincipal();
        List<Task> task = taskRepository.findAllByUserid(user.getId());
        if (task.isEmpty()) {
            throw new NotFound("You haven't any tasks yet");
        }
        return task;
    }
    public void save(Task task, Authentication authentication) throws AlreadyDefined, NotFound {
        User user = (User) authentication.getPrincipal();
        Task oldTask = taskRepository.findByName(task.getName());
        if (oldTask == null && task.getName() != null) {
            task.setUserid(user.getId());
            taskRepository.save(task);
        } else {
            if (task.getUserid() != user.getId()) {
                throw new NotFound("You don't have access to save task on this user's account");
            }
            if (task.getName() == null) {
                throw new NotFound("You don't write a name");
            }
            if (oldTask != null) {
                throw new AlreadyDefined("Task with this name already defined");
            }
        }
    }
    public void update(Task task, String uuid) throws NotFound, AlreadyDefined {
        Task newTask = taskRepository.findByUuid(uuid);
        Task oldTask = taskRepository.findByName(task.getName());
        if (task.getName() == null) {
            throw new NotFound("You don't write a name");
        }
        if (newTask == null) {
            throw new NotFound("Task with this uuid does not exist");
        }
        if (oldTask != null) {
            throw new AlreadyDefined("Task with this name already defined");
        }
        newTask.setName(task.getName());
        taskRepository.save(newTask);
    }
    public Task findByUuid(String uuid, Authentication authentication) throws NotFound {
        User user = (User) authentication.getPrincipal();
        Task task = taskRepository.findByUuid(uuid);
        if (task != null && task.getUserid() == user.getId()) {
            return task;
        } else  {
            if (user.getId() != task.getUserid()) {
                throw new NotFound("You don't have this board");
            }
            if(uuid == null) {
                throw new NotFound("Write a uuid of board you want to see");
            }
            if (task == null) {
                throw new NotFound("board with this uuid does not exist");
            }
            return null;
        }
    }
    public void delete(String uuid) throws NotFound {
        Task taskToDelete = taskRepository.findByUuid(uuid);
        if(taskToDelete != null) {
            taskRepository.deleteById(uuid);
        } else {
            if (taskToDelete == null) {
                throw new NotFound("Can't found task with this uuid");
            }
        }
    }
}

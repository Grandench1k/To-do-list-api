package com.example.Todolist.Task;

import com.example.Todolist.Board.Board;
import com.example.Todolist.Board.BoardRepository;
import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import com.example.Todolist.Exceptions.NotFoundUser;
import com.example.Todolist.User.User;
import com.example.Todolist.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    public void save(Task task, String userid) throws AlreadyDefined, NotFound, NotFoundUser {
        Task oldTask = taskRepository.findByName(task.getName());
        Board board = boardRepository.findByUuid(task.getBoard_uuid());
        User user = userRepository.findUserById(userid);
        if (oldTask == null && task.getName() != null && board != null && user != null) {
            task.setUserid(userid);
            taskRepository.save(task);
        } else {
            if (user == null) {
                throw new NotFoundUser("Can't find User with this id");
            }
            if (task.getName() == null) {
                throw new NotFound("You don't write a name");
            }
            if (oldTask != null) {
                throw new AlreadyDefined("Task with this name already defined");
            }
            if (board == null) {
                throw new NotFound("Can't find board with this uuid for task");
            }
        }
    }
    public void update(Task task, String uuid, String userid) throws NotFound, NotFoundUser, AlreadyDefined {
        Task newTask = taskRepository.findByUuid(uuid);
        Task oldTask = taskRepository.findByName(task.getName());
        User user = userRepository.findUserById(userid);
        if (user == null) {
            throw new NotFoundUser("Can't find User with this id");
        }
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
    public Task findByUuid(String uuid, String userid) throws NotFound, NotFoundUser {
        Task task = taskRepository.findByUuid(uuid);
        User user = userRepository.findUserById(userid);
        if (task != null && user != null) {
            return task;
        } else {
            if (user == null) {
                throw new NotFoundUser("Can't find User with this id");
            }
            if(uuid == null) {
                throw new NotFound("Write a uuid of task you want to see");
            }
            if (task == null) {
                throw new NotFound("Task with this uuid does not exist");
            }
            return null;
        }
    }
    public void delete(String uuid, String userid) throws NotFound, NotFoundUser {
        Task taskToDelete = taskRepository.findByUuid(uuid);
        User user = userRepository.findUserById(userid);
        if(taskToDelete != null && user != null) {
            taskRepository.deleteById(uuid);
        } else {
            if (user == null) {
                throw new NotFoundUser("Can't find User with this id");
            }
            if (taskToDelete == null) {
                throw new NotFound("Can't found task with this uuid");
            }
        }
    }

    public List<Task> findAll(String userid) throws NotFoundUser, NotFound {
        List<Task> tasks = taskRepository.findAllByUserid(userid);
        User user = userRepository.findUserById(userid);
        if (userid == null) {
            throw new NotFoundUser("Please, write id");
        }
        if(user == null) {
            throw new NotFoundUser("Can't find User with this id");
        }
        if (tasks.isEmpty()) {
            throw new NotFound("You haven't any tasks yet");
        }
        return tasks;
    }
}

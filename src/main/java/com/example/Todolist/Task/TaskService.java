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
        if (oldTask == null && board != null && user != null) {
            task.setUserid(userid);
            taskRepository.save(task);
        } else {
            if (user == null) {
                throw new NotFoundUser("Can't find User with this id");
            }
            if (oldTask != null) {
            throw new AlreadyDefined("Task with this name already defined");
            }
            if (board == null) {
                throw new NotFound("Can't find board with this uuid for task");
            }
        }
    }
    public void update(Task task, String uuid, String userid) throws NotFound, NotFoundUser {
        Task oldTask = taskRepository.findByUuid(uuid);
        User user = userRepository.findUserById(userid);
        if (user == null) {
            throw new NotFoundUser("Can't find User with this id");
        }
        if (oldTask == null) {
            throw new NotFound("Task with this uuid does not exist");
        }
        oldTask.setName(task.getName());
        taskRepository.save(oldTask);
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
}

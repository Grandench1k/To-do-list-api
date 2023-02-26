package com.example.Todolist.Task;

import com.example.Todolist.Board.Board;
import com.example.Todolist.Board.BoardRepository;
import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final BoardRepository boardRepository;
    public void save(Task task) throws AlreadyDefined, NotFound {
        Task oldTask = taskRepository.findByName(task.getName());
        Board board = boardRepository.findByUuid(task.getBoard_uuid());
        if (oldTask == null && board != null) {
            taskRepository.save(task);
        } else {
            if (oldTask != null) {
            throw new AlreadyDefined("Task with this name already defined");
            }
            if (board == null) {
                throw new NotFound("Can't find board with this uuid for task");
            }
        }
    }
    public void update(Task task, String uuid) throws NotFound {
        Task oldTask = taskRepository.findByUuid(uuid);
        if (oldTask == null) {
            throw new NotFound("Task with this uuid does not exist");
        }
        oldTask.setName(task.getName());
        taskRepository.save(oldTask);
    }
    public Task findByUuid(String uuid) throws NotFound {
        return taskRepository.findById(uuid).orElseThrow(() -> new NotFound("Can't found task with this uuid"));
    }
    public void delete(String uuid) throws NotFound {
        Task taskToDelete = taskRepository.findByUuid(uuid);
        if(taskToDelete != null) {
            taskRepository.deleteById(uuid);
        } else {
            throw new NotFound("Can't found task with this uuid");
        }
    }
}

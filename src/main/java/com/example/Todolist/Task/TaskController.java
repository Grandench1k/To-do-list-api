package com.example.Todolist.Task;

import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @GetMapping()
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Task task) {
        try {
            taskService.save(task);
            return ResponseEntity.ok("Succesful");
        } catch (AlreadyDefined e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity getByUuid(@PathVariable String uuid) {
        try {
            return ResponseEntity.ok(taskService.findByUuid(uuid));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Can't found task with this uuid");
        }

    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity update(@RequestBody Task task, @PathVariable String uuid) {
        try {
            taskService.update(task, uuid);
            return ResponseEntity.ok("successful!");
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity delete(@PathVariable String uuid) {
        try {
            taskService.delete(uuid);
            return ResponseEntity.ok("Successful!");
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

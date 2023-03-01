package com.example.Todolist.Task;

import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import com.example.Todolist.Exceptions.NotFoundUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/{userid}/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping()
    public ResponseEntity getAllTasks(@PathVariable String userid) {
        try {
            return ResponseEntity.ok(taskService.findAll(userid));
        } catch (NotFoundUser | NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Task task, @PathVariable String userid) {
        try {
            taskService.save(task, userid);
            return ResponseEntity.ok("Succesful");
        } catch (NotFound | NotFoundUser | AlreadyDefined e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity getByUuid(@PathVariable String userid, @PathVariable String uuid) {
        try {
            return ResponseEntity.ok(taskService.findByUuid(uuid, userid));
        } catch (NotFoundUser e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Can't found task with this uuid");
        }

    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity update(@RequestBody Task task,@PathVariable String userid, @PathVariable String uuid) {
        try {
            taskService.update(task, uuid, userid);
            return ResponseEntity.ok("successful!");
        } catch (NotFound | AlreadyDefined | NotFoundUser e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity delete(@PathVariable String userid, @PathVariable String uuid) {
        try {
            taskService.delete(uuid, userid);
            return ResponseEntity.ok("Successful!");
        } catch (NotFound | NotFoundUser e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }
}

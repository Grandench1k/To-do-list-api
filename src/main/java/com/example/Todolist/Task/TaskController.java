package com.example.Todolist.Task;

import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping()
    public ResponseEntity getAllBoards(Authentication authentication) {
        try {
            return ResponseEntity.ok(taskService.findAllByUser(authentication));
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Task task, Authentication authentication) {
        try {
            taskService.save(task, authentication);
            return ResponseEntity.ok("Succesful");
        } catch (AlreadyDefined | NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }

    @GetMapping("/{uuid}")
    public ResponseEntity getByUuid(Authentication authentication, @PathVariable String uuid) {
        try {
            return ResponseEntity.ok(taskService.findByUuid(uuid, authentication));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Can't found task with this uuid");
        }

    }

    @PutMapping("/{uuid}/update")
    public ResponseEntity update(@RequestBody Task task, @PathVariable String uuid) {
        try {
            taskService.update(task, uuid);
            return ResponseEntity.ok("successful!");
        } catch (NotFound | AlreadyDefined e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }

    @DeleteMapping("/{uuid}/delete")
    public ResponseEntity delete(@PathVariable String uuid) {
        try {
            taskService.delete(uuid);
            return ResponseEntity.ok("Successful!");
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }
}

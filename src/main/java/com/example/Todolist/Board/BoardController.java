package com.example.Todolist.Board;

import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardService boardService;
    @GetMapping()
    public ResponseEntity getAllBoards(Authentication authentication) {
        try {
            return ResponseEntity.ok(boardService.findAllByUser(authentication));
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Board board, Authentication authentication) {
        try {
            boardService.save(board, authentication);
            return ResponseEntity.ok("Succesful");
        } catch (AlreadyDefined | NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }
    @GetMapping("/{uuid}")
    public ResponseEntity getByUuid(@PathVariable String uuid, Authentication authentication) {
        try {
            return ResponseEntity.ok(boardService.findByUuid(uuid, authentication));
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }

    }
    @PutMapping("/{uuid}/update")
    public ResponseEntity update(@RequestBody Board board, @PathVariable String uuid) {
        try {
            boardService.update(board,uuid);
            return ResponseEntity.ok("Board succesfully updated!");
        } catch (NotFound | AlreadyDefined e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }
    @DeleteMapping("/{uuid}/delete")
    public ResponseEntity delete(@PathVariable String uuid) {
        try {
            boardService.delete(uuid);
            return ResponseEntity.ok("Successful");
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }
}

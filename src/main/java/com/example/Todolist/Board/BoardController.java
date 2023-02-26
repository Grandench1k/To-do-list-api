package com.example.Todolist.Board;

import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/boards")
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;
    @GetMapping()
    public List<Board> getAllTasks() {
    return boardRepository.findAll();
    }
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Board board) {
        try {
            boardService.save(board);
            return ResponseEntity.ok("Succesful");
        } catch (AlreadyDefined e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }
    @GetMapping("/{uuid}")
    public ResponseEntity getByUuid(@PathVariable String uuid) {
        try {
            return ResponseEntity.ok(boardService.findByUuid(uuid));
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Can't found board with this uuid");
        }

    }
    @PutMapping("/update/{uuid}")
    public ResponseEntity update(@RequestBody Board board, @PathVariable String uuid) {
        try {
            boardService.update(board, uuid);
            return ResponseEntity.ok("Board succesfully updated!");
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }
    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity delete(@PathVariable String uuid) {
        try {
            boardService.delete(uuid);
            return ResponseEntity.ok("Successful");
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

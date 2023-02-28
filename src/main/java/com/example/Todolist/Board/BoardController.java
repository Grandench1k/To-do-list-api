package com.example.Todolist.Board;

import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import com.example.Todolist.Exceptions.NotFoundUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/{userid}/boards")
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;
    @GetMapping()
    public List<Board> getAllTasks(@PathVariable String userid) {
            return boardRepository.findAllByUserid(userid);
    }
    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Board board, @PathVariable String userid) {
        try {
            boardService.save(board, userid);
            return ResponseEntity.ok("Succesful");
        } catch (AlreadyDefined e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundUser e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }
    @GetMapping("/{uuid}")
    public ResponseEntity getByUuid(@PathVariable String userid, @PathVariable String uuid) {
        try {
            return ResponseEntity.ok(boardService.findByUuid(uuid, userid));
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundUser e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }

    }
    @PutMapping("/update/{uuid}")
    public ResponseEntity update(@RequestBody Board board, @PathVariable String userid, @PathVariable String uuid ) {
        try {
            boardService.update(board,uuid, userid);
            return ResponseEntity.ok("Board succesfully updated!");
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundUser e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }
    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity delete(@PathVariable String userid, @PathVariable String uuid) {
        try {
            boardService.delete(userid, uuid);
            return ResponseEntity.ok("Successful");
        } catch (NotFound e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (NotFoundUser e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Exception");
        }
    }
}

package com.example.Todolist.Board;

import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    public final BoardRepository boardRepository;

    public void save(Board board) throws AlreadyDefined {
        Board newBoard = boardRepository.findByName(board.getName());
        if (newBoard == null) {
            boardRepository.save(board);
        } else {
        throw new AlreadyDefined("Board with this name already defined");
    }


    }
    public void update(Board board, String uuid) throws NotFound {
        Board oldBoard = boardRepository.findByUuid(uuid);
        if (oldBoard == null) {
            throw new NotFound("board with this uuid does not exist");
        }
        oldBoard.setName(board.getName());
        boardRepository.save(oldBoard);
    }
    public Board findByUuid(String uuid) throws NotFound {
       return boardRepository.findById(uuid).orElseThrow(() -> new NotFound("Can't found board with this uuid"));
    }
    public void delete(String uuid) throws NotFound {
       Board boardToDelete = boardRepository.findByUuid(uuid);
       if(boardToDelete != null) {
           boardRepository.deleteById(uuid);
       } else {
           throw new NotFound("Can't found board with this uuid");
       }
    }
}

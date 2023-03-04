package com.example.Todolist.Board;

import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import com.example.Todolist.User.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    public final BoardRepository boardRepository;

    public List<Board> findAllByUser(Authentication authentication) throws NotFound{
        User user = (User) authentication.getPrincipal();
        List<Board> board = boardRepository.findAllByUserid(user.getId());
        if (board.isEmpty()) {
        throw new NotFound("You haven't any boards yet");
        }
        return board;
    }

    public void save(Board board, Authentication authentication) throws AlreadyDefined, NotFound {
        User user = (User) authentication.getPrincipal();
        Board oldBoard = boardRepository.findByName(board.getName());
        if (oldBoard == null && board.getName() != null) {
            board.setUserid(user.getId());
            boardRepository.save(board);
        } else {
            if (board.getUserid() != user.getId()) {
                throw new NotFound("You don't have access to save board on this user's account");
            }
            if (board.getName() == null) {
                throw new NotFound("You don't write a name");
            }
            if (oldBoard != null) {
                throw new AlreadyDefined("Board with this name already defined");
            }
    }
    }
    public void update(Board board, String uuid) throws NotFound, AlreadyDefined {
        Board newBoard = boardRepository.findByUuid(uuid);
        Board oldBoard = boardRepository.findByName(board.getName());
            if (board.getName() == null) {
                throw new NotFound("You don't write a name");
            }
            if (newBoard == null) {
                throw new NotFound("board with this uuid does not exist");
            }
            if (oldBoard != null) {
                throw new AlreadyDefined("Board with this name already defined");
            }
        newBoard.setName(board.getName());
        boardRepository.save(newBoard);
    }
    public Board findByUuid(String uuid, Authentication authentication) throws NotFound {
       User user = (User) authentication.getPrincipal();
       Board board = boardRepository.findByUuid(uuid);
       if (board != null && board.getUserid() == user.getId()) {
           return board;
       } else  {
           if (user.getId() != board.getUserid()) {
               throw new NotFound("You don't have this board");
           }
           if(uuid == null) {
               throw new NotFound("Write a uuid of board you want to see");
           }
           if (board == null) {
               throw new NotFound("board with this uuid does not exist");
           }
           return null;
       }
    }
    public void delete(String uuid) throws NotFound {
       Board boardToDelete = boardRepository.findByUuid(uuid);

       if(boardToDelete != null) {
           boardRepository.deleteById(uuid);
       } else {
           if (boardToDelete == null) {
               throw new NotFound("Can't found board with this uuid");
           }
       }
    }


}

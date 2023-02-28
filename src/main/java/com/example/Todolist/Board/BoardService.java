package com.example.Todolist.Board;

import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import com.example.Todolist.Exceptions.NotFoundUser;
import com.example.Todolist.User.User;
import com.example.Todolist.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    public final BoardRepository boardRepository;

    public final UserRepository userRepository;

    public void save(Board board, String userid) throws AlreadyDefined, NotFound, NotFoundUser {
        System.out.println(board.getName());
        Board newBoard = boardRepository.findByName(board.getName());
        User user = userRepository.findUserById(userid);
        if (newBoard == null && user != null) {
            board.setUserid(userid);
            boardRepository.save(board);
        } else {
            if(user == null) {
                throw new NotFoundUser("Can't find User with this id");
            }
            if (newBoard != null) {
                throw new AlreadyDefined("Board with this name already defined");
            }
    }


    }
    public void update(Board board, String uuid, String userid) throws NotFound, NotFoundUser {
        Board oldBoard = boardRepository.findByUuid(uuid);
        User user = userRepository.findUserById(userid);
        if (user == null) {
            throw new NotFoundUser("Can't find User with this id");
        }
        if (oldBoard == null ) {
            throw new NotFound("board with this uuid does not exist");
        }
        oldBoard.setName(board.getName());
        boardRepository.save(oldBoard);
    }
    public Board findByUuid(String uuid, String userid) throws NotFound, NotFoundUser {
       Board board = boardRepository.findByUuid(uuid);
       User user = userRepository.findUserById(userid);
       if (board != null && user != null) {
           return board;
       } else  {
           if (user == null) {
               throw new NotFoundUser("Can't find User with this id");
           }
           if (board == null ) {
               throw new NotFound("board with this uuid does not exist");
           }
           return null;
       }
    }
    public void delete(String userid, String uuid) throws NotFound, NotFoundUser {
       Board boardToDelete = boardRepository.findByUuid(uuid);
       User user = userRepository.findUserById(userid);
       if(boardToDelete != null && user != null) {
           boardRepository.deleteById(uuid);
       } else {
           if (user == null) {
               throw new NotFoundUser("Can't find User with this id");
           }
           if (boardToDelete == null) {
               throw new NotFound("Can't found board with this uuid");
           }
       }
    }
}

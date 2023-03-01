package com.example.Todolist.Board;

import com.example.Todolist.Exceptions.AlreadyDefined;
import com.example.Todolist.Exceptions.NotFound;
import com.example.Todolist.Exceptions.NotFoundUser;
import com.example.Todolist.User.User;
import com.example.Todolist.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    public final BoardRepository boardRepository;

    public final UserRepository userRepository;

    public List<Board> findAll(String userid) throws NotFound, NotFoundUser {
    List<Board> board = boardRepository.findAllByUserid(userid);
    User user = userRepository.findUserById(userid);
        if (userid == null) {
            throw new NotFoundUser("Please, write id");
        }
        if(user == null) {
            throw new NotFoundUser("Can't find User with this id");
        }
        if (board.isEmpty()) {
        throw new NotFound("You haven't any boards yet");
        }
        return board;
    }

    public void save(Board board, String userid) throws AlreadyDefined, NotFound, NotFoundUser {
        Board oldBoard = boardRepository.findByName(board.getName());
        User user = userRepository.findUserById(userid);
        if (oldBoard == null && board.getName() != null && user != null) {
            board.setUserid(userid);
            boardRepository.save(board);
        } else {
            if(user == null) {
                throw new NotFoundUser("Can't find User with this id");
            }
            if (board.getName() == null) {
                throw new NotFound("You don't write a name");
            }
            if (oldBoard != null) {
                throw new AlreadyDefined("Board with this name already defined");
            }
    }
    }
    public void update(Board board, String uuid, String userid) throws NotFound, NotFoundUser, AlreadyDefined {
        Board newBoard = boardRepository.findByUuid(uuid);
        Board oldBoard = boardRepository.findByName(board.getName());
        User user = userRepository.findUserById(userid);
        if (user == null) {
            throw new NotFoundUser("Can't find User with this id");
        }
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
    public Board findByUuid(String uuid, String userid) throws NotFound, NotFoundUser {
       Board board = boardRepository.findByUuid(uuid);
       User user = userRepository.findUserById(userid);
       if (board != null && user != null) {
           return board;
       } else  {
           if (user == null) {
               throw new NotFoundUser("Can't find User with this id");
           }
           if(uuid == null) {
               throw new NotFound("Write a uuid of board you want to see");
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

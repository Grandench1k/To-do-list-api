package com.example.Todolist.Board;

import com.example.Todolist.Board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, String> {
    Board findByName(String name);
    Board findByUuid(String uuid);
}
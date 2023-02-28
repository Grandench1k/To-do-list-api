package com.example.Todolist.Board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, String> {
    Board findByName(String name);
    Board findByUuid(String uuid);
    List<Board> findAllByUserid(String userid);
}
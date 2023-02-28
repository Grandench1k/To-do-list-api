package com.example.Todolist.Board;

import jakarta.persistence.*;
import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    private String userid;
    private String name;
}

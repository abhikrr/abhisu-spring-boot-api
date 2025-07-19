package com.abhisu.abhisu_spring_boot_api.todo;

import com.abhisu.abhisu_spring_boot_api.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "todo_tbl")
@Data
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private boolean completed;

    @ManyToOne
    private User user;
}

package com.abhisu.abhisu_spring_boot_api.todo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findByUserUsername(String username);
}

package com.abhisu.abhisu_spring_boot_api.todo;

import com.abhisu.abhisu_spring_boot_api.entity.User;
import com.abhisu.abhisu_spring_boot_api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoRepository todoRepo;
    private final UserRepository userRepo;

    @GetMapping
    public List<ToDo> getUserTodos(Authentication auth) {
        return todoRepo.findByUserUsername(auth.getName());
    }

    @PostMapping
    public ToDo create(@RequestBody ToDo todo, Authentication auth) {
        User user = userRepo.findByUsername(auth.getName()).orElseThrow();
        todo.setUser(user);
        return todoRepo.save(todo);
    }
}

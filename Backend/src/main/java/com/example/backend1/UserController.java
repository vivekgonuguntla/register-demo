package com.example.backend1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    // LIST USERS
    @GetMapping
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    // GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE USER
    @PostMapping
    public User createUser(@RequestBody User user) {
        user.setId(null);
        return repository.save(user);
    }

    // UPDATE USER
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody User user) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        user.setId(id);
        return ResponseEntity.ok(repository.save(user));
    }

    // DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}

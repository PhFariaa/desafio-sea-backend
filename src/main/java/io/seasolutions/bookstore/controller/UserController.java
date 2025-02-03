package io.seasolutions.bookstore.controller;


import io.seasolutions.bookstore.domain.user.UserForm;
import io.seasolutions.bookstore.domain.user.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserForm> createUser(@RequestBody UserForm userForm){
        userForm = this.userService.createUser(userForm);
        return ResponseEntity.ok(userForm);
    }

    @GetMapping
    public ResponseEntity<?> getUsers(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(this.userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserForm> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(this.userService.findById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UserForm> updateUser(@PathVariable Long id, @RequestBody UserForm userForm){
        UserForm user = this.userService.update(id, userForm);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}

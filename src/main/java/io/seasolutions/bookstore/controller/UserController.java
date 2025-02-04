package io.seasolutions.bookstore.controller;


import io.seasolutions.bookstore.domain.user.UserForm;
import io.seasolutions.bookstore.domain.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users", produces = "application/json")
@Tag(name = "User")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Create new user", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Doctors created  successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserForm> createUser(@RequestBody UserForm userForm){
        userForm = this.userService.createUser(userForm);
        return ResponseEntity.ok(userForm);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", method = "PUT")
    @Transactional
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserForm> updateUser(@PathVariable Long id, @RequestBody UserForm userForm){
        UserForm user = this.userService.update(id, userForm);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @Operation(summary = "Get users", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get users successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getUsers(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(this.userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get user successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<UserForm> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(this.userService.findById(id));
    }


    @DeleteMapping
    @Transactional
    @Operation(summary = "Delete user", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete user successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}

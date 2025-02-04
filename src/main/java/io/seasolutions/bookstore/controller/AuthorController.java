package io.seasolutions.bookstore.controller;


import io.seasolutions.bookstore.domain.author.AuthorDTO;
import io.seasolutions.bookstore.domain.author.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/authors", produces = "application/json")
@Tag(name = "Author")
public class AuthorController {


    private AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Register a new author", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author created  successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthorDTO> registerNewAuthor(@RequestBody @Valid AuthorDTO authorDTO){
        authorDTO = this.authorService.createAuthor(authorDTO);
        return ResponseEntity.ok(authorDTO);

    }

    @GetMapping
    @Operation(summary = "Get all authors", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get authors successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getAuthors(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(this.authorService.findAll(pageable));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get author by id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get author successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id){
        return ResponseEntity.ok(this.authorService.findById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update author", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author created  successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AuthorDTO>  updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorDTO authorDTO){
        AuthorDTO author = this.authorService.updateAuthor(id, authorDTO);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete author", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete author successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        this.authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }


}

package io.seasolutions.bookstore.controller;


import io.seasolutions.bookstore.domain.author.AuthorDTO;
import io.seasolutions.bookstore.domain.author.AuthorService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
public class AuthorController {


    private AuthorService authorService;

    public AuthorController(AuthorService authorService){
        this.authorService = authorService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AuthorDTO> registerNewAuthor(@RequestBody @Valid AuthorDTO authorDTO){
        authorDTO = this.authorService.createAuthor(authorDTO);
        return ResponseEntity.ok(authorDTO);

    }

    @GetMapping
    public ResponseEntity<?> getAuthors(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(this.authorService.findAll(pageable));
    }


    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id){
        return ResponseEntity.ok(this.authorService.findById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AuthorDTO>  updateAuthor(@PathVariable Long id, @RequestBody @Valid AuthorDTO authorDTO){
        AuthorDTO author = this.authorService.updateAuthor(id, authorDTO);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        this.authorService.deleteAuthor(id);
        return ResponseEntity.ok().build();
    }


}

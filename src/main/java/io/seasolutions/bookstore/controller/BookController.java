package io.seasolutions.bookstore.controller;
import io.seasolutions.bookstore.domain.book.BookService;
import io.seasolutions.bookstore.domain.book.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/books", produces = "application/json")
@Tag(name = "Book")
public class BookController {


    private final BookService bookService;
    public BookController(BookService bookService){
        this.bookService = bookService;
    }



    @PostMapping
    @Transactional
    @Operation(summary = "Register a new book", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book created  successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BookDTO> registerNewBook (@RequestBody @Valid BookDTO bookDTO){
        bookDTO = this.bookService.createBook(bookDTO);
        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping
    @Operation(summary = "Get all books", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get books successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getBooks(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(this.bookService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get book successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(this.bookService.findById(id));
    }


    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Update book", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO){
        BookDTO book = this.bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Delete book", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delete book successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        this.bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}
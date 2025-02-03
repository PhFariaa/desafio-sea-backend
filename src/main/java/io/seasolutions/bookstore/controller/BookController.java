package io.seasolutions.bookstore.controller;
import io.seasolutions.bookstore.domain.book.BookService;
import io.seasolutions.bookstore.domain.book.BookDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {


    private final BookService bookService;
    public BookController(BookService bookService){
        this.bookService = bookService;
    }



    @PostMapping
    @Transactional
    public ResponseEntity<BookDTO> registerNewBook (@RequestBody @Valid BookDTO bookDTO){
        bookDTO = this.bookService.createBook(bookDTO);
        return ResponseEntity.ok(bookDTO);
    }

    @GetMapping
    public ResponseEntity<?> getBooks(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.ok(this.bookService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(this.bookService.findById(id));
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody @Valid BookDTO bookDTO){
        BookDTO book = this.bookService.updateBook(id, bookDTO);
        return ResponseEntity.ok(book);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        this.bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}
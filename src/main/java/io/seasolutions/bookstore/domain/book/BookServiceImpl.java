package io.seasolutions.bookstore.domain.book;

import io.seasolutions.bookstore.common.messageError.MessageError;
import io.seasolutions.bookstore.domain.author.Author;
import io.seasolutions.bookstore.domain.author.AuthorService;
import io.seasolutions.bookstore.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO){
        List<Author> authors  = this.authorService.findByName(bookDTO);

        if (authors.size() != bookDTO.getAuthors().size()){
            throw new EntityNotFoundException("Some Authors not found");
        }

        Book book = new Book(bookDTO, authors);
        this.bookRepository.save(book);
        return new BookDTO(book);
    }

    @Override
    public List<BookDTO> findAll(Pageable pageable) {
        return this.bookRepository.findAll(pageable).stream().map(BookDTO::new).toList();
    }

    @Override
    public BookDTO findById(Long id) {
        return new BookDTO(this.bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageError.BOOK_NOT_FOUND.getMessage())));
    }


    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageError.BOOK_NOT_FOUND.getMessage()));


        List<Author> authors = this.authorService.findByName(bookDTO);
        if (authors.size() != bookDTO.getAuthors().size()){
            throw new EntityNotFoundException(MessageError.AUTHOR_NOT_FOUND.getMessage());
        }

        book.update(bookDTO, authors);
        return new BookDTO(this.bookRepository.save(book));
    }


    @Override
    public void deleteBook(Long id) {
        Book book = this.bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageError.BOOK_NOT_FOUND.getMessage()));
        this.bookRepository.delete(book);
    }



}


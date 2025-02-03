package io.seasolutions.bookstore.domain.book;

import io.seasolutions.bookstore.common.messageError.MessageError;
import io.seasolutions.bookstore.domain.author.Author;
import io.seasolutions.bookstore.domain.author.AuthorDTO;
import io.seasolutions.bookstore.domain.author.AuthorFactory;
import io.seasolutions.bookstore.domain.author.AuthorService;
import io.seasolutions.bookstore.repositories.AuthorRepository;
import io.seasolutions.bookstore.repositories.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorService authorService;
    @Mock
    private AuthorFactory authorFactory;
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Book Service create method return Success")
    void bookServiceCreateSuccess() {

        Author author1 = authorFactory.getAuthor();
        Author author2 = authorFactory.getAuthor();

        AuthorDTO authorDTO1 = new AuthorDTO(author1);
        AuthorDTO authorDTO2 = new AuthorDTO(author2);


        when(authorRepository.save(any())).thenReturn(author1);

        authorRepository.save(author2);
        when(authorService.findByName(any())).thenReturn(List.of(author1, author2));
        when(bookRepository.save(any())).thenReturn(BookFactory.getBook(List.of(author1, author2)));
        BookDTO result = bookService.createBook(BookFactory.getBookDTO(List.of(authorDTO1, authorDTO2)));

        assertNotNull(result);
        assertEquals(BookFactory.getBookDTO(List.of(authorDTO1, authorDTO2)), result);

    }

    @Test
    @DisplayName("Book Service create method return EntityNotFoundException")
    void bookServiceCreateEntityNotFoundException(){

        Author author1 = authorFactory.getAuthor();
        Author author2 = authorFactory.getAuthor();

        AuthorDTO authorDTO1 = new AuthorDTO(author1);
        AuthorDTO authorDTO2 = new AuthorDTO(author2);

        authorRepository.save(author1);

        when(authorService.findByName(any())).thenReturn(List.of(author1));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> bookService.createBook(BookFactory
                .getBookDTO(List.of(authorDTO1, authorDTO2))));

        assertEquals(exception.getMessage(), MessageError.AUTHOR_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("Book Service findById method return Success")
    void bookServiceFindByIdSuccess() {

        Author author1 = authorFactory.getAuthor();
        AuthorDTO authorDTO1 = new AuthorDTO(author1);
        Book book = BookFactory.getBook(List.of(author1));

        authorRepository.save(author1);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        BookDTO result = bookService.findById(1L);

        assertNotNull(result);
        assertEquals(BookFactory.getBookDTO(List.of(authorDTO1)), result);

    }

    @Test
    @DisplayName("Book Service findById method return EntityNotFoundException")
    void bookServiceFindByIdEntityNotFound() {

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> bookService.findById(1L));
        assertEquals(exception.getMessage(), MessageError.BOOK_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("Book Service update method return Success")
    void bookServiceUpdateSuccess(){


        Book book = BookFactory.getBook(List.of(authorFactory.getAuthor()));
        BookDTO bookDTO = new BookDTO(book);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorService.findByName(any())).thenReturn(List.of(authorFactory.getAuthor()));
        when(bookRepository.save(any())).thenReturn(book);

        BookDTO result = bookService.updateBook(1L, bookDTO);

        assertNotNull(result);
        assertEquals(bookService.updateBook(1L, bookDTO), bookDTO);

    }


    @Test
    @DisplayName("Book Service update method return EntityNotFoundException")
    void bookServiceUpdateBookEntityNotFoundException(){

        Book book = BookFactory.getBook(List.of(authorFactory.getAuthor()));
        BookDTO bookDTO = new BookDTO(book);

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> bookService.updateBook(1L, bookDTO));
        assertEquals(exception.getMessage(), MessageError.BOOK_NOT_FOUND.getMessage());

    }

    @Test
    @DisplayName("Book Service update method return EntityNotFoundException")
    void bookServiceUpdateAuthorEntityNotFoundException(){

        Author author1 = authorFactory.getAuthor();
        Author author2 = authorFactory.getAuthor();
        Book book = BookFactory.getBook(List.of(author1));

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorService.findByName(any())).thenReturn(List.of(author1, author2));
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> bookService
                .updateBook(1L, BookFactory.getBookDTO(List.of(new AuthorDTO()))));

        assertEquals(exception.getMessage(), MessageError.AUTHOR_NOT_FOUND.getMessage());

    }

    @Test
    @DisplayName("Book Service delete method return Success")
    void deleteBook() {
        Book book = BookFactory.getBook(List.of(authorFactory.getAuthor()));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        assertDoesNotThrow(() -> bookService.deleteBook(1L));
    }

    @Test
    @DisplayName("Book Service delete method return EntityNotFoundException")
    void deleteBookEntityNotFoundException() {

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> bookService.deleteBook(1L));
        assertEquals(exception.getMessage(), MessageError.BOOK_NOT_FOUND.getMessage());
    }
}
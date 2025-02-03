package io.seasolutions.bookstore.domain.author;

import io.seasolutions.bookstore.common.exception.AuthorHasBooksException;
import io.seasolutions.bookstore.common.exception.ConflictException;
import io.seasolutions.bookstore.common.messageError.MessageError;
import io.seasolutions.bookstore.domain.book.Book;
import io.seasolutions.bookstore.domain.book.BookDTO;
import io.seasolutions.bookstore.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    AuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    @DisplayName("Author Service create method return Success")
    void authorServiceCreateSuccess() {

        when(authorRepository.save(any())).thenReturn(AuthorFactory.getAuthor());
        AuthorDTO result = authorService.createAuthor(AuthorFactory.getAuthorDTO());
        assertNotNull(result);
        assertEquals(AuthorFactory.getAuthorDTO(), result);

    }

    @Test
    @DisplayName("Author Service create method return ConflictException")
    void authorServiceCreateConflictException(){
        when(authorRepository.save(any())).thenThrow(new ConflictException(MessageError.AUTHOR_ALREADY_EXISTS));
        assertThrows(RuntimeException.class, () -> authorService.createAuthor(AuthorFactory.getAuthorDTO()));
    }

    @Test
    @DisplayName("Author Service findById method return Success")
    void authorServiceFindByIdSuccess() {

        when(authorRepository.findById(any())).thenReturn(Optional.of(AuthorFactory.getAuthor()));
        AuthorDTO result = authorService.findById(1L);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(AuthorFactory.getAuthorDTO(), result);
    }

    @Test
    @DisplayName("Author Service findById method return NotFoundException")
    void authorServiceUpdateNotFoundException() {

        when(authorRepository.findById(any())).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () -> authorService.findById(1L));

    }

    @Test
    @DisplayName("Author Service update method return Success")
    void authorServiceUpdateSuccess(){

        when(authorRepository.findById(any())).thenReturn(Optional.ofNullable(AuthorFactory.getAuthor()));
        when(authorRepository.save(any())).thenReturn(AuthorFactory.getAuthor());

        AuthorDTO result = authorService.updateAuthor(1L, AuthorFactory.getAuthorDTO());
        Assertions.assertNotNull(result);
        Assertions.assertEquals(AuthorFactory.getAuthorDTO(), result);

    }

    @Test
    @DisplayName("Author Service delete method return Success")
    void authorServiceDeleteSuccess() {

        when(authorRepository.findById(any())).thenReturn(Optional.of(AuthorFactory.getAuthor()));
        Assertions.assertDoesNotThrow(() -> authorService.deleteAuthor(1L));
    }

    @Test
    @DisplayName("Author Service delete method return NotFoundException")
    void authorServiceDeleteNotFoundException() {

        when(authorRepository.findById(any())).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> authorService.deleteAuthor(1L));
    }

    @Test
    @DisplayName("Author Service delete method return AuthorCannotBeDeletedException")
    void authorServiceDeleteAuthorCannotBeDeletedException() {

        Author author = new Author();
        author.setId(1L);
        author.setBooks(Arrays.asList(new Book(), new Book()));


        when(authorRepository.findById(1L)).thenReturn(Optional.of(AuthorFactory.getAuthor(author.getBooks())));

        AuthorHasBooksException exception = assertThrows(AuthorHasBooksException.class, () -> authorService.deleteAuthor(1L));

        Assertions.assertEquals(MessageError.AUTHOR_CANNOT_BE_DELETED, exception.messageError);

    }


    @Test
    @DisplayName("Author Service findByName method return Success")
    void authorServiceFindByNameSuccess() {


    Author author1 = new Author();
    author1.setName("Author 1");
    Author author2 = new Author();
    author2.setName("Author 2");
    Book book = new Book();
    book.setAuthors(Arrays.asList(author1, author2));


    when(authorRepository.findByName("Author 1")).thenReturn(Optional.of(author1));
    when(authorRepository.findByName("Author 2")).thenReturn(Optional.of(author2));

    List<Author> authors = authorService.findByName(new BookDTO(book));

    assertNotNull(authors);
    assertEquals(2, authors.size());
    assertEquals("Author 1", authors.get(0).getName());
    assertEquals("Author 2", authors.get(1).getName());

    }

    @Test
    @DisplayName("Author Service findByName method return NotFoundException")
    void authorServiceFindByNameNotFoundException() {
        Author author1 = new Author();
        author1.setName("Author Name 1");
        Author author2 = new Author();
        author2.setName("Author Name 2");
        Book book = new Book();
        book.setAuthors(Arrays.asList(author1, author2));

        BookDTO bookDTO = new BookDTO(book);

        when(authorRepository.findByName("Author Name 1")).thenReturn(Optional.of(new Author()));
        when(authorRepository.findByName("Author Name 2")).thenReturn(Optional.empty());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> authorService.findByName(bookDTO));

        assertEquals(exception.getMessage(), MessageError.AUTHOR_NOT_FOUND.getMessage() + "Author Name 2");

    }
}
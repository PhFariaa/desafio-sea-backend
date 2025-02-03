package io.seasolutions.bookstore.domain.book;

import io.seasolutions.bookstore.domain.author.Author;
import io.seasolutions.bookstore.domain.author.AuthorDTO;
import org.mockito.stubbing.OngoingStubbing;

import java.time.LocalDate;
import java.util.List;

public class BookFactory {

    public static Book getBook(List<Author> authors) {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book Title");
        book.setPublicationDate(LocalDate.parse("2021-01-01"));
        book.setNumberOfPages(250);
        book.setIsbn("123456");
        book.setAuthors(authors);
        return book;
    }

    public static BookDTO getBookDTO(List <AuthorDTO> authors) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(1L);
        bookDTO.setTitle("Book Title");
        bookDTO.setPublicationDate(LocalDate.parse("2021-01-01"));
        bookDTO.setNumberOfPages(250);
        bookDTO.setIsbn("123456");
        bookDTO.setAuthors(authors);
        return bookDTO;
    }
}

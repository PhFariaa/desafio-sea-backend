package io.seasolutions.bookstore.domain.author;

import io.seasolutions.bookstore.domain.book.Book;

import java.time.LocalDate;
import java.util.List;

public class AuthorFactory {


    public static Author getAuthor() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Author Name");
        author.setBirthDate(LocalDate.parse("1990-01-01"));
        author.setNationality("Brazil");
        return author;
    }

    public static Author getAuthor(List<Book> books){
        Author author = new Author();
        author.setId(1L);
        author.setName("Author Name");
        author.setBirthDate(LocalDate.parse("1990-01-01"));
        author.setNationality("Brazil");
        author.setBooks(books);
        return author;
    }

    public static AuthorDTO getAuthorDTO() {
    AuthorDTO authorDTO = new AuthorDTO();
    authorDTO.setId(1L);
    authorDTO.setName("Author Name");
    authorDTO.setBirthDate(LocalDate.parse("1990-01-01"));
    authorDTO.setNationality("Brazil");
    return authorDTO;


    }
}

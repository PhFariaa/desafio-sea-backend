package io.seasolutions.bookstore.domain.book;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.seasolutions.bookstore.domain.author.Author;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "books")
@Entity(name = "book")
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String isbn;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    @ManyToMany (cascade = CascadeType.PERSIST)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"),
                inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors = new ArrayList<>();

    @Column(name = "loaned")
    private boolean isLoaned;

    public Book(BookDTO bookDTO, List<Author> authors) {
        if (bookDTO.getId() != null) {
            this.id = bookDTO.getId();
        }
        this.title = bookDTO.getTitle();
        this.isbn = bookDTO.getIsbn();
        this.publicationDate = bookDTO.getPublicationDate();
        this.numberOfPages = bookDTO.getNumberOfPages();
        this.authors = authors;
    }

    public Book() {}


    public void update(BookDTO bookDTO, List<Author> authors) {
        this.title = bookDTO.getTitle();
        this.isbn = bookDTO.getIsbn();
        this.publicationDate = bookDTO.getPublicationDate();
        this.numberOfPages = bookDTO.getNumberOfPages();
        this.authors = authors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @JsonManagedReference
    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public boolean isLoaned() {
        return isLoaned;
    }

    public void setLoaned(boolean loaned) {
        isLoaned = loaned;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isLoaned == book.isLoaned && Objects.equals(id, book.id) && Objects.equals(title, book.title) && Objects.equals(isbn, book.isbn) && Objects.equals(publicationDate, book.publicationDate) && Objects.equals(numberOfPages, book.numberOfPages) && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, isbn, publicationDate, numberOfPages, authors, isLoaned);
    }
}

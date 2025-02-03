package io.seasolutions.bookstore.domain.author;


import com.fasterxml.jackson.annotation.JsonBackReference;
import io.seasolutions.bookstore.domain.book.Book;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "authors")
@Entity(name = "author")
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    private String nationality;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();


    public Author() {}

    public Author(AuthorDTO authorDTO) {
        if (authorDTO.getId() != null) {
            this.id = authorDTO.getId();
        }
        this.name = authorDTO.getName();
        this.birthDate = authorDTO.getBirthDate();
        this.nationality = authorDTO.getNationality();
    }


    public void update(AuthorDTO authorDTO) {
        this.name = authorDTO.getName();
        this.birthDate = authorDTO.getBirthDate();
        this.nationality = authorDTO.getNationality();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @JsonBackReference
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(name, author.name) && Objects.equals(birthDate, author.birthDate) && Objects.equals(nationality, author.nationality) && Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, nationality, books);
    }
}

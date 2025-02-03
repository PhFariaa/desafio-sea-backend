package io.seasolutions.bookstore.domain.book;

import io.seasolutions.bookstore.domain.author.AuthorDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookDTO {

    private Long id;
    @NotBlank
    private String title;
    @Pattern(regexp = "\\d{13}")
    private String isbn;
    @NotBlank
    private LocalDate publicationDate;
    @NotBlank
    private Integer numberOfPages;
    @NotBlank
    private boolean isLoaned;
    @NotNull
    @Size(min = 1, message = "At least one author is required")
    private List<AuthorDTO> authors;

    public BookDTO(Long id, String title, String isbn, LocalDate publicationDate, Integer numberOfPages, boolean isLoaned, List<AuthorDTO> authors) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.numberOfPages = numberOfPages;
        this.isLoaned = isLoaned;
        this.authors = authors;
    }

        public BookDTO(Book book) {
        this(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPublicationDate(),
                book.getNumberOfPages(),
                book.isLoaned(),
                book.getAuthors().stream()
                        .map(author -> new AuthorDTO(author.getId(),
                                author.getName(),
                                author.getBirthDate(),
                                author.getNationality())).collect(Collectors.toList())
        );
    }

        public BookDTO() {}

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

    public boolean isLoaned() {
        return isLoaned;
    }

    public void setLoaned(boolean loaned) {
        isLoaned = loaned;
    }

    public List<AuthorDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorDTO> authors) {
        this.authors = authors;
    }


        @Override
        public boolean equals(Object o) {
                if (o == null || getClass() != o.getClass()) return false;
                BookDTO bookDTO = (BookDTO) o;
                return isLoaned == bookDTO.isLoaned && Objects.equals(id, bookDTO.id) && Objects.equals(title, bookDTO.title) && Objects.equals(isbn, bookDTO.isbn) && Objects.equals(publicationDate, bookDTO.publicationDate) && Objects.equals(numberOfPages, bookDTO.numberOfPages) && Objects.equals(authors, bookDTO.authors);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, title, isbn, publicationDate, numberOfPages, isLoaned, authors);
        }
}

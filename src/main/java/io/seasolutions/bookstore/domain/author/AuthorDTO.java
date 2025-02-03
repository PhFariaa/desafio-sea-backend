package io.seasolutions.bookstore.domain.author;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Objects;

public class AuthorDTO {

    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private LocalDate birthDate;
    @NotBlank
    private String nationality;


    public AuthorDTO(){}

    public AuthorDTO(Long id, String name, LocalDate birthDate, String nationality) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.nationality = nationality;
    }

    public AuthorDTO(Author author) {
        this(
                author.getId(),
                author.getName(),
                author.getBirthDate(),
                author.getNationality()
        );
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


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDTO authorDTO = (AuthorDTO) o;
        return Objects.equals(id, authorDTO.id) && Objects.equals(name, authorDTO.name) && Objects.equals(birthDate, authorDTO.birthDate) && Objects.equals(nationality, authorDTO.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, nationality);
    }
}

package io.seasolutions.bookstore.repositories;

import io.seasolutions.bookstore.domain.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

}

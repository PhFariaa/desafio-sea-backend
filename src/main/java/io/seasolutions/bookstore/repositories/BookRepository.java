package io.seasolutions.bookstore.repositories;

import io.seasolutions.bookstore.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

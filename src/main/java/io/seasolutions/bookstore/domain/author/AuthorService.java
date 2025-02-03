package io.seasolutions.bookstore.domain.author;

import io.seasolutions.bookstore.domain.book.BookDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {
    AuthorDTO createAuthor(AuthorDTO authorDTO);

    List<AuthorDTO> findAll(Pageable pageable);

    AuthorDTO findById(Long id);

    AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO);

    void deleteAuthor(Long id);

    List<Author> findByName(BookDTO bookDTO);


}

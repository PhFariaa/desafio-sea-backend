package io.seasolutions.bookstore.domain.book;


import io.seasolutions.bookstore.domain.author.Author;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BookService {

    BookDTO createBook(BookDTO bookDTO);

    BookDTO findById(Long id);

    List<BookDTO> findAll(Pageable pageable);

    BookDTO updateBook(Long id, BookDTO bookDTO);

    void deleteBook(Long id);


}

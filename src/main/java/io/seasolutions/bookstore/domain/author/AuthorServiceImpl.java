package io.seasolutions.bookstore.domain.author;


import io.seasolutions.bookstore.common.exception.AuthorHasBooksException;
import io.seasolutions.bookstore.common.exception.ConflictException;
import io.seasolutions.bookstore.common.messageError.MessageError;
import io.seasolutions.bookstore.domain.book.BookDTO;
import io.seasolutions.bookstore.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService{

    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = new Author(authorDTO);
        try{
         this.authorRepository.save(author);

        } catch (Exception e){
            throw new ConflictException(MessageError.AUTHOR_ALREADY_EXISTS);
        }

        return new AuthorDTO(author);
    }

    @Override
    public List<AuthorDTO> findAll(Pageable pageable) {
        return this.authorRepository.findAll().stream().map(AuthorDTO::new).toList();
    }

    @Override
    public AuthorDTO findById(Long id) {
        return new AuthorDTO(this.authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageError.AUTHOR_NOT_FOUND.getMessage())));
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = this.authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageError.AUTHOR_NOT_FOUND.getMessage()));
        author.update(authorDTO);
        return new AuthorDTO(this.authorRepository.save(author));
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = this.authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageError.AUTHOR_NOT_FOUND.getMessage()));

        if (!author.getBooks().isEmpty() && author.getBooks() != null) {
            throw new AuthorHasBooksException(MessageError.AUTHOR_CANNOT_BE_DELETED);
        }
        this.authorRepository.delete(author);
    }

    @Override
    public List<Author> findByName(BookDTO bookDTO) {
        List<Author> authors = bookDTO.getAuthors().stream()
            .map(authorDTO -> {
                Optional<Author> author = this.authorRepository.findByName(authorDTO.getName());
                if (author.isEmpty()) {
                    throw new EntityNotFoundException(MessageError.AUTHOR_NOT_FOUND.getMessage() + authorDTO.getName());
                }
                return author.get();
            })
            .collect(Collectors.toList());
        return authors;
    }


}

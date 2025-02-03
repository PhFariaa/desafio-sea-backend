package io.seasolutions.bookstore.domain.loan;

import io.seasolutions.bookstore.common.exception.ConflictException;
import io.seasolutions.bookstore.common.exception.LimitExceededException;
import io.seasolutions.bookstore.common.messageError.MessageError;
import io.seasolutions.bookstore.domain.book.Book;
import io.seasolutions.bookstore.domain.user.User;
import io.seasolutions.bookstore.repositories.BookRepository;
import io.seasolutions.bookstore.repositories.LoanRepository;
import io.seasolutions.bookstore.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanService{


    private LoanRepository loanRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;

    public LoanServiceImpl(LoanRepository loanRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public LoanShowDetailsDTO createLoan(LoanRegisterDTO loanRegisterDTO) {

        User user = this.userRepository.findById(loanRegisterDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(MessageError.USER_NOT_FOUND.getMessage()));

        Book book = this.bookRepository.findById(loanRegisterDTO.getBookId())
                .orElseThrow(() -> new EntityNotFoundException(MessageError.BOOK_NOT_FOUND.getMessage()));



        if (this.loanRepository.findByUserIdAndIsActiveIsTrue(loanRegisterDTO.getUserId()).size() == 5){
            throw new LimitExceededException(MessageError.USER_LOAN_LIMIT_EXCEEDED);
        }

        this.loanRepository.findByBookIdAndIsActiveIsTrue(loanRegisterDTO.getBookId()).ifPresent(l -> {
            throw new ConflictException(MessageError.BOOK_ALREADY_LOANED);
        });

        Loan loan = new Loan(loanRegisterDTO, user, book);
        this.loanRepository.save(loan);
        return new LoanShowDetailsDTO(loan);
    }

    @Override
    public LoanShowDetailsDTO findById(Long id) {
        return new LoanShowDetailsDTO(this.loanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageError.LOAN_NOT_FOUND.getMessage())));
        }


    @Override
    public List<LoanShowDetailsDTO> findAll(Pageable pageable) {
        return this.loanRepository.findByIsActiveTrue(pageable).stream().map(LoanShowDetailsDTO::new).toList();
    }

    @Override
    public LoanShowDetailsDTO returnBook(Long id) {
        Loan loan = this.loanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(MessageError.LOAN_NOT_FOUND.getMessage()));

        LocalDate returnDate = LocalDate.now();
        if (returnDate.isAfter(loan.getLoanDate().plusDays(14))){
            loan.calculateFine(returnDate);
        }

        loan.returnBook(loan);
        return new LoanShowDetailsDTO(this.loanRepository.save(loan));
    }

    @Override
    public List<LoanShowDetailsDTO> findByUserId(Long userId, Pageable pageable) {
        return this.loanRepository.findByUserId(userId, pageable).stream().map(LoanShowDetailsDTO::new).toList();
    }

    @Override
    public List<LoanShowDetailsDTO> findActiveByUserId(Long userId, Pageable pageable) {
        return this.loanRepository.findByUserIdAndIsActiveIsTrue(userId).stream().map(LoanShowDetailsDTO::new).toList();
    }
}

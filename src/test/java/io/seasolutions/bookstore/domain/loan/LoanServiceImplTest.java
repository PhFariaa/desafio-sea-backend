package io.seasolutions.bookstore.domain.loan;

import io.seasolutions.bookstore.common.exception.ConflictException;
import io.seasolutions.bookstore.common.exception.LimitExceededException;
import io.seasolutions.bookstore.common.messageError.MessageError;
import io.seasolutions.bookstore.domain.author.AuthorFactory;
import io.seasolutions.bookstore.domain.book.Book;
import io.seasolutions.bookstore.domain.book.BookFactory;
import io.seasolutions.bookstore.domain.user.User;
import io.seasolutions.bookstore.domain.user.UserFactory;
import io.seasolutions.bookstore.repositories.BookRepository;
import io.seasolutions.bookstore.repositories.LoanRepository;
import io.seasolutions.bookstore.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {
    @Mock
    private LoanRepository loanRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private LoanFactory loanFactory;
    @InjectMocks
    private LoanServiceImpl loanService;


    @Test
    @DisplayName("Loan Service create method return Success")
    void loanServiceCreateMethodSuccess() {

        Loan loan = loanFactory.createLoan();
        User user = loan.getUser();
        Book book = loan.getBook();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        when(loanRepository.findByUserIdAndIsActiveIsTrue(user.getId())).thenReturn(List.of(loan));
        when(loanRepository.findByBookIdAndIsActiveIsTrue(any())).thenReturn(Optional.empty());

        LoanShowDetailsDTO result = loanService.createLoan(loanFactory.createLoanDTO());
        assertNotNull(result);
        assertEquals(result, loanFactory.createLoanShowDetailsDTO());
    }

    @Test
    @DisplayName("Loan Service create method return LimitExceededException")
    void loanServiceCreateMethodLimitExceededException() {

    Loan loan1 = loanFactory.createLoan();
    Loan loan2 = loanFactory.createLoan();
    Loan loan3 = loanFactory.createLoan();
    Loan loan4 = loanFactory.createLoan();
    Loan loan5 = loanFactory.createLoan();

    Book book1 = loan1.getBook();

    User user = loan1.getUser();

    LoanRegisterDTO loan1DTO = loanFactory.createLoanDTO();

    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    when(bookRepository.findById(any())).thenReturn(Optional.of(book1));
    when(loanRepository.findByUserIdAndIsActiveIsTrue(user.getId())).thenReturn(Arrays.asList(loan1, loan2, loan3, loan4, loan5));

    LimitExceededException exception = assertThrows(LimitExceededException.class, () -> loanService.createLoan(loan1DTO));

    assertEquals(MessageError.USER_LOAN_LIMIT_EXCEEDED, exception.messageError);

    }

    @Test
    @DisplayName("Loan Service create method return User EntityNotFoundException")
    void loanServiceCreateMethodUserEntityNotFoundException() {

    when(userRepository.findById(any())).thenThrow(new EntityNotFoundException(MessageError.USER_NOT_FOUND.getMessage()));

    assertThrows(EntityNotFoundException.class, () -> loanService.createLoan(loanFactory.createLoanDTO()));

    }
    @Test
    @DisplayName("Loan Service create method return Book EntityNotFoundException")
    void loanServiceCreateMethodBookEntityNotFoundException() {
        Loan loan = loanFactory.createLoan();
        User user = loan.getUser();

    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    when(bookRepository.findById(any())).thenThrow(new EntityNotFoundException(MessageError.BOOK_NOT_FOUND.getMessage()));

    assertThrows(EntityNotFoundException.class, () -> loanService.createLoan(loanFactory.createLoanDTO()));

    }
    @Test
    @DisplayName("Loan Service create method return Book ConflictException")
    void loanServiceCreateMethodBookConflictException() {

        Loan loan = loanFactory.createLoan();
        Book book = loan.getBook();
        User user = loan.getUser();

    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    when(bookRepository.findById(any())).thenReturn(Optional.of(book));
    when(loanRepository.findByUserIdAndIsActiveIsTrue(book.getId())).thenReturn(List.of(loan));
    when(loanRepository.findByBookIdAndIsActiveIsTrue(any())).thenThrow(new ConflictException(MessageError.BOOK_ALREADY_LOANED));

    assertThrows(ConflictException.class, () -> loanService.createLoan(loanFactory.createLoanDTO()));

    }



    @Test
    @DisplayName("Loan Service findById method return Success")
    void loanServiceFindByIdSuccess() {

        Loan loan = loanFactory.createLoan();
        loan.getBook().setLoaned(true);
        loan.setIsActive(true);

        when(loanRepository.findById(any())).thenReturn(Optional.of(loan));
        LoanShowDetailsDTO result = loanService.findById(1L);
        assertNotNull(result);
        assertEquals(result, loanFactory.createLoanShowDetailsDTO(loan.getIsActive(), loan.getBook().isLoaned()));
    }

    @Test
    @DisplayName("Loan Service findById method return EntityNotFoundException")
    void loanServiceFindByIdEntityNotFoundException() {
        when(loanRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> loanService.findById(1L));
    }

    @Test
    @DisplayName("Loan Service returnBook method return Success")
    void loanServiceReturnBookMethodSuccess() {

        Loan loan = loanFactory.createLoan();


        when(loanRepository.findById(any())).thenReturn(Optional.of(loan));
        when(loanRepository.save(any())).thenReturn(loan);
        LoanShowDetailsDTO result = loanService.returnBook(1L);
        assertNotNull(result);
        assertEquals(result, loanFactory.createLoanShowDetailsDTO(loan.getIsActive(), loan.getBook().isLoaned()));
    }

    @Test
    @DisplayName("Loan Service returnBook method return EntityNotFoundException")
    void loanServiceReturnBookMethodEntityNotFoundException() {

        when(loanRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> loanService.returnBook(1L));

    }

    @Test
    @DisplayName("Loan Service findByUserId method return Success")
    void loanServiceFindByUserIdSuccess() {
        Loan loan = loanFactory.createLoan();
        loan.getBook().setLoaned(true);
        loan.setIsActive(true);

        when(loanRepository.findByUserId(any(), any())).thenReturn(List.of(loan));
        List<LoanShowDetailsDTO> result = loanService.findByUserId(1L, null);
        assertNotNull(result);
        assertEquals(result, List.of(loanFactory.createLoanShowDetailsDTO(loan.getIsActive(), loan.getBook().isLoaned())));
    }

    @Test
    @DisplayName("Loan Service findByUserId method return EntityNotFoundException")
    void loanServiceFindByUserIdEntityNotFoundException(){
        when(loanRepository.findByUserId(any(), any())).thenThrow(new EntityNotFoundException(MessageError.USER_NOT_FOUND.getMessage()));
        assertThrows(EntityNotFoundException.class, () -> loanService.findByUserId(1L, null));
    }

    @Test
    @DisplayName("Loan Service findActiveByUserId method return Success")
    void loanServicefindActiveByUserIdMethodSuccess() {
        Loan loan = loanFactory.createLoan();
        loan.getBook().setLoaned(true);
        loan.setIsActive(true);

        when(loanRepository.findByUserIdAndIsActiveIsTrue(any())).thenReturn(List.of(loan));
        List<LoanShowDetailsDTO> result = loanService.findActiveByUserId(1L, null);
        assertNotNull(result);
        assertEquals(result, List.of(loanFactory.createLoanShowDetailsDTO(loan.getIsActive(), loan.getBook().isLoaned())));
    }

    @Test
    @DisplayName("Loan Service findActiveByUserId method return EntityNotFoundException")
    void loanServicefindActiveByUserIdMethodEntityNotFoundException(){

        when(loanRepository.findByUserIdAndIsActiveIsTrue(any())).thenThrow(new EntityNotFoundException(MessageError.USER_NOT_FOUND.getMessage()));
        assertThrows(EntityNotFoundException.class, () -> loanService.findActiveByUserId(1L, null));
    }

}

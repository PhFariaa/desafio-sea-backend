package io.seasolutions.bookstore.domain.loan;

import io.seasolutions.bookstore.domain.author.AuthorFactory;
import io.seasolutions.bookstore.domain.book.BookFactory;
import io.seasolutions.bookstore.domain.user.UserFactory;

import java.time.LocalDate;
import java.util.List;

public class LoanFactory {

    private static BookFactory bookFactory;
    private static AuthorFactory authorFactory;

    public static Loan createLoan() {

        Loan loan = new Loan();
        loan.setId(1L);
        loan.setBook(bookFactory.getBook(List.of(authorFactory.getAuthor())));
        loan.setUser(UserFactory.getUser());
        loan.setLoanDate(LocalDate.now());
        return loan;
    }

    public static LoanRegisterDTO createLoanDTO() {
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setBook(bookFactory.getBook(List.of(authorFactory.getAuthor())));
        loan.setUser(UserFactory.getUser());
        loan.setLoanDate(LocalDate.now());
        return new LoanRegisterDTO(loan);
    }

    public static LoanShowDetailsDTO createLoanShowDetailsDTO(boolean isActive, boolean isLoaned) {
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setUser(UserFactory.getUser());
        loan.setBook(bookFactory.getBook(List.of(authorFactory.getAuthor())));
        loan.setLoanDate(LocalDate.now());
        loan.getBook().setLoaned(isLoaned);
        loan.setIsActive(isActive);


        return new LoanShowDetailsDTO(loan);

    }

    public static LoanShowDetailsDTO createLoanShowDetailsDTO() {
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setUser(UserFactory.getUser());
        loan.setBook(bookFactory.getBook(List.of(authorFactory.getAuthor())));
        loan.setLoanDate(LocalDate.now());
        loan.getBook().setLoaned(true);
        loan.setIsActive(true);
        return new LoanShowDetailsDTO(loan);
    }
}

package io.seasolutions.bookstore.domain.loan;


import io.seasolutions.bookstore.common.messageError.MessageError;
import io.seasolutions.bookstore.domain.book.Book;
import io.seasolutions.bookstore.domain.user.User;
import io.seasolutions.bookstore.repositories.BookRepository;
import io.seasolutions.bookstore.repositories.LoanRepository;
import io.seasolutions.bookstore.repositories.UserRepository;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Table(name = "loans")
@Entity(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    private Book book;

    @Column(name = "loaned_date")
    private LocalDate loanDate;

    @Column(name = "returned_date")
    private LocalDate returnDate;

    @Column(name = "is_active")
    private boolean isActive;


    private Float fine;


    public void returnBook(Loan loan) {
        loan.setReturnDate(LocalDate.now());
        loan.getBook().setLoaned(false);
        loan.setIsActive(false);
    }


    public Loan() {}

    public Loan(LoanRegisterDTO loanRegisterDTO, User user, Book book) {

        if (loanRegisterDTO.getId() != null) {
            this.id = loanRegisterDTO.getId();
        }
        this.user = user;
        this.book = book;
        this.book.setLoaned(true);
        this.loanDate = LocalDate.now();
        this.returnDate = null;
        this.isActive = true;
    }


    public Float calculateFine(LocalDate returnDate) {
        fine = ChronoUnit.DAYS.between(this.loanDate.plusDays(14), returnDate) * 2.00f;
        setFine(fine);
        return fine;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Float getFine() {
        return fine;
    }

    public void setFine(Float fine) {
        this.fine = fine;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return isActive == loan.isActive && Objects.equals(id, loan.id) && Objects.equals(user, loan.user) && Objects.equals(book, loan.book) && Objects.equals(loanDate, loan.loanDate) && Objects.equals(returnDate, loan.returnDate) && Objects.equals(fine, loan.fine);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, book, loanDate, returnDate, isActive, fine);
    }
}

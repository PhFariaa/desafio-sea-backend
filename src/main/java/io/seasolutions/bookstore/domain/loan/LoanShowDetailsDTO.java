package io.seasolutions.bookstore.domain.loan;

import io.seasolutions.bookstore.domain.book.Book;
import io.seasolutions.bookstore.domain.user.User;

import java.util.Objects;

public class LoanShowDetailsDTO {

    private Long id;
    private Float fine;
    private boolean isActive;
    private User user;
    private Book book;

    public LoanShowDetailsDTO() {}

    public LoanShowDetailsDTO(Long id, Float fine, boolean isActive, User user, Book book) {
        this.id = id;
        this.fine = fine;
        this.isActive = isActive;
        this.user = user;
        this.book = book;
    }

    public LoanShowDetailsDTO(Loan loan) {
        this(loan.getId(), loan.getFine(), loan.getIsActive(), loan.getUser(), loan.getBook());
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getFine() {
        return fine;
    }

    public void setFine(Float fine) {
        this.fine = fine;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LoanShowDetailsDTO that = (LoanShowDetailsDTO) o;
        return isActive == that.isActive && Objects.equals(id, that.id) && Objects.equals(fine, that.fine) && Objects.equals(user, that.user) && Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fine, isActive, user, book);
    }
}

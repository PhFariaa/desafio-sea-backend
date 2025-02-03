package io.seasolutions.bookstore.domain.loan;

public class LoanRegisterDTO {

    private Long id;
    private Long userId;
    private Long bookId;

    public LoanRegisterDTO() {}

    public LoanRegisterDTO(Long id, Long userId, Long bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
    }

    public LoanRegisterDTO(Loan loan) {
        this(loan.getId(), loan.getUser().getId(), loan.getBook().getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}

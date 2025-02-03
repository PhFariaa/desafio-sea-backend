package io.seasolutions.bookstore.domain.loan;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LoanService {
    LoanShowDetailsDTO createLoan(LoanRegisterDTO loanRegisterDTO);

    LoanShowDetailsDTO findById(Long id);

    List<LoanShowDetailsDTO> findAll(Pageable pageable);

    LoanShowDetailsDTO returnBook(Long id);

    List<LoanShowDetailsDTO> findByUserId(Long userId, Pageable pageable);
    List<LoanShowDetailsDTO> findActiveByUserId(Long userId, Pageable pageable);
}

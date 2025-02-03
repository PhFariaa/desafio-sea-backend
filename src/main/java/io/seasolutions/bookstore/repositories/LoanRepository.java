package io.seasolutions.bookstore.repositories;

import io.seasolutions.bookstore.domain.loan.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    Page<Loan> findByIsActiveTrue(Pageable pageable);

    Optional<Loan> findByBookIdAndIsActiveIsTrue(Long id);

    List<Loan> findByUserIdAndIsActiveIsTrue(Long id);

    List<Loan> findByUserId(Long userId, Pageable pageable);
}

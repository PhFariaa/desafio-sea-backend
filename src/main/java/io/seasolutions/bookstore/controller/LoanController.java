package io.seasolutions.bookstore.controller;


import io.seasolutions.bookstore.domain.loan.LoanRegisterDTO;
import io.seasolutions.bookstore.domain.loan.LoanService;
import io.seasolutions.bookstore.domain.loan.LoanShowDetailsDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<LoanShowDetailsDTO> createLoan(@RequestBody LoanRegisterDTO loanRegisterDTO) {
        LoanShowDetailsDTO loan = loanService.createLoan(loanRegisterDTO);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanShowDetailsDTO> getLoan(@PathVariable Long id) {
        LoanShowDetailsDTO loan = loanService.findById(id);
        return ResponseEntity.ok(loan);
    }

    @GetMapping
    public ResponseEntity<?> getLoans(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.loanService.findAll(pageable));
    }

    @PutMapping("return/{id}")
    @Transactional
    public ResponseEntity<LoanShowDetailsDTO> returnBook(@PathVariable Long id) {
        LoanShowDetailsDTO loan = loanService.returnBook(id);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getLoansByUserId(@PathVariable Long userId, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.loanService.findByUserId(userId, pageable));
    }

    @GetMapping("/user/active/{userId}")
    public ResponseEntity<?> getActiveLoansByUserId(@PathVariable Long userId, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.loanService.findActiveByUserId(userId, pageable));
    }
}

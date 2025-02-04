package io.seasolutions.bookstore.controller;


import io.seasolutions.bookstore.domain.loan.LoanRegisterDTO;
import io.seasolutions.bookstore.domain.loan.LoanService;
import io.seasolutions.bookstore.domain.loan.LoanShowDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/loans", produces = "application/json")
@Tag(name = "Loan")
public class LoanController {

    private LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }


    @PostMapping
    @Transactional
    @Operation(summary = "Create new loan", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Loan created  successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<LoanShowDetailsDTO> createLoan(@RequestBody LoanRegisterDTO loanRegisterDTO) {
        LoanShowDetailsDTO loan = loanService.createLoan(loanRegisterDTO);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan by id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get loan successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<LoanShowDetailsDTO> getLoan(@PathVariable Long id) {
        LoanShowDetailsDTO loan = loanService.findById(id);
        return ResponseEntity.ok(loan);
    }

    @GetMapping
    @Operation(summary = "Get all loans", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get loans successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getLoans(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.loanService.findAll(pageable));
    }

    @PutMapping("return/{id}")
    @Transactional
    @Operation(summary = "Return book", method = "PUT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Loan created  successfully"),
        @ApiResponse(responseCode = "400", description = "Bad Request"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<LoanShowDetailsDTO> returnBook(@PathVariable Long id) {
        LoanShowDetailsDTO loan = loanService.returnBook(id);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get loans by user id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get loans by user id successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getLoansByUserId(@PathVariable Long userId, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.loanService.findByUserId(userId, pageable));
    }


    @GetMapping("/user/active/{userId}")
    @Operation(summary = "Get active loans by user id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get active loans by user id successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> getActiveLoansByUserId(@PathVariable Long userId, @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.loanService.findActiveByUserId(userId, pageable));
    }
}

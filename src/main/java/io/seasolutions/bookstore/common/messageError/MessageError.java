package io.seasolutions.bookstore.common.messageError;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;


@JsonSerialize(using = MessageErrorSerializer.class)
public enum MessageError {

        AUTHOR_ALREADY_EXISTS(409, "AUTHOR_ALREADY_EXISTS", "Author already exists"),
        AUTHOR_NOT_FOUND(404, "AUTHOR_NOT_FOUND", "Some Authors not found"),
        AUTHOR_CANNOT_BE_DELETED(409, "AUTHOR_CANNOT_BE_DELETED", "Author cannot be deleted because he still has books"),
        BOOK_NOT_FOUND(404, "BOOK_NOT_FOUND", "Book not found"),
        USER_ALREADY_EXIST(409,"USER_ALREADY_EXISTS", "User already exists"),
        USER_NOT_FOUND(404,"USER_NOT_FOUND" ,"User not found"),
        USER_LOAN_LIMIT_EXCEEDED(400,"USER_LOAN_LIMIT_EXCEEDED","User loan limit exceeded" ),
        BOOK_ALREADY_LOANED(409,"BOOK_ALREADY_LOANED", "Book already loaned"),
        LOAN_NOT_FOUND(409,"LOAN_NOT_FOUND" ,"Loan not found" );


        private final int status;
        private final String code;
        private final String message;
    MessageError(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

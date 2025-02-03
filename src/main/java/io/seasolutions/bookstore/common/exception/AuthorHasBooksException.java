package io.seasolutions.bookstore.common.exception;

import io.seasolutions.bookstore.common.messageError.MessageError;

public class AuthorHasBooksException extends RuntimeException{
    public final MessageError messageError;

    public AuthorHasBooksException(MessageError messageError) {
        this.messageError = messageError;
    }
}

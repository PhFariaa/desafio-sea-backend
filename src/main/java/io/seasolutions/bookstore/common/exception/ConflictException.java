package io.seasolutions.bookstore.common.exception;

import io.seasolutions.bookstore.common.messageError.MessageError;

public class ConflictException extends RuntimeException{
    public final MessageError messageError;

    public ConflictException(MessageError messageError) {
        this.messageError = messageError;
    }
}


package io.seasolutions.bookstore.common.exception;

import io.seasolutions.bookstore.common.messageError.MessageError;

public class LimitExceededException extends RuntimeException {
    public final MessageError messageError;

    public LimitExceededException(MessageError messageError) {
        this.messageError = messageError;
    }
}

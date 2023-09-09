package com.fatec.tcc.agendify.CustomExceptions;

public class AccountDoesNotExistsException extends RuntimeException {
    public AccountDoesNotExistsException() {
        super();
    }

    public AccountDoesNotExistsException(String errorMessage) {
        super(errorMessage);
    }

    public AccountDoesNotExistsException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }
}

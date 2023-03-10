package com.fatec.tcc.agendeja.CustomExceptions;

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

package com.fatec.tcc.agendify.CustomExceptions;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException() {
        super();
    }

    public AccountAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }

    public AccountAlreadyExistsException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }
}

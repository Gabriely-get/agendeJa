package com.fatec.tcc.agendeja.CustomExceptions;

public class UserDoesNotExistsException extends RuntimeException {
    public UserDoesNotExistsException() {
        super();
    }

    public UserDoesNotExistsException(String errorMessage) {
        super(errorMessage);
    }

    public UserDoesNotExistsException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }

}

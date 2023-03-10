package com.fatec.tcc.agendeja.CustomExceptions;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException() {
        super();
    }

    public UserNotLoggedInException(String errorMessage) {
        super(errorMessage);
    }

    public UserNotLoggedInException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }
}

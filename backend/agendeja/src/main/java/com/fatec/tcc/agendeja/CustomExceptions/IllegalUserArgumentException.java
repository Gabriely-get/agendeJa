package com.fatec.tcc.agendeja.CustomExceptions;

public class IllegalUserArgumentException extends RuntimeException {
    public IllegalUserArgumentException() {
        super();
    }

    public IllegalUserArgumentException(String errorMessage) {
        super(errorMessage);
    }

    public IllegalUserArgumentException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }
}

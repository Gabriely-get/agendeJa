package com.fatec.tcc.agendeja.CustomExceptions;

public class InsufficientMoneyException extends RuntimeException {
    public InsufficientMoneyException() {
        super();
    }

    public InsufficientMoneyException(String errorMessage) {
        super(errorMessage);
    }

    public InsufficientMoneyException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }
}

package com.fatec.tcc.agendeja.CustomExceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super();
    }

    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }

    public NotFoundException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }

}

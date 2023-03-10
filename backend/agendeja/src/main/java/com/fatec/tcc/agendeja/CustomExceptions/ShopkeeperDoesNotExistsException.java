package com.fatec.tcc.agendeja.CustomExceptions;

public class ShopkeeperDoesNotExistsException extends RuntimeException {
    public ShopkeeperDoesNotExistsException() {
        super();
    }

    public ShopkeeperDoesNotExistsException(String errorMessage) {
        super(errorMessage);
    }

    public ShopkeeperDoesNotExistsException(String errorMessage, Exception e) {
        super(errorMessage, e);
    }

}

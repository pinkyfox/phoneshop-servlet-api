package com.es.phoneshop.exceptions;

public class NotEnoughStockException extends Exception {
    public NotEnoughStockException() {
        super();
    }
    public NotEnoughStockException(String message) {
        super(message);
    }
}

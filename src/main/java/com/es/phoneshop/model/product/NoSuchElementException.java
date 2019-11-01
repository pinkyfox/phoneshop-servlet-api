package com.es.phoneshop.model.product;

public class NoSuchElementException extends Exception {
    public NoSuchElementException() {
        super("this item doesn't exist");
    }
}

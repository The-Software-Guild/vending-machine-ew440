package com.mthree.c130.service;

public class NoItemInventoryException extends Exception {

    public NoItemInventoryException(String message) {
        super(message);
    }

    public NoItemInventoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
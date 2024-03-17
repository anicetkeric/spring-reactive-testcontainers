package com.example.testcontainers.exception;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public DataNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

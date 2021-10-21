package com.example.webshop.exceptions;

public class CodeNotUniqueException extends RuntimeException{
    public CodeNotUniqueException() {
    }

    public CodeNotUniqueException(String message) {
        super(message);
    }

    public CodeNotUniqueException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeNotUniqueException(Throwable cause) {
        super(cause);
    }

    public CodeNotUniqueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

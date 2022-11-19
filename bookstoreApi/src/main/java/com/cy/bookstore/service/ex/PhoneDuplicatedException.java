package com.cy.bookstore.service.ex;

public class PhoneDuplicatedException extends ServiceException{
    public PhoneDuplicatedException() {
    }

    public PhoneDuplicatedException(String message) {
        super(message);
    }

    public PhoneDuplicatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public PhoneDuplicatedException(Throwable cause) {
        super(cause);
    }

    public PhoneDuplicatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

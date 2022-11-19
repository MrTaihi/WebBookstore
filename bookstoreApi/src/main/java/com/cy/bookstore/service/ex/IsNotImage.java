package com.cy.bookstore.service.ex;

public class IsNotImage extends ServiceException{
    public IsNotImage() {
    }

    public IsNotImage(String message) {
        super(message);
    }

    public IsNotImage(String message, Throwable cause) {
        super(message, cause);
    }

    public IsNotImage(Throwable cause) {
        super(cause);
    }

    public IsNotImage(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

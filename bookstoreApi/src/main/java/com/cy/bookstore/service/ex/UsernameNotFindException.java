package com.cy.bookstore.service.ex;

/**
 * 用户名未找到异常
 */
public class UsernameNotFindException extends ServiceException{
    public UsernameNotFindException() {
    }

    public UsernameNotFindException(String message) {
        super(message);
    }

    public UsernameNotFindException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameNotFindException(Throwable cause) {
        super(cause);
    }

    public UsernameNotFindException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

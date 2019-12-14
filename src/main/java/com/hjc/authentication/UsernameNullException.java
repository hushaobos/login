package com.hjc.authentication;

public class UsernameNullException extends AuthenticationException{
    static final long serialVersionUID = -5478617190745766939L;

    public static final UsernameNullException USERNAME_NULL_EXCEPTION = new UsernameNullException();

    private final static String MESSAGE = "username is Not allowed to be null";

    public UsernameNullException() {
        super(MESSAGE);
    }

    public UsernameNullException(String message) {
        super(message);
    }

    public UsernameNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameNullException(Throwable cause) {
        super(MESSAGE,cause);
    }

    public UsernameNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

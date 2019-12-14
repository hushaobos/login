package com.hjc.authentication;

public class PasswordIncorrectException extends AuthenticationException {

    public static final PasswordIncorrectException PWD_INCORRECT = new PasswordIncorrectException();

    private final static String MESSAGE = "Password is Incorrect";

    public PasswordIncorrectException() {
        super(MESSAGE);
    }

    public PasswordIncorrectException(String message) {
        super(message);
    }

    public PasswordIncorrectException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordIncorrectException(Throwable cause) {
        super(cause);
    }

    public PasswordIncorrectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

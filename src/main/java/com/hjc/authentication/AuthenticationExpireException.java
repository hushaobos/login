package com.hjc.authentication;

public class AuthenticationExpireException extends AuthenticationException {
    private static final long serialVersionUID = -985451736324594147l;

    public static final AuthenticationExpireException EXPIRE_EXCEPTION = new AuthenticationExpireException();

    private final static String MESSAGE = "Authentication has expired";

    public AuthenticationExpireException() {
        super(MESSAGE);
    }

    public AuthenticationExpireException(String message) {
        super(message);
    }

    public AuthenticationExpireException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationExpireException(Throwable cause) {
        super(cause);
    }

    public AuthenticationExpireException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package ru.mikaev.blogstar.exceptions;

public class SecurityServiceException extends RuntimeException {
    public SecurityServiceException() {
        super();
    }

    public SecurityServiceException(String message) {
        super(message);
    }

    public SecurityServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityServiceException(Throwable cause) {
        super(cause);
    }

    protected SecurityServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

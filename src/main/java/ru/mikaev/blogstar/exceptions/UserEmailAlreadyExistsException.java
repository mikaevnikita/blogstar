package ru.mikaev.blogstar.exceptions;

public class UserEmailAlreadyExistsException extends UsersServiceException {
    public UserEmailAlreadyExistsException() {
        super();
    }

    public UserEmailAlreadyExistsException(String message) {
        super(message);
    }

    public UserEmailAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected UserEmailAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public UserEmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}

package ru.mikaev.blogstar.exceptions;

public class UsersPhotoServiceException extends RuntimeException{
    public UsersPhotoServiceException() {
        super();
    }

    public UsersPhotoServiceException(String message) {
        super(message);
    }

    public UsersPhotoServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsersPhotoServiceException(Throwable cause) {
        super(cause);
    }

    protected UsersPhotoServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

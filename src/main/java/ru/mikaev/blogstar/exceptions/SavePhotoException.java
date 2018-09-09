package ru.mikaev.blogstar.exceptions;

public class SavePhotoException extends UsersPhotoServiceException {
    public SavePhotoException() {
        super();
    }

    public SavePhotoException(String message) {
        super(message);
    }

    public SavePhotoException(String message, Throwable cause) {
        super(message, cause);
    }

    public SavePhotoException(Throwable cause) {
        super(cause);
    }

    protected SavePhotoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

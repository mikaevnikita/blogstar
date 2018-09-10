package ru.mikaev.blogstar.exceptions;

public class ActivationServiceException extends RuntimeException{
    public ActivationServiceException(){
        super();
    }

    public ActivationServiceException(String s) {
        super(s);
    }

    public ActivationServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActivationServiceException(Throwable cause) {
        super(cause);
    }
}

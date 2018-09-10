package ru.mikaev.blogstar.exceptions;

public class ActivationPairNotFoundException extends ActivationServiceException {
    public ActivationPairNotFoundException(){
        super();
    }

    public ActivationPairNotFoundException(String s) {
        super(s);
    }

    public ActivationPairNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActivationPairNotFoundException(Throwable cause) {
        super(cause);
    }
}

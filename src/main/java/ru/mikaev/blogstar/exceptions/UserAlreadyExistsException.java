package ru.mikaev.blogstar.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class UserAlreadyExistsException extends UsersServiceException {
    public UserAlreadyExistsException(){
        super();
    }

    public UserAlreadyExistsException(String s) {
        super(s);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }

}

package edu.tanta.fci.reoil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NewPasswordEqualOldPasswordException extends RuntimeException  {
    public NewPasswordEqualOldPasswordException(String message) {
        super(message);
    }
}

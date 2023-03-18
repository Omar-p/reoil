package edu.tanta.fci.reoil.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailIsAlreadyExist extends RuntimeException {

    public EmailIsAlreadyExist(String email) {
        super(email);
    }
}

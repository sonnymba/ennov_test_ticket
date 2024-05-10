package com.ennov.ticketapi.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class InvalidUrlException extends BadCredentialsException {
    public InvalidUrlException(String message) {
        super(message);
    }
}

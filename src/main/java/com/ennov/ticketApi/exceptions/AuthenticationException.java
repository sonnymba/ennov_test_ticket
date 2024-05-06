package com.ennov.ticketApi.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class AuthenticationException extends BadCredentialsException {
    public AuthenticationException(String message){
        super(message);
    }
}

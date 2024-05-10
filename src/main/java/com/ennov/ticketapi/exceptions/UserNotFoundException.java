package com.ennov.ticketapi.exceptions;

import org.webjars.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String message){
        super(message);
    }
}

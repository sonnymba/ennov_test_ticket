package com.ennov.ticketapi.service;

import com.ennov.ticketapi.entities.Role;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.exceptions.ResourceNotFoundException;

import java.util.List;


public interface MainService  {

    User getCurrentUser() throws ResourceNotFoundException;
    User makeAdmin(Long id);
    User removeAdmin(Long id);
    List<Role> getRoles();

}

package com.ennov.ticketApi.service;

import com.ennov.ticketApi.dto.request.UserRequestDTO;
import com.ennov.ticketApi.entities.User;

import java.util.List;

public interface UserService extends GenericService<User, UserRequestDTO> {

    boolean existWithUsersame(String username);
    boolean existWithEmail(String email);
}

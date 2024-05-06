package com.ennov.ticketApi.service.impl;

import com.ennov.ticketApi.dto.request.UserRequestDTO;
import com.ennov.ticketApi.dto.response.DetailsUserResponseDTO;
import com.ennov.ticketApi.dto.response.EntityResponse;
import com.ennov.ticketApi.dto.response.TicketResponseDTO;
import com.ennov.ticketApi.dto.response.UserResponseDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.service.GenericService;
import com.ennov.ticketApi.service.UserService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public ResponseEntity<EntityResponse> save(UserRequestDTO userRequestDTO) {
        return null;
    }

    @Override
    public List<UserResponseDTO> getAll() {
        return null;
    }

    @Override
    public DetailsUserResponseDTO getOne(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityResponse> update(UserRequestDTO userRequestDTO, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityResponse> delete(Long id) {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<TicketResponseDTO> getAllTicketAssignTicketToUser(Long userId) {
        return null;
    }
}

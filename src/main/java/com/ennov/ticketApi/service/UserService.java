package com.ennov.ticketApi.service;

import com.ennov.ticketApi.dto.request.UserRequestDTO;
import com.ennov.ticketApi.dto.response.DetailsUserResponseDTO;
import com.ennov.ticketApi.dto.response.TicketResponseDTO;
import com.ennov.ticketApi.dto.response.UserResponseDTO;
import com.ennov.ticketApi.entities.User;

import java.util.List;

public interface UserService extends GenericService<User, UserRequestDTO, UserResponseDTO, DetailsUserResponseDTO> {
    List<TicketResponseDTO> getAllTicketAssignTicketToUser(Long userId);
}

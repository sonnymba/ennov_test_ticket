package com.ennov.ticketApi.mapper;

import com.ennov.ticketApi.dto.request.AssignTicketRequestDTO;
import com.ennov.ticketApi.dto.request.TicketRequestDTO;
import com.ennov.ticketApi.dto.request.UserRequestDTO;
import com.ennov.ticketApi.dto.response.DetailsTicketResponseDTO;
import com.ennov.ticketApi.dto.response.DetailsUserResponseDTO;
import com.ennov.ticketApi.dto.response.TicketResponseDTO;
import com.ennov.ticketApi.dto.response.UserResponseDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserRequestDTO, UserResponseDTO, DetailsUserResponseDTO> {
}

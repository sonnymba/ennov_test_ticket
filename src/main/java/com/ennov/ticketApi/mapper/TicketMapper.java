package com.ennov.ticketApi.mapper;

import com.ennov.ticketApi.dto.request.AssignTicketRequestDTO;
import com.ennov.ticketApi.dto.request.TicketRequestDTO;
import com.ennov.ticketApi.dto.response.DetailsTicketResponseDTO;
import com.ennov.ticketApi.dto.response.TicketResponseDTO;
import com.ennov.ticketApi.entities.Ticket;

import com.ennov.ticketApi.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses={Ticket.class, User.class,})
public interface TicketMapper extends GenericMapper<Ticket, TicketRequestDTO, TicketResponseDTO, DetailsTicketResponseDTO> {
}

package com.ennov.ticketApi.service;

import com.ennov.ticketApi.dto.request.TicketRequestDTO;
import com.ennov.ticketApi.dto.response.SmallTicketDTO;
import com.ennov.ticketApi.dto.response.TicketResponseDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import com.google.common.base.Ticker;

import java.util.List;

public interface TicketService extends GenericService<Ticket, TicketRequestDTO> {

    Ticket assignTicketToUser(Long id, Long userId);
    List<Ticket> getAllTicketAssignedToUser(Long userId);
}

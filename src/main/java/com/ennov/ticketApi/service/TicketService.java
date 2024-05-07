package com.ennov.ticketApi.service;

import com.ennov.ticketApi.dto.request.TicketRequestDTO;
import com.ennov.ticketApi.entities.Ticket;

import java.util.List;

public interface TicketService extends GenericService<Ticket, TicketRequestDTO> {

    Ticket assignTicketToUser(Long id, Long userId);

    List<Ticket> listAssignedToUser(Long userId);

    boolean exitbyTitle(String title);
}

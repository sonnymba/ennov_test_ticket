package com.ennov.ticketapi.service;

import com.ennov.ticketapi.dto.request.TicketRequestDTO;
import com.ennov.ticketapi.entities.Ticket;

import java.util.List;

public interface TicketService extends GenericService<Ticket, TicketRequestDTO> {

    Ticket assignTicketToUser(Long id, Long userId);

    List<Ticket> listAssignedToUser(Long userId);

    boolean exitbyTitle(String title);

}

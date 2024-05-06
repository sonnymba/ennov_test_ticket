package com.ennov.ticketApi.service;

import com.ennov.ticketApi.dto.request.TicketRequestDTO;
import com.ennov.ticketApi.dto.response.DetailsTicketResponseDTO;
import com.ennov.ticketApi.dto.response.TicketResponseDTO;
import com.ennov.ticketApi.entities.Ticket;

public interface TicketService extends GenericService<Ticket, TicketRequestDTO, TicketResponseDTO, DetailsTicketResponseDTO> {

    TicketResponseDTO assignTicketToUser(Long id, Long userId);
}

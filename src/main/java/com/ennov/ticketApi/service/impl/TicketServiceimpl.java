package com.ennov.ticketApi.service.impl;

import com.ennov.ticketApi.dto.request.TicketRequestDTO;
import com.ennov.ticketApi.dto.response.DetailsTicketResponseDTO;
import com.ennov.ticketApi.dto.response.EntityResponse;
import com.ennov.ticketApi.dto.response.TicketResponseDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.service.GenericService;
import com.ennov.ticketApi.service.TicketService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class TicketServiceimpl implements TicketService {

    @Override
    public ResponseEntity<EntityResponse> save(TicketRequestDTO ticketRequestDTO) {
        return null;
    }

    @Override
    public List<TicketResponseDTO> getAll() {
        return null;
    }

    @Override
    public DetailsTicketResponseDTO getOne(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityResponse> update(TicketRequestDTO ticketRequestDTO, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityResponse> delete(Long id) {
        return null;
    }

    @Override
    public Ticket findById(Long id) {
        return null;
    }



    @Override
    public TicketResponseDTO assignTicketToUser(Long id, Long userId) {
        return null;
    }
}

package com.ennov.ticketApi.service.impl;

import com.ennov.ticketApi.dao.TicketRepository;
import com.ennov.ticketApi.dto.request.TicketRequestDTO;

import com.ennov.ticketApi.dto.response.SmallTicketDTO;
import com.ennov.ticketApi.dto.response.TicketResponseDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.exceptions.ResourceNotFoundException;
import com.ennov.ticketApi.service.GenericService;
import com.ennov.ticketApi.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
public class TicketServiceimpl implements TicketService {


    @Autowired
    TicketRepository repository;

    @Override
    public Ticket save(TicketRequestDTO ticketRequestDTO) {
        return null;
    }

    @Override
    public List<Ticket> getAll() {

       return  repository.findAll();
    }

    @Override
    public Ticket getOne(Long id) {
        return null;
    }

    @Override
    public Ticket update(Long id, TicketRequestDTO ticketRequestDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {
        Ticket ticket = findById(id);
        repository.delete(ticket);
    }

    @Override
    public Ticket findById(Long id) {

        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Ticket with id " + id + " not found")
        );
    }



    @Override
    public Ticket assignTicketToUser(Long id, Long userId) {
        return null;
    }

    @Override
    public List<Ticket> getAllTicketAssignedToUser(Long userId) {
        return null;
    }
}

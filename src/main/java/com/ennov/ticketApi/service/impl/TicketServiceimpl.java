package com.ennov.ticketApi.service.impl;

import com.ennov.ticketApi.dao.TicketRepository;
import com.ennov.ticketApi.dao.UserRepository;
import com.ennov.ticketApi.dto.request.TicketRequestDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.enums.Status;
import com.ennov.ticketApi.exceptions.APIException;
import com.ennov.ticketApi.exceptions.ResourceNotFoundException;
import com.ennov.ticketApi.service.MainService;
import com.ennov.ticketApi.service.TicketService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class TicketServiceimpl implements TicketService {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    MainService mainService;

    @Override
    public Ticket save(TicketRequestDTO dto) {
        try{
            if(exitbyTitle(dto.getTitle())) throw new APIException("Ticket already exists");
            Ticket ticket = new Ticket(dto);
            ticket.setAssignedTo(mainService.getCurrentUser());
            return ticketRepository.save(ticket);
        }catch (Exception e){
            throw new APIException(e.getMessage());
        }

    }

    @Override
    public List<Ticket> getAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket getOne(Long id) {
        return ticketRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Ticket not found with id "+id)
        );
    }

    @Override
    public Ticket update(Long id, TicketRequestDTO dto) {
        Ticket ticket = getOne(id);
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setStatus(Status.valueOf(dto.getStatus()));
        return ticketRepository.save(ticket);
    }

    @Override
    public void delete(Long id) {
        Ticket ticket = getOne(id);
        ticketRepository.delete(ticket);
    }


    @Override
    public Ticket assignTicketToUser(Long id, Long userId) {
        Ticket ticket = getOne(id);
        User user = mainService.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id"+userId)
        );
        ticket.setAssignedTo(user);
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> listAssignedToUser(Long userId){
        List<Ticket> results = new ArrayList<>();
        User user = mainService.userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id"+userId)
        );
        results =  ticketRepository.findByAssignedTo(user);
        return results;
    }

    @Override
    public boolean exitbyTitle(String title) {
        return ticketRepository.getByTitle(title).isPresent();
    }

}

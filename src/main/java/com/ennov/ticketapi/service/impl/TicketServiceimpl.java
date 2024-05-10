package com.ennov.ticketapi.service.impl;

import com.ennov.ticketapi.dao.TicketRepository;
import com.ennov.ticketapi.dao.UserRepository;
import com.ennov.ticketapi.dto.request.TicketRequestDTO;
import com.ennov.ticketapi.entities.Ticket;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.enums.Status;
import com.ennov.ticketapi.exceptions.APIException;
import com.ennov.ticketapi.exceptions.ResourceNotFoundException;
import com.ennov.ticketapi.service.MainService;
import com.ennov.ticketapi.service.TicketService;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@Slf4j
public class TicketServiceimpl implements TicketService {

    private final TicketRepository ticketRepository;


    private final UserRepository userRepository;

    private final MainService mainService;

    public TicketServiceimpl(TicketRepository ticketRepository, UserRepository userRepository, MainService mainService) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.mainService = mainService;
    }

    @Override
    public Ticket save(TicketRequestDTO dto) {
        if(exitbyTitle(dto.getTitle())) throw new APIException("Ticket with title already exists");
        Ticket ticket = new Ticket(dto);
        ticket.setAssignedTo(mainService.getCurrentUser());
        return  ticketRepository.save(ticket);
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
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("This user not found")
        );
        ticket.setAssignedTo(user);
        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> listAssignedToUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id "+userId)
        );
        return ticketRepository.findByAssignedTo(user);
    }

    @Override
    public boolean exitbyTitle(String title) {
        return ticketRepository.getByTitle(title).isPresent();
    }

}

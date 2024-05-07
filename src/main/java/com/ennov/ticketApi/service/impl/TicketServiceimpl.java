package com.ennov.ticketApi.service.impl;

import com.ennov.ticketApi.dao.TicketRepository;
import com.ennov.ticketApi.dao.UserRepository;
import com.ennov.ticketApi.dto.request.TicketRequestDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.enums.Status;
import com.ennov.ticketApi.exceptions.ResourceNotFoundException;
import com.ennov.ticketApi.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class TicketServiceimpl implements TicketService {

    @Autowired
    TicketRepository repository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Ticket save(TicketRequestDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setStatus(Status.valueOf(dto.getStatus()));
        ticket.setAssignedTo(getCurrentUser());
        return repository.save(ticket);
    }

    @Override
    public List<Ticket> getAll() {
        return repository.findAll();
    }

    @Override
    public Ticket getOne(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Ticket not found with id "+id)
        );
    }

    @Override
    public Ticket update(Long id, TicketRequestDTO dto) {
        Ticket ticket = getOne(id);
        ticket.setTitle(dto.getTitle());
        ticket.setDescription(dto.getDescription());
        ticket.setStatus(Status.valueOf(dto.getStatus()));
        return repository.save(ticket);
    }

    @Override
    public void delete(Long id) {
        Ticket ticket = getOne(id);
        repository.delete(ticket);
    }


    @Override
    public Ticket assignTicketToUser(Long id, Long userId) {
        Ticket ticket = getOne(id);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id"+userId)
        );
        ticket.setAssignedTo(user);
        return repository.save(ticket);
    }

    @Override
    public List<Ticket> listAssignedToUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id"+userId)
        );
        return repository.findByAssignedUser(user);
    }

    @Override
    public boolean exitbyTitle(String title) {
        return repository.getByTitle(title).isPresent();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            throw new ResourceNotFoundException("The current user not found!");
        }
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return  userRepository.findByUsername(userPrincipal.getUsername()).orElseThrow(
                () ->  new ResourceNotFoundException("User not found with username " + userPrincipal.getUsername())
        );
    }
}

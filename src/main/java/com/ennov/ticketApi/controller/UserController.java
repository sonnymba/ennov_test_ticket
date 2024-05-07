package com.ennov.ticketApi.controller;

import com.ennov.ticketApi.dto.request.TicketRequestDTO;
import com.ennov.ticketApi.dto.request.UserRequestDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.enums.Status;
import com.ennov.ticketApi.exceptions.APIException;
import com.ennov.ticketApi.exceptions.ResourceNotFoundException;
import com.ennov.ticketApi.service.TicketService;
import com.ennov.ticketApi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
@RestController
@CrossOrigin("*")
@Slf4j
public class UserController implements CrudController<UserRequestDTO, User> {

    @Autowired
    private UserService service;

    @Autowired
    private TicketService ticketService;

    /**
     * Crud opérations
     */

    @Override
    public ResponseEntity<?> save(UserRequestDTO dto) {
        if (dto.getEmail() == null) throw new APIException("Email is required");
        if (dto.getUsername() == null) throw new APIException("Username is required");
        if (dto.getPassword() == null) throw new APIException("Password is required");
        return ResponseEntity.ok().body(service.save(dto));
    }

    @Override
    public List<User> list() {
        return service.getAll();
    }

    @Override
    public ResponseEntity<?> update(Long id, UserRequestDTO dto) {
        return ResponseEntity.ok().body(service.update(id, dto));
    }

    @GetMapping("/{id}/ticket")
    public List<Ticket> listAssignedByUser(@PathVariable(name = "id") Long userId) {
        if(userId == null) throw new APIException("userId is required");
        return ticketService.getAllTicketAssignedToUser(userId);
    }




}

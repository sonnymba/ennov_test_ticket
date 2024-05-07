package com.ennov.ticketApi.controller;

import com.ennov.ticketApi.dto.request.TicketRequestDTO;

import com.ennov.ticketApi.dto.request.UserRequestDTO;
import com.ennov.ticketApi.dto.response.LiteTicketDTO;
import com.ennov.ticketApi.dto.response.LiteUserDTO;
import com.ennov.ticketApi.dto.response.TicketResponseDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.enums.Status;
import com.ennov.ticketApi.exceptions.APIException;
import com.ennov.ticketApi.exceptions.ResourceNotFoundException;
import com.ennov.ticketApi.service.TicketService;
import com.ennov.ticketApi.utils.MyUtils;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/tickets")
@RestController
@CrossOrigin("*")
@Slf4j
public class TicketController{

    @Autowired
    private TicketService service;

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> list() {
        List<Ticket> tickets = service.getAll();
        List<TicketResponseDTO> dtos = tickets.stream().map(TicketResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getOne(@PathVariable(name = "id") Long id) {
        if(id == null) throw new APIException("id is required");
        Ticket ticket = service.getOne(id);
        TicketResponseDTO ticketDTO = new TicketResponseDTO(ticket);
        return ResponseEntity.ok(ticketDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TicketResponseDTO> save(@RequestBody @Valid TicketRequestDTO dto) {
        if(!MyUtils.isValidEmailAddress(dto.getTitle())) throw new APIException("title is already exist.");
        Ticket ticket = service.save(dto);
        TicketResponseDTO ticketDTO = new TicketResponseDTO(ticket);
        URI uri = URI.create("/tickets/" + ticketDTO.getId());
        return ResponseEntity.created(uri).body(ticketDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> update(@PathVariable(name = "id")  Long id, @RequestBody @Valid TicketRequestDTO dto) {
        if(id == null) throw new APIException("id is required");
        dto.setId(id);
        Ticket ticket = service.update(id, dto);
        TicketResponseDTO ticketDTO = new TicketResponseDTO(ticket);
        return ResponseEntity.ok(ticketDTO);
    }


    @PutMapping("/{id}/assign/{userId}")
    public ResponseEntity<TicketResponseDTO> update(@PathVariable(name = "id")  Long id, @PathVariable(name = "userId") Long userId) {
        if(id == null) throw new APIException("id is required");
        if(userId == null) throw new APIException("userId is required");

        Ticket ticket = service.assignTicketToUser(id, userId);
         TicketResponseDTO dto = new TicketResponseDTO(ticket);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(id == null) throw new APIException("id is required");
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

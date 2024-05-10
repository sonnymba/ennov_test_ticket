package com.ennov.ticketapi.controller;

import com.ennov.ticketapi.dto.request.TicketRequestDTO;

import com.ennov.ticketapi.dto.response.TicketResponseDTO;
import com.ennov.ticketapi.entities.Ticket;

import com.ennov.ticketapi.enums.Status;
import com.ennov.ticketapi.exceptions.APIException;
import com.ennov.ticketapi.service.TicketService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/tickets")
@RestController
@CrossOrigin("*")
@Slf4j
public class TicketController{

    private static final String TICKET_ENDPOINT = "/tickets/";

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }


    /**
     * Récupérer tous les tickets.
     */
    @GetMapping("/admin")
    public ResponseEntity<List<TicketResponseDTO>> list() {
        List<Ticket> tickets = service.getAll();
        List<TicketResponseDTO> dtos = tickets.stream().map(TicketResponseDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Récupérer un ticket par son ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> getOne(@PathVariable(name = "id") Long id) {
        if(id == null) throw new APIException("id is required");
        Ticket ticket = service.getOne(id);
        TicketResponseDTO ticketDTO = new TicketResponseDTO(ticket);
        return ResponseEntity.ok(ticketDTO);
    }

    /**
     * Créer un nouveau ticket.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TicketResponseDTO> save(@RequestBody @Valid TicketRequestDTO dto) {
        if(service.exitbyTitle(dto.getTitle())) throw new APIException("title is already exist.");
        Ticket ticket = service.save(dto);
        TicketResponseDTO ticketDTO = new TicketResponseDTO(ticket);
        URI uri = URI.create(TICKET_ENDPOINT + ticketDTO.getId());
        return ResponseEntity.created(uri).body(ticketDTO);
    }

    /**
     * Mettre à jour un ticket existant.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> update(@PathVariable(name = "id")  Long id, @RequestBody @Valid TicketRequestDTO dto) {
        if(id == null) throw new APIException("id is required");
        dto.setId(id);
        Ticket ticket = service.update(id, dto);
        TicketResponseDTO ticketDTO = new TicketResponseDTO(ticket);
        return ResponseEntity.ok(ticketDTO);
    }


    /**
     * Assigner un ticket à un utilisateur.
     */
    @PutMapping("/{id}/assign/{userId}")
    public ResponseEntity<TicketResponseDTO> assignTicketToUser(@PathVariable(name = "id")  Long id, @PathVariable(name = "userId") Long userId) {
        if(id == null) throw new APIException("id is required");
        if(userId == null) throw new APIException("userId is required");

        Ticket ticket = service.assignTicketToUser(id, userId);
         TicketResponseDTO dto = new TicketResponseDTO(ticket);
        return ResponseEntity.ok(dto);
    }

    /**
     * Supprimer un ticket par son ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if(id == null) throw new APIException("id is required");
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

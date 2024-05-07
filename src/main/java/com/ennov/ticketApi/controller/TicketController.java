package com.ennov.ticketApi.controller;

import com.ennov.ticketApi.dto.request.TicketRequestDTO;

import com.ennov.ticketApi.dto.response.SmallTicketDTO;
import com.ennov.ticketApi.dto.response.TicketResponseDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.enums.Status;
import com.ennov.ticketApi.exceptions.APIException;
import com.ennov.ticketApi.exceptions.ResourceNotFoundException;
import com.ennov.ticketApi.service.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/tickets")
@RestController
@CrossOrigin("*")
@Slf4j
public class TicketController implements CrudController<TicketRequestDTO, Ticket> {

    @Autowired
    private TicketService service;

    /**
     * Crud opérations
     */

    @Override
    public ResponseEntity<?> save(TicketRequestDTO dto) {
        if (dto.getTitle() == null) throw new APIException("Title is required");
        if (dto.getDescription() == null) throw new APIException("Description is required");
        if (dto.getStatus() == null) throw new APIException("Status is required");
        if (Status.valueOf(dto.getStatus()).getValue().isEmpty()) throw new ResourceNotFoundException("Status not found");
        return ResponseEntity.ok().body(service.save(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable(name = "id") Long id) {
        if (id == null) throw new APIException("Id is required");
        return ResponseEntity.ok().body(service.getOne(id));
    }

    @Override
    public List<Ticket> list() {
        return service.getAll();
    }


    @Override
    public ResponseEntity<?> update(Long id, TicketRequestDTO dto) {
        if (dto.getTitle() == null) throw new APIException("Title is required");
        if (dto.getDescription() == null) throw new APIException("Description is required");
        if (dto.getStatus() == null) throw new APIException("Status is required");
        if (Status.valueOf(dto.getStatus()).getValue().isEmpty()) throw new ResourceNotFoundException("Status not found");
        return ResponseEntity.ok().body(service.update(id, dto));
    }



    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        if (id == null) throw new APIException("Id is required");
       service.delete(id);
    }

    @PutMapping("/{id}/assign/{userId}")

    public ResponseEntity<?> assign(@PathVariable(name = "id") Long id, @PathVariable(name = "userId") Long userId) {
        if (id == null) throw new APIException("id is required");
        if (userId == null) throw new APIException("userId is required");
        Ticket ticket =  service.assignTicketToUser(id, userId);

        return ResponseEntity.ok().body(ticket);
    }

}

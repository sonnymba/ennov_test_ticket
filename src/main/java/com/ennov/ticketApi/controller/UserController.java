package com.ennov.ticketApi.controller;

import com.ennov.ticketApi.dto.request.UserRequestDTO;
import com.ennov.ticketApi.dto.response.LiteTicketDTO;
import com.ennov.ticketApi.dto.response.LiteUserDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.exceptions.APIException;
import com.ennov.ticketApi.service.TicketService;
import com.ennov.ticketApi.service.UserService;
import com.ennov.ticketApi.utils.MyUtils;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/users")
@RestController
@CrossOrigin("*")
@Slf4j
public class UserController {

    private static final String USER_ENDPOINT = "/tickets/";

    @Autowired
    private UserService service;

    @Autowired
    private TicketService ticketService;

    /**
     * Récupérer tous les utilisateurs
     */
    @GetMapping
    public ResponseEntity<List<LiteUserDTO>> list() {
        List<User> users = service.getAll();
        List<LiteUserDTO> userDTOs = users.stream().map(LiteUserDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(userDTOs);
    }

    /**
     * Récupérer les tickets assignés à l'utilisateur
     */
    @GetMapping("/{id}/ticket")
    public ResponseEntity<List<LiteTicketDTO>> listAssignedTicketToUser(@PathVariable(name = "id") Long id) {
        if(id == null) throw new APIException("Id is required");
        List<Ticket> tickets = ticketService.listAssignedToUser(id);
        List<LiteTicketDTO> dtos = tickets.stream().map(LiteTicketDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Créer un utilisateur
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LiteUserDTO> save(@RequestBody @Valid UserRequestDTO dto) {
        if(!MyUtils.isValidEmailAddress(dto.getEmail())) throw new APIException("Email invalid");
        User persistedUser = service.save(dto);
        LiteUserDTO userDTO = new LiteUserDTO(persistedUser);
        URI uri = URI.create(USER_ENDPOINT + userDTO.getId());
        return ResponseEntity.created(uri).body(userDTO);
    }

    /**
     * Modifier un utilisateur
     */
    @PutMapping("/{id}")
    public ResponseEntity<LiteUserDTO> update(@PathVariable Long id, @RequestBody @Valid UserRequestDTO dto) {
        if(id == null) throw new APIException("Id is required");
        if(!MyUtils.isValidEmailAddress(dto.getEmail())) throw new APIException("Email invalid");
        dto.setId(id);
        User persistedUser = service.update(id, dto);
        LiteUserDTO userDTO = new LiteUserDTO(persistedUser);
        return ResponseEntity.ok(userDTO);
    }
}

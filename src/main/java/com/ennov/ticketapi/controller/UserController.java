package com.ennov.ticketapi.controller;

import com.ennov.ticketapi.dto.request.UserRequestDTO;
import com.ennov.ticketapi.dto.response.LiteTicketDTO;
import com.ennov.ticketapi.dto.response.LiteUserDTO;
import com.ennov.ticketapi.entities.Ticket;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.exceptions.APIException;
import com.ennov.ticketapi.service.TicketService;
import com.ennov.ticketapi.service.UserService;
import com.ennov.ticketapi.utils.MyUtils;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

    private final UserService service;

    private final TicketService ticketService;

    public UserController(UserService service, TicketService ticketService) {
        this.service = service;
        this.ticketService = ticketService;
    }

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
        if(id == null) throw new APIException("id is required");
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
        if(!MyUtils.isValidEmailAddress(dto.getEmail())) throw new IllegalArgumentException("Email invalid");
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
        if(id == null) throw new APIException("id is required");
        if(!MyUtils.isValidEmailAddress(dto.getEmail())) throw new IllegalArgumentException("Email invalid");
        dto.setId(id);
        User persistedUser = service.update(id, dto);
        LiteUserDTO userDTO = new LiteUserDTO(persistedUser);
        return ResponseEntity.ok(userDTO);
    }
}

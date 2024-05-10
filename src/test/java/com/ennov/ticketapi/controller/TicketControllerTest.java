package com.ennov.ticketapi.controller;

import com.ennov.ticketapi.dto.request.TicketRequestDTO;
import com.ennov.ticketapi.dto.response.TicketResponseDTO;
import com.ennov.ticketapi.entities.Ticket;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.enums.Status;
import com.ennov.ticketapi.exceptions.APIException;
import com.ennov.ticketapi.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ticketController = new TicketController(ticketService);
    }

    @Test
    void list() {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketService.getAll()).thenReturn(tickets);

        ResponseEntity<List<TicketResponseDTO>> response = ticketController.list();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
    }

    @Test
    void testGetOne() {
        Long id = 1L;
        Ticket ticket = new Ticket();
        when(ticketService.getOne(id)).thenReturn(ticket);

        ResponseEntity<TicketResponseDTO> response = ticketController.getOne(id);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(ticket.getId());
    }

    @Test
     void testSave() {
        TicketRequestDTO dto = new TicketRequestDTO();
        dto.setTitle("Test Ticket");
        dto.setStatus(Status.EN_COURS.name());
        Ticket savedTicket = new Ticket();
        savedTicket.setId(1L);
        savedTicket.setTitle("Test Ticket");
        savedTicket.setStatus(Status.EN_COURS);
        when(ticketService.save(dto)).thenReturn(savedTicket);

        ResponseEntity<TicketResponseDTO> response = ticketController.save(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(savedTicket.getId());
    }

    @Test
    void getOne_throwsAPIException() {
        Long id = null;

        APIException exception = assertThrows(APIException.class, () -> ticketController.getOne(id));

        assertThat(exception.getMessage()).isEqualTo("id is required");
    }


    @Test
    void save_throwsAPIException() {
        TicketRequestDTO dto = new TicketRequestDTO();
        dto.setTitle("existing title");
        when(ticketService.exitbyTitle(dto.getTitle())).thenReturn(true);

        APIException exception = assertThrows(APIException.class, () -> ticketController.save(dto));
        assertThat(exception.getMessage()).isEqualTo("title is already exist.");
    }

    @Test
     void testUpdate() {
        Long id = 1L;
        TicketRequestDTO dto = new TicketRequestDTO();
        dto.setId(id);
        dto.setTitle("Updated Ticket");
        dto.setStatus(Status.TERMINE.name());
        Ticket updatedTicket = new Ticket();
        updatedTicket.setId(id);
        updatedTicket.setTitle("Updated Ticket");
        updatedTicket.setStatus(Status.TERMINE);
        when(ticketService.update(id, dto)).thenReturn(updatedTicket);

        ResponseEntity<TicketResponseDTO> response = ticketController.update(id, dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(response.getBody().getId());
    }

    @Test
     void testAssignTicketToUser() {
        Long id = 1L;
        Long userId = 2L;
        Ticket assignedTicket = new Ticket();
        assignedTicket.setId(id);

        User user = new User();
        user.setId(userId);
        assignedTicket.setAssignedTo(user);

        when(ticketService.assignTicketToUser(id, userId)).thenReturn(assignedTicket);

        ResponseEntity<TicketResponseDTO> response = ticketController.assignTicketToUser(id, userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(Objects.requireNonNull(response.getBody()).getId()).isEqualTo(response.getBody().getId());
        assertThat(assignedTicket.getAssignedTo().getId()).isEqualTo(response.getBody().getAssignedTo().getId());
        verify(ticketService, times(1)).assignTicketToUser(id, userId);
    }
    @Test
     void testDelete() {
        Long id = 1L;
        doNothing().when(ticketService).delete(id);

        ResponseEntity<Void> response = ticketController.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void delete_throwsAPIException() {
        Long id = null;

        APIException exception = assertThrows(APIException.class, () -> ticketController.delete(id));

        assertThat(exception.getMessage()).isEqualTo("id is required");
    }

    @Test
     void testSave_TitleAlreadyExists() {
        TicketRequestDTO dto = new TicketRequestDTO();
        dto.setTitle("Test Ticket");
        when(ticketService.exitbyTitle(dto.getTitle())).thenReturn(true);

        assertThrows(APIException.class, () -> ticketController.save(dto));
    }








    @Test
    void update_throwsAPIException() {
        Long id = null;
        TicketRequestDTO dto = new TicketRequestDTO();

        APIException exception = assertThrows(APIException.class, () -> ticketController.update(id, dto));

        assertThat(exception.getMessage()).isEqualTo("id is required");
    }

    @Test
     void testAssignTicketToUser_WithNullId() {
        Long id = null;
        Long userId = 123L;
        APIException exception = assertThrows(APIException.class, () -> ticketController.assignTicketToUser(id, userId));
        assertThat(exception.getMessage()).isEqualTo("id is required");
    }

    @Test
     void testAssignTicketToUser_WithNullUserId() {
        Long id = 12L;
        Long userId = null;
        APIException exception = assertThrows(APIException.class, () -> ticketController.assignTicketToUser(id, userId));
        assertThat(exception.getMessage()).isEqualTo("userId is required");
    }

}
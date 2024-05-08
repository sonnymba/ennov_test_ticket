package com.ennov.ticketapi.controller;

import com.ennov.ticketapi.dto.request.TicketRequestDTO;

import com.ennov.ticketapi.dto.response.TicketResponseDTO;
import com.ennov.ticketapi.entities.Ticket;
import com.ennov.ticketapi.service.TicketService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {
    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    public void setup() {}

    @Test
    public void testList(){
        // Given
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketService.getAll()).thenReturn(tickets);

        // When
        ResponseEntity<List<TicketResponseDTO>> response = ticketController.list();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testGetOne() {
        // Given
        Long id = 1L;
        Ticket ticket = new Ticket();
        when(ticketService.getOne(id)).thenReturn(ticket);

        // When
        ResponseEntity<TicketResponseDTO> response = ticketController.getOne(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testSave() {
        // Given
        TicketRequestDTO dto = new TicketRequestDTO();
        dto.setTitle("Test Title");
        when(ticketService.save(dto)).thenReturn(new Ticket());

        // When
        ResponseEntity<TicketResponseDTO> response = ticketController.save(dto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testSave_TitleAlreadyExists() {
        // Given
        TicketRequestDTO dto = new TicketRequestDTO();
        dto.setTitle("Test Title");

        // When
        ticketController.save(dto);
    }

    @Test
    public void testUpdate() {
        // Given
        Long id = 1L;
        TicketRequestDTO dto = new TicketRequestDTO();
        dto.setId(id);
        when(ticketService.update(id, dto)).thenReturn(new Ticket());

        // When
        ResponseEntity<TicketResponseDTO> response = ticketController.update(id, dto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testAssignTicketToUser() {
        // Given
        Long id = 1L;
        Long userId = 2L;
        when(ticketService.assignTicketToUser(id, userId)).thenReturn(new Ticket());

        // When
        ResponseEntity<TicketResponseDTO> response = ticketController.assignTicketToUser(id, userId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testDelete() {
        // Given
        Long id = 1L;

        // When
        ResponseEntity<Void> response = ticketController.delete(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
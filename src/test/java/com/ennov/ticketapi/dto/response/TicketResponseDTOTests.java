package com.ennov.ticketapi.dto.response;

import com.ennov.ticketapi.entities.Ticket;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

 class TicketResponseDTOTests {

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testValidTicketResponseDTO() {
        Status status = Status.EN_COURS;
        User user = new User("Test User", "test@example.com", new HashSet<>());

        Ticket ticket = new Ticket("Test Ticket", "test@example.com", status, user);

        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO(ticket);

        Assertions.assertEquals(ticket.getId(), ticketResponseDTO.getId());
        Assertions.assertEquals(ticket.getTitle(), ticketResponseDTO.getTitle());
        Assertions.assertEquals(ticket.getDescription(), ticketResponseDTO.getDescription());
        Assertions.assertEquals(ticket.getStatus(), ticketResponseDTO.getStatus());
        Assertions.assertEquals(ticket.getAssignedTo().getUsername(), ticketResponseDTO.getAssignedTo().getUsername());
    }

    @Test
     void testNoArgsConstructor() {
        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO();

        Assertions.assertNull(ticketResponseDTO.getId());
        Assertions.assertNull(ticketResponseDTO.getTitle());
        Assertions.assertNull(ticketResponseDTO.getDescription());
        Assertions.assertNull(ticketResponseDTO.getStatus());
        Assertions.assertNull(ticketResponseDTO.getAssignedTo());
    }

    @Test
     void testAllArgsConstructor() {
        Status status = Status.EN_COURS;
        User user = new User("Test User", "test@example.com", new HashSet<>());

        Ticket ticket = new Ticket("Test Ticket", "test@example.com", status, user);

        TicketResponseDTO ticketResponseDTO = new TicketResponseDTO(ticket.getId(), ticket.getTitle(), ticket.getDescription(), ticket.getStatus(), new LiteUserDTO(ticket.getAssignedTo()));

        Assertions.assertEquals(ticket.getId(), ticketResponseDTO.getId());
        Assertions.assertEquals(ticket.getTitle(), ticketResponseDTO.getTitle());
        Assertions.assertEquals(ticket.getDescription(), ticketResponseDTO.getDescription());
        Assertions.assertEquals(ticket.getStatus(), ticketResponseDTO.getStatus());
        Assertions.assertEquals(ticket.getAssignedTo().getUsername(), ticketResponseDTO.getAssignedTo().getUsername());
    }
}
package com.ennov.ticketapi.dto.response;

import com.ennov.ticketapi.entities.Ticket;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.stream.Collectors;

 class UserWithTicketDTOTests {

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testValidUserWithTicketDTO() {
        Status status = Status.EN_COURS;
        User user = new User("Test User", "test1@example.com", new ArrayList<Ticket>());
        Ticket ticket1 = new Ticket("Test Ticket 1", "test@example.com", status, user);
        Ticket ticket2 = new Ticket("Test Ticket 2", "test@example.com", status, user);
        user.getTickets().add(ticket1);
        user.getTickets().add(ticket2);

        assertThat(user.getTickets()).hasSize(2);
        UserWithTicketDTO userWithTicketDTO = new UserWithTicketDTO(user);

        Assertions.assertEquals(user.getId(), userWithTicketDTO.getId());
        Assertions.assertEquals(user.getUsername(), userWithTicketDTO.getUsername());
        Assertions.assertEquals(user.getEmail(), userWithTicketDTO.getEmail());
        Assertions.assertEquals(2, userWithTicketDTO.getTickets().size());
        Assertions.assertEquals(ticket1.getId(), userWithTicketDTO.getTickets().get(0).getId());
        Assertions.assertEquals(ticket2.getId(), userWithTicketDTO.getTickets().get(1).getId());
    }

    @Test
     void testNoArgsConstructor() {
        UserWithTicketDTO userWithTicketDTO = new UserWithTicketDTO();

        Assertions.assertNull(userWithTicketDTO.getId());
        Assertions.assertNull(userWithTicketDTO.getUsername());
        Assertions.assertNull(userWithTicketDTO.getEmail());
        Assertions.assertNull(userWithTicketDTO.getTickets());
    }

    @Test
     void testAllArgsConstructor() {
        Status status = Status.EN_COURS;
        User user = new User("Test User", "test1@example.com", new ArrayList<Ticket>());
        Ticket ticket1 = new Ticket("Test Ticket 1", "test@example.com", status, user);
        Ticket ticket2 = new Ticket("Test Ticket 2", "test@example.com", status, user);
        user.getTickets().add(ticket1);
        user.getTickets().add(ticket2);

        UserWithTicketDTO userWithTicketDTO = new UserWithTicketDTO(user.getId(), user.getUsername(), user.getEmail(), user.getTickets().stream().map(LiteTicketDTO::new).collect(Collectors.toList()));

        Assertions.assertEquals(user.getId(), userWithTicketDTO.getId());
        Assertions.assertEquals(user.getUsername(), userWithTicketDTO.getUsername());
        Assertions.assertEquals(user.getEmail(), userWithTicketDTO.getEmail());
        Assertions.assertEquals(2, userWithTicketDTO.getTickets().size());
        Assertions.assertEquals(ticket1.getId(), userWithTicketDTO.getTickets().get(0).getId());
        Assertions.assertEquals(ticket2.getId(), userWithTicketDTO.getTickets().get(1).getId());
    }
 }
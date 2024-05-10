package com.ennov.ticketapi.dto.response;

import com.ennov.ticketapi.entities.Ticket;
import com.ennov.ticketapi.enums.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

 class LiteTicketDTOTests {

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testValidLiteTicketDTO() {
        Status status = Status.EN_COURS;

        Ticket ticket = new Ticket("Test Ticket", "test@example.com", status);

        LiteTicketDTO liteTicketDTO = new LiteTicketDTO(ticket);

        Assertions.assertEquals(ticket.getId(), liteTicketDTO.getId());
        Assertions.assertEquals(ticket.getTitle(), liteTicketDTO.getTitle());
        Assertions.assertEquals(ticket.getDescription(), liteTicketDTO.getDescription());
        Assertions.assertEquals(ticket.getStatus(), liteTicketDTO.getStatus());
    }

     @Test
      void testNoArgsConstructor() {
         LiteTicketDTO liteTicketDTO = new LiteTicketDTO();

         Assertions.assertNull(liteTicketDTO.getId());
         Assertions.assertNull(liteTicketDTO.getTitle());
         Assertions.assertNull(liteTicketDTO.getDescription());
         Assertions.assertNull(liteTicketDTO.getStatus());
     }
     @Test
      void testAllArgsConstructor() {
         Status status = Status.EN_COURS;

         Ticket ticket = new Ticket("Test Ticket", "test@example.com", status);

         LiteTicketDTO liteTicketDTO = new LiteTicketDTO(ticket.getId(), ticket.getTitle(), ticket.getDescription(), ticket.getStatus());

         Assertions.assertEquals(ticket.getId(), liteTicketDTO.getId());
         Assertions.assertEquals(ticket.getTitle(), liteTicketDTO.getTitle());
         Assertions.assertEquals(ticket.getDescription(), liteTicketDTO.getDescription());
         Assertions.assertEquals(ticket.getStatus(), liteTicketDTO.getStatus());
     }
 }
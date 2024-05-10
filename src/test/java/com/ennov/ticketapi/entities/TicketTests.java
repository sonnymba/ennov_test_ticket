package com.ennov.ticketapi.entities;

import com.ennov.ticketapi.dto.request.TicketRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

 class TicketTests {

    public static final String TEST_TICKET = "Test Ticket";
    public static final String DESCRIPTION = "This is a test ticket.";
    public static final String STATUS = "EN_COURS";
    private Validator validator;

    @BeforeEach
     void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
     void testValidTicket() {
        TicketRequestDTO dto = new TicketRequestDTO(TEST_TICKET, DESCRIPTION, STATUS);
        Ticket ticket = new Ticket(dto);
        assertThat(ticket).isNotNull();
        assertThat(ticket.getTitle()).isEqualTo(dto.getTitle());
        assertThat(ticket.getDescription()).isEqualTo(dto.getDescription());
        assertThat(ticket.getStatus().name()).isEqualTo(dto.getStatus());
    }

    @Test
     void testInvalidTitle() {
        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO(null, DESCRIPTION, STATUS);
        Ticket ticket = new Ticket(ticketRequestDTO);
        Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);
        assertThat(violations).isNotNull();
    }

    @Test
     void testInvalidDescription() {
        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO(TEST_TICKET, null, STATUS);
        Ticket ticket = new Ticket(ticketRequestDTO);
        Set<ConstraintViolation<Ticket>> violations = validator.validate(ticket);
        assertThat(violations).isNotNull();
    }


    @Test
     void testAssignedTo() {
        TicketRequestDTO dto = new TicketRequestDTO(TEST_TICKET, DESCRIPTION, STATUS);
        Ticket ticket = new Ticket(dto);
        User user = new User();
        user.setUsername("TEST");
        ticket.setAssignedTo(user);
        User user2 = ticket.getAssignedTo();
        assertThat(user2).isNotNull();
        assertThat(user2.getUsername()).isEqualTo(user.getUsername());
    }
}
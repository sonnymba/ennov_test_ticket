package com.ennov.ticketapi.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

 class TicketRequestDTOTests {

    private Validator validator;

    @BeforeEach
     void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
     void testValidTicketRequestDTO() {
        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO("Test Ticket", "This is a test ticket.", "OPEN");

        Set<ConstraintViolation<TicketRequestDTO>> violations = validator.validate(ticketRequestDTO);
        Assertions.assertEquals(0, violations.size());
    }

    @Test
    void testInvalidTitle() {
        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO("", "This is a test ticket.", "EN_COURS");

        Set<ConstraintViolation<TicketRequestDTO>> violations = validator.validate(ticketRequestDTO);
        assertThat(violations).isNotNull();
    }

    @Test
     void testInvalidDescription() {
        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO("Test Ticket", "", "EN_COURS");

        Set<ConstraintViolation<TicketRequestDTO>> violations = validator.validate(ticketRequestDTO);

        assertThat(violations).isNotNull();
    }

    @Test
     void testInvalidStatus() {
        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO("Test Ticket", "This is a test ticket.", null);

        Set<ConstraintViolation<TicketRequestDTO>> violations = validator.validate(ticketRequestDTO);

        assertThat(violations).isNotNull();
    }
}
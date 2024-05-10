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

class AssignTicketRequestDTOTests {

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
     void testValidAssignTicketRequestDTO() {
        AssignTicketRequestDTO assignTicketRequestDTO = new AssignTicketRequestDTO(1L, 2L);

        Assertions.assertEquals(1L, assignTicketRequestDTO.getId());
        Assertions.assertEquals(2L, assignTicketRequestDTO.getUserId());
    }

    @Test
     void testInvalidId() {
        AssignTicketRequestDTO dto = new AssignTicketRequestDTO(null,  2L);

        Set<ConstraintViolation<AssignTicketRequestDTO>> violations = validator.validate(dto);
        assertThat(violations).isNotNull();

    }

    @Test
    void testInvalidUserId() {
        AssignTicketRequestDTO dto = new AssignTicketRequestDTO(1L,  null);

        Set<ConstraintViolation<AssignTicketRequestDTO>> violations = validator.validate(dto);
        assertThat(violations).isNotNull();
    }
}
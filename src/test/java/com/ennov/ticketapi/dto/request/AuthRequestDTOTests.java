package com.ennov.ticketapi.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

 class AuthRequestDTOTests {

    private Validator validator;

    @BeforeEach
     void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
     void testValidAuthRequestDTO() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO("testUser", "testPassword");

        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(authRequestDTO);

        Assertions.assertEquals(0, violations.size());
    }

    @Test
     void testInvalidUsername() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO("", "testPassword");

        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(authRequestDTO);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Username est obligatoire", violations.iterator().next().getMessage());
    }

    @Test
     void testInvalidPassword() {
        AuthRequestDTO authRequestDTO = new AuthRequestDTO("testUser", "");

        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(authRequestDTO);

        Assertions.assertEquals(1, violations.size());
        Assertions.assertEquals("Le mot de passe est obligatoire", violations.iterator().next().getMessage());
    }
}
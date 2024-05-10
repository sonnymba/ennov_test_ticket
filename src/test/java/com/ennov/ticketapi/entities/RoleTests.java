package com.ennov.ticketapi.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTests {

    private Validator validator;

    @BeforeEach
     void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
     void testValidRole() {
        Role role = new Role("Test Role");

        Set<ConstraintViolation<Role>> violations = validator.validate(role);

        Assertions.assertEquals(0, violations.size());
    }

    @Test
     void testInvalidName() {
        Role role = new Role(null);

        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        assertThat(violations).isNotNull();
    }


}
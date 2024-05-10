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

 class PrivilegeTests {

    private Validator validator;

    @BeforeEach
     void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
     void testValidPrivilege() {
        Privilege privilege = new Privilege("Test Privilege");

        Set<ConstraintViolation<Privilege>> violations = validator.validate(privilege);

        Assertions.assertEquals(0, violations.size());
    }

    @Test
     void testInvalidName() {
        Privilege privilege = new Privilege(null);
        assertThat(privilege.getName()).isNull();
    }

}
package com.ennov.ticketapi.dto;

import com.ennov.ticketapi.entities.Role;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


 class RoleDTOTests {

    public static final String TEST_ROLE = "TEST_ROLE";
    public static final long ID = 1L;
    private RoleDTO roleDTO;

    @Mock
    private Role role;

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);
        role = new Role(TEST_ROLE);
        role.setId(ID);
        roleDTO = new RoleDTO(role);
    }

    @Test
     void testGettersAndSetters() {
        Assertions.assertEquals(1L, roleDTO.getId());
        Assertions.assertEquals("TEST_ROLE", roleDTO.getName());

        roleDTO.setId(2L);
        roleDTO.setName("UPDATED_ROLE");

        Assertions.assertEquals(2L, roleDTO.getId());
        Assertions.assertEquals("UPDATED_ROLE", roleDTO.getName());
    }

    @Test
     void testConstructor() {
        RoleDTO roleDTO = new RoleDTO(role);

        Assertions.assertEquals(1L, roleDTO.getId());
        Assertions.assertEquals("TEST_ROLE", roleDTO.getName());
    }

    @Test
     void testToString() {
        String toStringResult = roleDTO.toString();

        Assertions.assertTrue(toStringResult.contains("RoleDTO"));
        Assertions.assertTrue(toStringResult.contains("id=1"));
        Assertions.assertTrue(toStringResult.contains("name=TEST_ROLE"));
    }

    @Test
     void testRoleDTOFromRole() {
        RoleDTO roleDTO = new RoleDTO(role);

        Assertions.assertEquals(1L, roleDTO.getId());
        Assertions.assertEquals("TEST_ROLE", roleDTO.getName());
    }
}
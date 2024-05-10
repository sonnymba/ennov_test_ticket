package com.ennov.ticketapi.dto.response;

import com.ennov.ticketapi.entities.Role;
import com.ennov.ticketapi.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

 class LiteUserDTOTests {

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testValidLiteUserDTO() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Test Role"));

        User user = new User("Test User", "test@example.com", roles);

        LiteUserDTO liteUserDTO = new LiteUserDTO(user);

        Assertions.assertEquals(user.getId(), liteUserDTO.getId());
        Assertions.assertEquals(user.getUsername(), liteUserDTO.getUsername());
        Assertions.assertEquals(user.getEmail(), liteUserDTO.getEmail());
        Assertions.assertEquals(user.getRoles(), liteUserDTO.getRoles());
    }

    @Test
     void testNoArgsConstructor() {
        LiteUserDTO liteUserDTO = new LiteUserDTO();

        Assertions.assertNull(liteUserDTO.getId());
        Assertions.assertNull(liteUserDTO.getUsername());
        Assertions.assertNull(liteUserDTO.getEmail());
        Assertions.assertNull(liteUserDTO.getRoles());
    }

    @Test
     void testAllArgsConstructor() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("Test Role"));

        User user = new User("Test User", "test@example.com", roles);

        LiteUserDTO liteUserDTO = new LiteUserDTO(user.getId(), user.getUsername(), user.getEmail(), user.getRoles());

        Assertions.assertEquals(user.getId(), liteUserDTO.getId());
        Assertions.assertEquals(user.getUsername(), liteUserDTO.getUsername());
        Assertions.assertEquals(user.getEmail(), liteUserDTO.getEmail());
        Assertions.assertEquals(user.getRoles(), liteUserDTO.getRoles());
    }
}
package com.ennov.ticketapi.dto.request;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;

 class UserRequestDTOTests {

    private UserRequestDTO userRequestDTO;

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("testUser");
        userRequestDTO.setEmail("testUser@test.com");
        userRequestDTO.setPassword("testPassword");
    }

    @Test
     void testGettersAndSetters() {
        Assertions.assertEquals("testUser", userRequestDTO.getUsername());
        Assertions.assertEquals("testUser@test.com", userRequestDTO.getEmail());
        Assertions.assertEquals("testPassword", userRequestDTO.getPassword());

        userRequestDTO.setUsername("updatedUser");
        userRequestDTO.setEmail("updatedUser@test.com");
        userRequestDTO.setPassword("updatedPassword");

        Assertions.assertEquals("updatedUser", userRequestDTO.getUsername());
        Assertions.assertEquals("updatedUser@test.com", userRequestDTO.getEmail());
        Assertions.assertEquals("updatedPassword", userRequestDTO.getPassword());
    }

    @Test
     void testAllArgsConstructor() {
        UserRequestDTO userRequestDTO = new UserRequestDTO("updatedUser", "updatedUser@test.com", "updatedPassword");
        assertThat(userRequestDTO).isNotNull();
        Assertions.assertEquals("updatedUser", userRequestDTO.getUsername());
        Assertions.assertEquals("updatedUser@test.com", userRequestDTO.getEmail());
       Assertions.assertEquals("updatedPassword", userRequestDTO.getPassword());
    }
    @Test
    void test_2ArgsConstructor() {
       UserRequestDTO userRequestDTO = new UserRequestDTO("updatedUser", "updatedUser@test.com");
       assertThat(userRequestDTO).isNotNull();
       Assertions.assertEquals("updatedUser", userRequestDTO.getUsername());
       Assertions.assertEquals("updatedUser@test.com", userRequestDTO.getEmail());
    }


    @Test
     void testNoArgsConstructor() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();

        Assertions.assertNull(userRequestDTO.getUsername());
        Assertions.assertNull(userRequestDTO.getEmail());
        Assertions.assertNull(userRequestDTO.getPassword());
    }

    @Test
     void testToString() {
        String toStringResult = userRequestDTO.toString();

        Assertions.assertTrue(toStringResult.contains("UserRequestDTO"));
        Assertions.assertTrue(toStringResult.contains("id=null"));
        Assertions.assertTrue(toStringResult.contains("username=testUser"));
        Assertions.assertTrue(toStringResult.contains("email=testUser@test.com"));
        Assertions.assertTrue(toStringResult.contains("password=testPassword"));
    }
}
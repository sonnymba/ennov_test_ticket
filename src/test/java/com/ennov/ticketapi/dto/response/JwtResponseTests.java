package com.ennov.ticketapi.dto.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

 class JwtResponseTests {

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
     void testValidJwtResponse() {
        Long id = 1L;
        String username = "testUser";
        List<String> stringRoles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
        JwtResponse jwtResponse = new JwtResponse("testToken", "Bearer", id, username, stringRoles);

        Assertions.assertEquals("testToken", jwtResponse.getAccessToken());
        Assertions.assertEquals("Bearer", jwtResponse.getType());
        Assertions.assertEquals(id, jwtResponse.getId());
        Assertions.assertEquals(username, jwtResponse.getUsername());
        Assertions.assertEquals(stringRoles, jwtResponse.getRoles());
    }
    @Test
    void test4ArgsJwtResponse() {
       Long id = 1L;
       String username = "testUser";
       List<String> stringRoles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
       JwtResponse jwtResponse = new JwtResponse("testToken",  id, username, stringRoles);

       Assertions.assertEquals("testToken", jwtResponse.getAccessToken());
       Assertions.assertEquals(id, jwtResponse.getId());
       Assertions.assertEquals(username, jwtResponse.getUsername());
       Assertions.assertEquals(stringRoles, jwtResponse.getRoles());
    }


    @Test
     void testNoArgsConstructor() {
        JwtResponse jwtResponse = new JwtResponse();

        Assertions.assertNull(jwtResponse.getAccessToken());
        Assertions.assertEquals("Bearer", jwtResponse.getType());
        Assertions.assertNull(jwtResponse.getId());
        Assertions.assertNull(jwtResponse.getUsername());
        Assertions.assertNull(jwtResponse.getRoles());
    }

    @Test
     void testAllArgsConstructor() {
       String accessToken = "testToken";
       Long id = 1L;
       String username = "testUser";
       List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");

       JwtResponse jwtResponse = new JwtResponse(accessToken, "Bearer", id, username, roles);

       Assertions.assertEquals(accessToken, jwtResponse.getAccessToken());
       Assertions.assertEquals("Bearer", jwtResponse.getType());
       Assertions.assertEquals(id, jwtResponse.getId());
       Assertions.assertEquals(username, jwtResponse.getUsername());
       Assertions.assertEquals(roles, jwtResponse.getRoles());
    }

}
package com.ennov.ticketapi.controller;

import com.ennov.ticketapi.config.security.jwt.JwtUtils;
import com.ennov.ticketapi.config.security.services.UserDetailsImpl;
import com.ennov.ticketapi.dto.RoleDTO;
import com.ennov.ticketapi.dto.request.AuthRequestDTO;
import com.ennov.ticketapi.dto.response.JwtResponse;
import com.ennov.ticketapi.dto.response.LiteUserDTO;
import com.ennov.ticketapi.entities.Role;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.exceptions.AuthenticationException;
import com.ennov.ticketapi.service.MainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    public static final String ROLE_USER = "ROLE_USER";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    @Mock
    private AuthenticationManager authManager;

    @Mock
    private MainService mainService;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogin() {
        // Set up authentication token
        String username = "testuser";
        String password = "testpassword";
        UserDetails userDetails = mock(UserDetailsImpl.class);

        when(userDetails.getUsername()).thenReturn(username);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        // Set up request body
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername(username);
        request.setPassword(password);

        // Perform request
        ResponseEntity<?> response = accountController.login(request);

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        JwtResponse jwtResponse = (JwtResponse) response.getBody();
        assertNotNull(jwtResponse);
        assertNotEquals(jwtResponse.getAccessToken(), empty());
        assertEquals("Bearer", jwtResponse.getType());
        assertNotNull(jwtResponse.getId());
        assertEquals(username, jwtResponse.getUsername());
        List<String> roles = jwtResponse.getRoles();
        assertNotEquals(roles, empty());

        // Verify authentication manager was called
        verify(authManager, times(1))
                .authenticate(eq(new UsernamePasswordAuthenticationToken(username, password)));

        // Verify JWT utils was called
        verify(jwtUtils, times(1)).generateJwtToken(authentication);
    }
    @Test
    public void testLogin_BadCredentialsException() {
        // Set up authentication token
        String username = "testuser";
        String password = "testpassword";
        when(authManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Set up request body
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername(username);
        request.setPassword(password);

        // Perform request
        assertThrows(AuthenticationException.class, () -> accountController.login(request));

        // Verify authentication manager was called
        verify(authManager, times(1))
                .authenticate(eq(new UsernamePasswordAuthenticationToken(username, password)));

        // Verify JWT utils was not called
        verify(jwtUtils, times(0)).generateJwtToken(any(Authentication.class));
    }

    @Test
    public void testRoles() {
        // Set up roles
        List<Role> roles = Arrays.asList(
                new Role(ROLE_USER),
                new Role(ROLE_ADMIN)
        );
        // Set up roles
        when(mainService.getRoles()).thenReturn(roles);

        // Perform request
        ResponseEntity<List<RoleDTO>> response = accountController.roles();

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        List<RoleDTO> responseRoles = response.getBody();
        assertEquals(2, responseRoles.size());

        // Verify main service was called
        verify(mainService, times(1))
                .getRoles();
    }

    @Test
    public void testMakeAdmin() {
        // Set up user
        Long id = 1L;
        User user = new User();
        user.setId(id);
        when(mainService.makeAdmin(id)).thenReturn(user);

        // Perform request
        ResponseEntity<LiteUserDTO> response = accountController.makeAdmin(id);

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        LiteUserDTO userDTO = response.getBody();
        assertEquals(id, userDTO.getId());

        // Verify main service was called
        verify(mainService, times(1))
                .makeAdmin(id);
    }

    @Test
    public void testRemoveAdmin() {
        // Set up user
        Long id = 1L;
        User user = new User();
        user.setId(id);
        when(mainService.removeAdmin(id)).thenReturn(user);

        // Perform request
        ResponseEntity<LiteUserDTO> response = accountController.removeAdmin(id);

        // Verify response
        assertEquals(200, response.getStatusCodeValue());
        LiteUserDTO userDTO = response.getBody();
        assertEquals(id, userDTO.getId());

        // Verify main service was called
        verify(mainService, times(1))
                .removeAdmin(id);
    }
}
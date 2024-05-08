package com.ennov.ticketapi.controller;

import com.ennov.ticketapi.dto.request.UserRequestDTO;
import com.ennov.ticketapi.dto.response.LiteTicketDTO;
import com.ennov.ticketapi.dto.response.LiteUserDTO;
import com.ennov.ticketapi.entities.Ticket;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.service.TicketService;
import com.ennov.ticketapi.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    public void setup() {}

    @Test
    public void testList() {
        // Given
        List<User> users = Arrays.asList(new User(), new User());
        when(userService.getAll()).thenReturn(users);

        // When
        ResponseEntity<List<LiteUserDTO>> response = userController.list();

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testListAssignedTicketToUser() {
        // Given
        Long id = 1L;
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketService.listAssignedToUser(id)).thenReturn(tickets);

        // When
        ResponseEntity<List<LiteTicketDTO>> response = userController.listAssignedTicketToUser(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
    }

    @Test
    public void testSave() {
        // Given
        UserRequestDTO dto = new UserRequestDTO();
        dto.setUsername("testUser");
        dto.setEmail("test@example.com");
        dto.setPassword("testPassword");

        when(userService.save(dto)).thenReturn(new User());

        // When
        ResponseEntity<LiteUserDTO> response = userController.save(dto);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }


    @Test
    public void testUpdate() {
        // Given
        Long id = 1L;
        UserRequestDTO dto = new UserRequestDTO();
        dto.setUsername("testUser");
        dto.setEmail("test@example.com");
        dto.setPassword("testPassword");
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setPassword(dto.getPassword());

        when(userService.update(id, dto)).thenReturn(user);

        // When
        ResponseEntity<LiteUserDTO> response = userController.update(id, dto);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }



}
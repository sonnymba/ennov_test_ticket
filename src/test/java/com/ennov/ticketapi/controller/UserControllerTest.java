package com.ennov.ticketapi.controller;

import com.ennov.ticketapi.dto.request.UserRequestDTO;
import com.ennov.ticketapi.dto.response.LiteTicketDTO;
import com.ennov.ticketapi.dto.response.LiteUserDTO;
import com.ennov.ticketapi.entities.Ticket;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.exceptions.APIException;
import com.ennov.ticketapi.service.TicketService;
import com.ennov.ticketapi.service.UserService;
import com.ennov.ticketapi.utils.MyUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private TicketService ticketService;

    @Mock
    private MyUtils myUtils;

    @InjectMocks
    private UserController userController;

    @BeforeEach
     void setup() {
        userService = mock(UserService.class);
        ticketService = mock(TicketService.class);
        myUtils = mock(MyUtils.class);
        userController = new UserController(userService, ticketService);
    }

    @Test
     void testList_ReturnsAllUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);
        users.add(user1);
        users.add(user2);

        when(userService.getAll()).thenReturn(users);

        ResponseEntity<List<LiteUserDTO>> response = userController.list();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        verify(userService, times(1)).getAll();
    }

    @Test
     void testListAssignedTicketToUser_ReturnsAllTicketsAssignedToUser() {
        Long userId = 1L;
        List<Ticket> tickets = new ArrayList<>();
        Ticket ticket1 = new Ticket();
        ticket1.setId(1L);
        Ticket ticket2 = new Ticket();
        ticket2.setId(2L);
        tickets.add(ticket1);
        tickets.add(ticket2);

        when(ticketService.listAssignedToUser(userId)).thenReturn(tickets);

        ResponseEntity<List<LiteTicketDTO>> response = userController.listAssignedTicketToUser(userId);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        verify(ticketService, times(1)).listAssignedToUser(userId);
    }



    @Test
     void testSave_SavesUserAndReturnsCreatedResponse() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setEmail("valid-email@example.com");

        when(userService.save(dto)).thenReturn(new User());

        ResponseEntity<LiteUserDTO> response = userController.save(dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        verify(userService, times(1)).save(dto);
    }

    @Test
     void testUpdate_UpdatesUserAndReturnsOkResponse() {
        Long id = 1L;
        UserRequestDTO dto = new UserRequestDTO();
        dto.setEmail("valid-email@example.com");

        when(userService.update(id, dto)).thenReturn(new User());

        ResponseEntity<LiteUserDTO> response = userController.update(id, dto);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userService, times(1)).update(id, dto);
    }

    @Test
    void listAssignedTicketToUser_throwsAPIException() {
        Long id = null;

        APIException exception = assertThrows(APIException.class, () -> userController.listAssignedTicketToUser(id));

        assertThat(exception.getMessage()).isEqualTo("id is required");
    }

    @Test
    void update_throwsAPIException() {
        Long id = null;
        UserRequestDTO dto = new UserRequestDTO();
        APIException exception = assertThrows(APIException.class, () -> userController.update(id, dto));

        assertThat(exception.getMessage()).isEqualTo("id is required");
    }

    @Test
    void update_throwsIllegalArgumentException() {
        Long id = 1L;
        UserRequestDTO dto = new UserRequestDTO();
        dto.setEmail("abceeff");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userController.update(id, dto));

        assertThat(exception.getMessage()).isNotNull();
    }

    @Test
    void save_throwsIllegalArgumentException() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setEmail("abceeff");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> userController.save(dto));

        assertThat(exception.getMessage()).isNotNull();
    }
}
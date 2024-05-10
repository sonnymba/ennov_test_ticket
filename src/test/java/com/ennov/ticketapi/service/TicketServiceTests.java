package com.ennov.ticketapi.service;

import com.ennov.ticketapi.dao.TicketRepository;
import com.ennov.ticketapi.dao.UserRepository;
import com.ennov.ticketapi.dto.request.TicketRequestDTO;
import com.ennov.ticketapi.entities.Ticket;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.enums.Status;
import com.ennov.ticketapi.exceptions.APIException;
import com.ennov.ticketapi.exceptions.ResourceNotFoundException;
import com.ennov.ticketapi.exceptions.UserNotFoundException;
import com.ennov.ticketapi.service.impl.MainServiceImpl;
import com.ennov.ticketapi.service.impl.TicketServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceTests {

    public static final long ID = 1L;
    public static final long ID_USER = ID;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketServiceimpl ticketService;

    @Mock
    private MainServiceImpl mainService;

    private Ticket ticket;
    private TicketRequestDTO ticketDto;
    private User user;
    private List<Ticket> ticketList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        ticket = new Ticket();
        ticket.setTitle("Test Ticket");
        ticket.setDescription("Test Description");
        ticket.setAssignedTo(user);
        ticket.setStatus(Status.EN_COURS);
        user = new User();
        ticketList.add(ticket);
        ticketDto = new TicketRequestDTO();
        ticketDto.setTitle("Test Ticket");
        ticketDto.setDescription("Test Description");
        ticketDto.setStatus(Status.EN_COURS.toString());
    }

    @Test
    void testSave() throws Exception {
        doReturn(ticket).when(ticketRepository).save(any(Ticket.class));
        Ticket savedTicket = ticketService.save(ticketDto);
        assertNotNull(savedTicket);
        assertEquals(ticket, savedTicket);
    }

    @Test
    void testGetAllTickets() {
        when(ticketRepository.findAll()).thenReturn(ticketList);
        List<Ticket> tickets = ticketService.getAll();
        assertEquals(ticketList, tickets);
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    void testGetOne() {
        when(ticketRepository.findById(ID)).thenReturn(Optional.of(ticket));
        Ticket foundTicket = ticketService.getOne(ID);
        assertEquals(ticket, foundTicket);
        verify(ticketRepository, times(1)).findById(ID);
    }

    @Test
    void testGetOneNotFound() {
        when(ticketRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> ticketService.getOne(ID));
        verify(ticketRepository, times(1)).findById(ID);
    }

    @Test
    void testUpdateTicket() {
        when(ticketRepository.findById(ID)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        TicketRequestDTO updatedTicket = new TicketRequestDTO();
        updatedTicket.setTitle("Updated Ticket");
        updatedTicket.setDescription("Updated Description");
        updatedTicket.setStatus(Status.EN_COURS.toString());
        Ticket savedTicket = ticketService.update(ID, updatedTicket);
        Ticket ticketEntity = ticketService.getOne(ID);
        assertEquals(ticketEntity, savedTicket);
        verify(ticketRepository, times(1)).save(ticketEntity);
    }

    @Test
    void testUpdateTicketNotFound() {
        when(ticketRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> ticketService.update(ID, ticketDto));
        verify(ticketRepository, times(1)).findById(ID);
    }

    @Test
    void testDeleteTicket() {
        when(ticketRepository.findById(ID)).thenReturn(Optional.of(ticket));
        ticketService.delete(ID);
        verify(ticketRepository, times(1)).delete(ticket);
    }

    @Test
    void testDeleteTicketNotFound() {
        when(ticketRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> ticketService.delete(ID));
        verify(ticketRepository, times(1)).findById(ID);
    }

    @Test
    void testAssignTicketToUser() {
        when(ticketRepository.findById(ID)).thenReturn(Optional.of(ticket));
        when(userRepository.findById(ID_USER)).thenReturn(Optional.of(user));
        User userToAssign = userRepository.getById(ID);
        ticket.setAssignedTo(userToAssign);
        Ticket ticketR = ticketRepository.save(ticket);
        Ticket assignTicket = ticketService.assignTicketToUser(ID, ID_USER);
        assertEquals(assignTicket, ticketR);
        verify(ticketRepository, times(1)).findById(ID);
        verify(userRepository, times(1)).getById(ID_USER);
    }

    @Test
    void testGetlistAssignedTickerusers() {
        when(userRepository.findById(ID_USER)).thenReturn(Optional.of(user));
        when(ticketRepository.findByAssignedTo(user)).thenReturn(ticketList);
        List<Ticket> tickets = ticketService.listAssignedToUser(ID);
        assertEquals(ticketList, tickets);
        verify(ticketRepository, times(1)).findByAssignedTo(user);
    }

    @Test
     void testAssignTicketToUser_WithNullId() {
        Long id = null;
        Long userId = 123L;
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> ticketService.assignTicketToUser(id, userId));
        assertThat(exception.getMessage()).isNotNull();
    }

    @Test
    void testAssignTicketToUser_WithNullUserId() {
        Long id = 12L;
        Long userId = null;
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setTitle("test tite");
        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> ticketService.assignTicketToUser(id, userId));
        assertThat(exception.getMessage()).isEqualTo("This user not found");
    }



    @Test
     void testListAssignedToUser_WithNullUserId() {
        Long userId = null;
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> ticketService.listAssignedToUser(userId));
        assertThat(exception.getMessage()).isEqualTo("User not found with id "+userId);
    }


    @Test
     void testSave_WithExistTitle() {
        TicketRequestDTO dto = new TicketRequestDTO();
        dto.setTitle("Existing Title");
        when(ticketRepository.getByTitle(dto.getTitle())).thenReturn(Optional.of(ticket));
        APIException exception = assertThrows(APIException.class, () -> ticketService.save(dto));
        assertThat(exception.getMessage()).isEqualTo("Ticket with title already exists");
    }
}
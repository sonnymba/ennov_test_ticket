package com.ennov.ticketApi.service;

import com.ennov.ticketApi.dao.TicketRepository;
import com.ennov.ticketApi.dao.UserRepository;
import com.ennov.ticketApi.dto.request.TicketRequestDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.enums.Status;
import com.ennov.ticketApi.exceptions.ResourceNotFoundException;
import com.ennov.ticketApi.service.impl.TicketServiceimpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    public static final long ID = 1L;
    public static final long ID_USER = ID;
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TicketServiceimpl ticketService;

    private Ticket ticket;
    private TicketRequestDTO ticketDto;
    private User user;
    private List<Ticket> ticketList;

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
        ticketDto.setStatus(Status.EN_COURS.getValue());
    }

    @Test
    void testSaveTicket() {
        when(ticketRepository.save(ticket)).thenReturn(ticket);
        Ticket savedTicket = ticketService.save(ticketDto);
        assertEquals(ticket, savedTicket);
        verify(ticketRepository, times(1)).save(ticket);
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
        updatedTicket.setStatus(Status.EN_COURS.getValue());
        Ticket savedTicket = ticketService.update(ID, updatedTicket);
        Ticket ticketEntity = ticketService.getOne(ID);
        assertEquals(updatedTicket, savedTicket);
        verify(ticketRepository, times(1)).findById(ID);
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
        assertThrows(ResourceNotFoundException.class, () -> ticketService.assignTicketToUser(ID, ID_USER));
        //assertEquals(ticket.getAssignedTo(), user);
        verify(ticketRepository, times(1)).findById(ID);
    }

    @Test
    void testGetlistAssignedTickerusers() {
        when(userRepository.findById(ID_USER)).thenReturn(Optional.of(user));
        User assignUser = userRepository.getReferenceById(ID);
        when(ticketRepository.findByAssignedUser(assignUser)).thenReturn(ticketList);
        List<Ticket> tickets = ticketService.listAssignedToUser(ID_USER);
        assertEquals(ticketList, tickets);
        verify(ticketRepository, times(1)).findByAssignedUser(assignUser);
    }
}
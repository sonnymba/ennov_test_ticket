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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @InjectMocks
    private MainService mainService;

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

//    @Test
//    void testSave() throws Exception {
//        TicketRequestDTO dto = new TicketRequestDTO();
//        dto.setTitle("Test Ticket");
//        dto.setDescription("Test Description");
//        dto.setStatus(Status.EN_COURS.toString());
//
//        Ticket savedTicket = new Ticket();
//        savedTicket.setId(UUID.randomUUID().getMostSignificantBits());
//        savedTicket.setAssignedTo(ticketService.getCurrentUser());
//        Ticket response = ticketService.save(dto);
//        assertEquals(savedTicket.getId(), response.getId());
//        verify(ticketRepository, times(1)).save(any(Ticket.class));
//    }

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
        User userToAssign = userRepository.getReferenceById(ID);
        ticket.setAssignedTo(userToAssign);
        Ticket ticketR = ticketRepository.save(ticket);
        Ticket assignTicket = ticketService.assignTicketToUser(ID, ID_USER);
        assertEquals(assignTicket, ticketR);
        verify(ticketRepository, times(1)).findById(ID);
        verify(userRepository, times(1)).getReferenceById(ID_USER);
    }

    @Test
    void testGetlistAssignedTickerusers() {
        when(userRepository.findById(ID_USER)).thenReturn(Optional.of(user));
        when(ticketRepository.findByAssignedTo(user)).thenReturn(ticketList);
        List<Ticket> tickets = ticketService.listAssignedToUser(ID);
        assertEquals(ticketList, tickets);
        verify(ticketRepository, times(1)).findByAssignedTo(user);
    }
}
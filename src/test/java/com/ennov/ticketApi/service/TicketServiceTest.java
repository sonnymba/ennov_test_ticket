package com.ennov.ticketApi.service;

import com.ennov.ticketApi.dao.PrivilegeRepository;
import com.ennov.ticketApi.dao.RoleRepository;
import com.ennov.ticketApi.dao.TicketRepository;
import com.ennov.ticketApi.dao.UserRepository;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.enums.Status;
import com.ennov.ticketApi.mapper.TicketMapper;
import com.ennov.ticketApi.mapper.UserMapper;
import com.ennov.ticketApi.service.impl.TicketServiceimpl;
import com.ennov.ticketApi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {


    public static final Long TICKET_ID = 79L;
    public static final String TICKET_TITLE = "Ticket-01";
    public static final String TICKET_DESCRIPTION = "Une petite description";

    public static Status TICKET_STATUS = Status.EN_COURS;

    @InjectMocks
    TicketServiceimpl ticketService;

    @Mock
    UserRepository userRepository;

    @Mock
    TicketMapper mapper;


    @Mock
    TicketRepository repository;



    //Récupérer tous les tickets
    @Test
    void whenGetAll_thenListOfTicketResponseDTO(){
        List<Ticket> tickets = new ArrayList<>();
        tickets.add(new Ticket());
        List<Ticket> mapList = ticketService.getAll();

        given(mapList).willReturn(tickets);
        List<Ticket> excepted = ticketService.getAll();
        assertEquals(excepted, tickets);
        verify(repository).findAll();
    }


    //Récupérer un ticket par son ID.
    @Test
    void givenId_whenGetOne_thenTicketResponseDTO(){

    }

    //Créer un nouveau ticket
    @Test
    void givenTicketRequestDTO_whenSave_thenSmallTicketDTO(){

    }

    //Mettre à jour un ticket existant.
    @Test
    void givenIdAndTicketRequestDTO_whenUpdate_thenTicketResponseDTO(){

    }

    //Assigner un ticket à un utilisateur.
    @Test
    void givenIdAndUserId_whenAssignTicketToUser_thenTicketResponseDTO(){

    }

    //Supprimer un ticket par son ID.
    @Test
    void whenGivenId_shouldDeleteTicket_ifFound(){
       Ticket ticket = new Ticket();
       ticket.setId(TICKET_ID);
       ticket.setTitle(TICKET_TITLE);
       ticket.setDescription(TICKET_DESCRIPTION);
       ticket.setStatus(TICKET_STATUS);

       given(repository.findById(anyLong())).willReturn(Optional.of(ticket));
       ticketService.delete(ticket.getId());
    }
}

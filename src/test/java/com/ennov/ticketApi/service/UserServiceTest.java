package com.ennov.ticketApi.service;

import com.ennov.ticketApi.dao.PrivilegeRepository;
import com.ennov.ticketApi.dao.RoleRepository;
import com.ennov.ticketApi.dao.TicketRepository;
import com.ennov.ticketApi.dao.UserRepository;
import com.ennov.ticketApi.dto.request.UserRequestDTO;
import com.ennov.ticketApi.dto.response.SmallUserDTO;
import com.ennov.ticketApi.dto.response.UserResponseDTO;
import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import com.ennov.ticketApi.mapper.UserMapper;
import com.ennov.ticketApi.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    public static final Long ID_TEST = 1L;
    public static final String USERNAME_TEST = "admin";
    public static final String EMAIL_TEST = "admin@test.com";
    public static final String PASSWORD_TEST = "admin";

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PrivilegeRepository privilegeRepository;

    @Mock
    TicketRepository ticketRepository;


    @Test
    void givenUserRequestDTO_whenSave_thenReturnUser() throws Exception{
        //given UserRequestDTO
        UserRequestDTO dto = new UserRequestDTO();
        dto.setUsername(USERNAME_TEST);
        dto.setEmail(EMAIL_TEST);
        dto.setPassword(PASSWORD_TEST);

        //When
        when(userRepository.save(isA(User.class))).thenAnswer(invocation -> (User) invocation.getArguments()[0]);
        User user = userService.save(dto);

        //then
        verifyNoMoreInteractions(userRepository);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(USERNAME_TEST);
        assertThat(user.getEmail()).isEqualTo(EMAIL_TEST);
        assertThat(user.getRoles()).isNotNull();
    }


    //Modifier un user
    @Test
    void givenUserRequestDTOandId_whenUpdate_thenReturnUser_ifFound(){
        User user = new User();
        user.setId(ID_TEST);
        user.setUsername(USERNAME_TEST);
        user.setEmail(EMAIL_TEST);


        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        User saved = userService.update(user.getId(), new UserRequestDTO(user.getUsername(), user.getEmail(), PASSWORD_TEST));
        verify(userRepository, times(1)).save(user);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isEqualTo(ID_TEST);
        assertThat(saved.getEmail()).isEqualTo(EMAIL_TEST);
        assertThat(saved.getUsername()).isEqualTo(USERNAME_TEST);
    }

    //Reccuperer tous les users
    @Test
    void whenGetAll_thenReturnListOfSmallUserDTO(){
        List<User> users = new ArrayList<>();
        users.add(new User());
        List<User> mapList = userService.getAll();

        given(mapList).willReturn(users);
        List<User> excepted = userService.getAll();
        assertEquals(excepted, users);
        verify(userRepository).findAll();
    }




}

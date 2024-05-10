package com.ennov.ticketapi.service;

import com.ennov.ticketapi.dao.PrivilegeRepository;
import com.ennov.ticketapi.dao.RoleRepository;
import com.ennov.ticketapi.dao.UserRepository;
import com.ennov.ticketapi.dto.request.UserRequestDTO;
import com.ennov.ticketapi.entities.Role;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.exceptions.APIException;
import com.ennov.ticketapi.exceptions.ResourceNotFoundException;
import com.ennov.ticketapi.service.impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserServiceTests {

    public static final String USERNAME = "testUser";
    public static final String EMAIL = "test@example.com";
    public static final String PASSWORD = "testPassword";
    public static final String ROLE_USER = "ROLE_USER";
    public static final long ID = 1L;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PrivilegeRepository privilegeRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    private User user;
    private UserRequestDTO dto;
    private List<User> userList;
    private Role role;
    private Collection<Role> roles;

    @BeforeEach
    void setUp() {
        initMocks(this);
        user = new User();
        user.setId(ID);
        user.setUsername(USERNAME);
        user.setEmail(EMAIL);
        user.setPassword(passwordEncoder.encode(PASSWORD));
        user.setEnabled(true);
        user.setRoles(roles);

        role = new Role();
        role.setId(ID);
        role.setName(ROLE_USER);
        roles = new ArrayList<>();
        roles.add(role);
        userList = new ArrayList<>();
        userList.add(user);

        dto = new UserRequestDTO();
        dto.setUsername(USERNAME);
        dto.setEmail(EMAIL);
        dto.setPassword(PASSWORD);
    }

    @Test
    void testSaveUser() {
        doReturn(user).when(userRepository).save(any(User.class));
        User savedUser = userService.save(dto);
        assertNotNull(savedUser);
        assertEquals(user, savedUser);
    }

    @Test
    void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(userList);
        List<User> users = userService.getAll();
        assertEquals(userList, users);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetOneId() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        User foundUser = userService.getOne(ID);
        assertEquals(user, foundUser);
        verify(userRepository, times(1)).findById(ID);
    }

    @Test
    void testGetOneNotFound() {
        when(userRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getOne(ID));
        verify(userRepository, times(1)).findById(ID);
    }

    @Test
    void testUpdateUser() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        dto.setUsername("updatedUser");
        dto.setEmail("updated@example.com");
        dto.setPassword("updatedPassword");
        user.setEmail(dto.getEmail());
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        User updatedUser = userService.update(ID, dto);
        assertEquals(user, updatedUser);
        verify(userRepository, times(1)).findById(ID);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testUpdateUserNotFound() {
        when(userRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.update(ID, dto));
        verify(userRepository, times(1)).findById(ID);
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById(ID)).thenReturn(Optional.of(user));
        userService.delete(ID);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testDelete_UserNotFound() {
        when(userRepository.findById(ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.delete(ID));
    }

    @Test
     void testSave_WithExistingUsername() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setUsername("existingUser");
        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));
        APIException exception = assertThrows(APIException.class, () -> userService.save(dto));
        assertThat(exception.getMessage()).isEqualTo("This username is already in use");
    }

    @Test
     void testSave_WithExistingEmail() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setEmail("existingUser@email.com");
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        APIException exception = assertThrows(APIException.class, () -> userService.save(dto));
        assertThat(exception.getMessage()).isEqualTo("This email is already in use");
    }


}
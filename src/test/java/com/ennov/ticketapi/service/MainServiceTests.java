package com.ennov.ticketapi.service;

import com.ennov.ticketapi.dao.RoleRepository;
import com.ennov.ticketapi.dao.UserRepository;
import com.ennov.ticketapi.entities.Role;
import com.ennov.ticketapi.entities.User;
import com.ennov.ticketapi.exceptions.APIException;
import com.ennov.ticketapi.exceptions.ResourceNotFoundException;
import com.ennov.ticketapi.service.impl.MainServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MainServiceTests {
    public static final String TEST_USER = "testUser";
    public static final String CREDENTIALS = "password";
    public static final long ID = 9L;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private MainServiceImpl mainService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetCurrentUser_anonymous() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        Authentication authentication = new AnonymousAuthenticationToken("anonymous", "admin", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = mainService.getCurrentUser();
        assertInstanceOf(User.class, user);
    }

    @Test
    public void testGetCurrentUser_authenticated() {
        User user = mainService.getCurrentUser();
        UserDetails userPrincipal = mock(UserDetails.class);
        when(userPrincipal.getUsername()).thenReturn(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userPrincipal, userPrincipal.getPassword(), new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        assertEquals(userPrincipal.getUsername(), user.getUsername());
    }

    @Test
    public void testMakeAdmin_success() {
        Long id = 1L;
        User user = new User();
        user.setId(id);
        Role role = new Role();
        role.setName(ROLE_ADMIN);

        when(userRepository.findById(eq(ID))).thenReturn(Optional.of(user));
        when(roleRepository.getByName(eq(ROLE_ADMIN))).thenReturn(Optional.of(role));

        User result = mainService.makeAdmin(ID);
        if (result!= null) {
            assertEquals(ID, result.getId().longValue());
            assertTrue(result.getRoles().contains(role));
        } else {
            assertNull(result, "User not found with id "+ID);
        }
    }

    @Test
    public void testMakeAdmin_userNotFound() {
        when(userRepository.findById(eq(ID))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> mainService.makeAdmin(ID));
    }

    @Test
    public void testMakeAdmin_roleNotFound() {
        User user = new User();
        user.setId(ID);
        when(userRepository.findById(eq(ID))).thenReturn(Optional.of(user));
        when(roleRepository.getByName(eq(ROLE_ADMIN))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> mainService.makeAdmin(ID));
    }

    @Test
    public void testMakeAdmin_userAlreadyHasRole() {
        User user = new User();
        user.setId(ID);
        Role role = new Role();
        role.setName(ROLE_ADMIN);
        user.getRoles().add(role);
        when(userRepository.findById(eq(ID))).thenReturn(Optional.of(user));
        when(roleRepository.getByName(eq(ROLE_ADMIN))).thenReturn(Optional.of(role));
        assertThrows(APIException.class, () -> mainService.makeAdmin(ID));
    }

    @Test
    public void testRemoveAdmin_success() {
        User user = new User();
        user.setId(ID);
        Role role = new Role();
        role.setName(ROLE_ADMIN);
        user.getRoles().add(role);
        when(userRepository.findById(eq(ID))).thenReturn(Optional.of(user));
        when(roleRepository.getByName(eq(ROLE_ADMIN))).thenReturn(Optional.of(role));
        User result = mainService.removeAdmin(ID);
        if (result!= null) {
            assertEquals(ID, result.getId().longValue());
            assertFalse(result.getRoles().contains(role));
        } else {
            assertNull(result, "User not found with id "+ID);
        }

    }

    @Test
    public void testRemoveAdmin_userNotFound() {
        when(userRepository.findById(eq(ID))).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> mainService.removeAdmin(ID));
    }

    @Test
    public void testRemoveAdmin_defaultUser() {
        User user = new User();
        user.setId(ID);
        user.setDefaultUser(true);
        Role role = new Role();
        role.setName(ROLE_ADMIN);
        user.getRoles().add(role);
        when(userRepository.findById(eq(ID))).thenReturn(Optional.of(user));
        when(roleRepository.getByName(eq(ROLE_ADMIN))).thenReturn(Optional.of(role));
        assertThrows(APIException.class, () -> mainService.removeAdmin(ID));
    }

    @Test
    public void testRemoveAdmin_userDoesNotHaveRole() {
        User user = new User();
        user.setId(ID);
        Role role = new Role();
        role.setName(ROLE_ADMIN);
        when(userRepository.findById(eq(ID))).thenReturn(Optional.of(user));
        when(roleRepository.getByName(eq(ROLE_ADMIN))).thenReturn(Optional.of(role));
        assertThrows(APIException.class, () -> mainService.removeAdmin(ID));
    }

    @Test
    public void testGetRoles_success() {
        List<Role> roles = new ArrayList<>();
        roles.add(new Role());
        roles.add(new Role());
        when(roleRepository.findAll()).thenReturn(roles);
        List<Role> result = mainService.getRoles();
        assertEquals(2, result.size());
    }
}
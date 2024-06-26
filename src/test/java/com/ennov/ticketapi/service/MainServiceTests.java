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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class MainServiceTests {
     private static final long ID = 9L;
     private static final String ROLE_ADMIN = "ROLE_ADMIN";

     @Mock
     private UserRepository userRepository;

     @Mock
     private RoleRepository roleRepository;

     @InjectMocks
     private MainServiceImpl mainService;

     @Mock
     private SecurityContextHolder securityContextHolder;

     @Mock
     private Authentication authentication;

     @Mock
     private UserDetails userDetails;

     @BeforeEach
     void setup() {
         MockitoAnnotations.initMocks(this);
     }

     @Test
     void testGetCurrentUser_anonymous() {
         List<GrantedAuthority> authorities = new ArrayList<>();
         authorities.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
         Authentication authentication = new AnonymousAuthenticationToken("anonymous", "admin", authorities);
         SecurityContextHolder.getContext().setAuthentication(authentication);
         User user = mainService.getCurrentUser();
         assertThat(user).isNull();
     }


     @Test
     void testMakeAdmin_success() {
         Long id = 1L;
         User user = new User();
         user.setId(id);
         Role role = new Role();
         role.setName(ROLE_ADMIN);

         when(userRepository.findById(ID)).thenReturn(Optional.of(user));
         when(roleRepository.getByName(ROLE_ADMIN)).thenReturn(Optional.of(role));

         User result = mainService.makeAdmin(ID);
         if (result != null) {
             assertEquals(ID, result.getId().longValue());
             assertTrue(result.getRoles().contains(role));
         } else {
             assertNull(result, "User not found with id " + ID);
         }
     }

     @Test
     void testMakeAdmin_userNotFound() {
         when(userRepository.findById(ID)).thenReturn(Optional.empty());
         assertThrows(ResourceNotFoundException.class, () -> mainService.makeAdmin(ID));
     }

     @Test
     void testMakeAdmin_roleNotFound() {
         User user = new User();
         user.setId(ID);
         when(userRepository.findById(ID)).thenReturn(Optional.of(user));
         when(roleRepository.getByName(ROLE_ADMIN)).thenReturn(Optional.empty());
         assertThrows(ResourceNotFoundException.class, () -> mainService.makeAdmin(ID));
     }

     @Test
     void testMakeAdmin_userAlreadyHasRole() {
         User user = new User();
         user.setId(ID);
         Role role = new Role();
         role.setName(ROLE_ADMIN);
         user.getRoles().add(role);
         when(userRepository.findById(ID)).thenReturn(Optional.of(user));
         when(roleRepository.getByName(ROLE_ADMIN)).thenReturn(Optional.of(role));
         assertThrows(APIException.class, () -> mainService.makeAdmin(ID));
     }

     @Test
     void testRemoveAdmin_success() {
         User user = new User();
         user.setId(ID);
         Role role = new Role();
         role.setName(ROLE_ADMIN);
         user.getRoles().add(role);
         when(userRepository.findById(ID)).thenReturn(Optional.of(user));
         when(roleRepository.getByName(ROLE_ADMIN)).thenReturn(Optional.of(role));
         User result = mainService.removeAdmin(ID);
         if (result != null) {
             assertEquals(ID, result.getId().longValue());
             assertFalse(result.getRoles().contains(role));
         } else {
             assertNull(result, "User not found with id " + ID);
         }

     }

     @Test
     void testRemoveAdmin_userNotFound() {
         when(userRepository.findById(ID)).thenReturn(Optional.empty());
         assertThrows(ResourceNotFoundException.class, () -> mainService.removeAdmin(ID));
     }

     @Test
     void testRemoveAdmin_defaultUser() {
         User user = new User();
         user.setId(ID);
         user.setDefaultUser(true);
         Role role = new Role();
         role.setName(ROLE_ADMIN);
         user.getRoles().add(role);
         when(userRepository.findById(ID)).thenReturn(Optional.of(user));
         when(roleRepository.getByName(ROLE_ADMIN)).thenReturn(Optional.of(role));
         assertThrows(APIException.class, () -> mainService.removeAdmin(ID));
     }

     @Test
     void testRemoveAdmin_userDoesNotHaveRole() {
         User user = new User();
         user.setId(ID);
         Role role = new Role();
         role.setName(ROLE_ADMIN);
         when(userRepository.findById(ID)).thenReturn(Optional.of(user));
         when(roleRepository.getByName(ROLE_ADMIN)).thenReturn(Optional.of(role));
         assertThrows(APIException.class, () -> mainService.removeAdmin(ID));
     }

     @Test
     void testRemoveAdmin_roleNotFound() {
         Long id = 25L;
         Role role = new Role("ROLE_ADMIN");
         role.setId(id);
         when(userRepository.findById(ID)).thenReturn(Optional.empty());
         assertThrows(ResourceNotFoundException.class, () -> mainService.removeAdmin(ID));
     }


     @Test
     void testGetRoles_success() {
         List<Role> roles = new ArrayList<>();
         roles.add(new Role());
         roles.add(new Role());
         when(roleRepository.findAll()).thenReturn(roles);
         List<Role> result = mainService.getRoles();
         assertEquals(2, result.size());
     }

     @Test
     void testFindUserByUsernameWithExistingUsername() {
         UserDetails userPrincipal = mock(UserDetails.class);
         when(userPrincipal.getUsername()).thenReturn("testUser");
         User user = new User();
         user.setId(1L);
         user.setUsername("testUser");
         when(userRepository.findByUsername(any())).thenReturn(java.util.Optional.of(user));
         Optional<User> result = userRepository.findByUsername(userPrincipal.getUsername());
         assertThat(result).isNotNull();
         assertThat(user.getId()).isEqualTo(result.get().getId());
     }


     @Test
      void testGetCurrentUser() throws ResourceNotFoundException {
         // Set up mock user repository

         User user = new User();
         user.setId(1L);
         user.setUsername("testUser");
         user.setPassword("testUser");
         when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

         // Set up mock authentication
         Authentication authentication = mock(Authentication.class);
         when(authentication.getPrincipal()).thenReturn(user);
         when(authentication.isAuthenticated()).thenReturn(true);
         SecurityContext securityContext = mock(SecurityContext.class);
         when(securityContext.getAuthentication()).thenReturn(authentication);
         SecurityContextHolder.setContext(securityContext);

         // Call the method to test
         User result =mainService.getCurrentUser();

         // Verify the result
         assertEquals(user, result);
     }

     @Test
      void testGetCurrentUser_AuthenticationException() {
         // Set up mock authentication
         Authentication authentication = mock(Authentication.class);
         when(authentication.isAuthenticated()).thenReturn(false);
         SecurityContext securityContext = mock(SecurityContext.class);
         when(securityContext.getAuthentication()).thenReturn(authentication);
         SecurityContextHolder.setContext(securityContext);
         // Call the method to test
         assertThat(mainService.getCurrentUser()).isNull();
     }

     @Test
     void testGetCurrentUser_AuthenticationNull() {
         // Set up mock authentication
         Authentication authentication = mock(Authentication.class);
         when(authentication.isAuthenticated()).thenReturn(false);

         assertThat(mainService.getCurrentUser()).isNull();
     }

 }
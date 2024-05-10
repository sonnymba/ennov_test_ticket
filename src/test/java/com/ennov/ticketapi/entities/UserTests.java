package com.ennov.ticketapi.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

class UserTests {

    @Mock
    private Role role1;

    @Mock
    private Role role2;

    private User user;

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setUsername("testUser");
        user.setEmail("testUser@test.com");
        user.setPassword("testPassword");
        user.setEnabled(true);
        role1 = new Role("ROLE_ADMIN");
        role2 = new Role("ROLE_SER");
        user.setRoles(new ArrayList<>(Arrays.asList(role1, role2)));
    }

    @Test
     void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        Assertions.assertEquals(2, authorities.size());
    }

    @Test
     void testGetUsername() {
        Assertions.assertEquals("testUser", user.getUsername());
    }

    @Test
     void testIsAccountNonExpired() {
        Assertions.assertTrue(user.isAccountNonExpired());
    }

    @Test
     void testIsAccountNonLocked() {
        Assertions.assertTrue(user.isAccountNonLocked());
    }

    @Test
     void testIsCredentialsNonExpired() {
        Assertions.assertTrue(user.isCredentialsNonExpired());
    }

    @Test
     void testIsEnabled() {
        Assertions.assertTrue(user.isEnabled());
    }

    @Test
    void test_2ArgsConstructor() {
        User user = new User("test", "test@gmail.com");

        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("test");
        assertThat(user.getEmail()).isEqualTo("test@gmail.com");

    }
}
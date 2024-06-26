package com.ennov.ticketapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class User implements UserDetails {
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //info personnelles
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "assignedTo")
    private List<Ticket> tickets;

    @Column(nullable = false)
    private String password;


    private boolean enabled;
    private boolean defaultUser = false;




    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles = new ArrayList<>();
    private boolean tokenExpired;

    public User(String username, String email, List<Ticket> tickets) {
        this.username = username;
        this.email = email;
        this.tickets = tickets;
    }

    public User(String username, String email, Collection<Role> roles) {
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User(Long id, String username, Collection<Role> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    //userDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }


    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}

package com.ennov.ticketapi.dto.response;

import com.ennov.ticketapi.entities.Role;
import com.ennov.ticketapi.entities.User;
import lombok.*;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LiteUserDTO {
    private Long id;
    private String username;
    private String email;
    private Collection<Role> roles;

    public LiteUserDTO(User user) {
        if(user != null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.roles = user.getRoles();
        }
    }
}

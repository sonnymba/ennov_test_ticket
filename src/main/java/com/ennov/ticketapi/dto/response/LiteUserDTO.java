package com.ennov.ticketapi.dto.response;

import com.ennov.ticketapi.entities.User;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LiteUserDTO {
    private Long id;
    private String username;
    private String email;

    public LiteUserDTO(User user) {
        if(user != null) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }
}

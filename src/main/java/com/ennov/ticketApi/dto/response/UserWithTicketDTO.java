package com.ennov.ticketApi.dto.response;

import com.ennov.ticketApi.entities.User;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserWithTicketDTO {
    private Long id;
    private String username;
    private String email;
    List<LiteTicketDTO> tickets;

    public UserWithTicketDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.tickets = user.getTickets().stream().map(LiteTicketDTO::new).collect(Collectors.toList());
    }
}

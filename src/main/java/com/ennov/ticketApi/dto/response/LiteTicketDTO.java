package com.ennov.ticketApi.dto.response;

import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.enums.Status;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LiteTicketDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;

    public LiteTicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.title = ticket.getTitle();
        this.description = ticket.getDescription();
        this.status = ticket.getStatus();
    }
}

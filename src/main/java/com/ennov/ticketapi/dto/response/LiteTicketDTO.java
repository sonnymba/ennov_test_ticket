package com.ennov.ticketapi.dto.response;

import com.ennov.ticketapi.entities.Ticket;
import com.ennov.ticketapi.enums.Status;
import lombok.*;


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

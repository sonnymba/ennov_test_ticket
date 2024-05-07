package com.ennov.ticketApi.dto.response;

import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.enums.Status;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketResponseDTO {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private LiteUserDTO assignedTo;

    public TicketResponseDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.title = ticket.getTitle();
        this.description = ticket.getDescription();
        this.status = ticket.getStatus();
        this.assignedTo = new LiteUserDTO(ticket.getAssignedTo());
    }
}

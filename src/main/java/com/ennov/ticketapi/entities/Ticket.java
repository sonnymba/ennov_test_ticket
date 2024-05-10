package com.ennov.ticketapi.entities;

import com.ennov.ticketapi.dto.request.TicketRequestDTO;
import com.ennov.ticketapi.enums.Status;
import javax.persistence.*;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Ticket{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    private User assignedTo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    public Ticket(TicketRequestDTO dto){
        if(dto != null){
            this.setId(dto.getId());
            this.setTitle(dto.getTitle());
            this.setDescription(dto.getDescription());
            this.setStatus(Status.valueOf(dto.getStatus()));
        }
    }

    public Ticket(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Ticket(String title, String description, Status status, User assignedTo) {
        this.title = title;
        this.description = description;
        this.assignedTo = assignedTo;
        this.status = status;
    }
}

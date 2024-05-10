package com.ennov.ticketapi.entities;

import com.ennov.ticketapi.dto.request.TicketRequestDTO;
import com.ennov.ticketapi.enums.Status;
import javax.persistence.*;
import javax.validation.Valid;

import lombok.*;
import org.hibernate.annotations.NotFound;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
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

}

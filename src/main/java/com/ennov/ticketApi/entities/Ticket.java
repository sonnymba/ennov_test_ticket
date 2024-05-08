package com.ennov.ticketApi.entities;

import com.ennov.ticketApi.dto.request.TicketRequestDTO;
import com.ennov.ticketApi.dto.response.TicketResponseDTO;
import com.ennov.ticketApi.enums.Status;
import jakarta.persistence.*;
import lombok.*;



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
        this.setId(dto.getId());
        this.setTitle(dto.getTitle());
        this.setDescription(dto.getDescription());
        this.setStatus(Status.valueOf(dto.getStatus()));
    }

}

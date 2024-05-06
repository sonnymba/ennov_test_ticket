package com.ennov.ticketApi.entities;

import com.ennov.ticketApi.enums.Statut;
import jakarta.persistence.*;
import lombok.*;



@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Ticket extends BaseAuditingEntity {

    @Column(nullable = false, unique = true)
    private String titre;

    @Column(nullable = false)
    private String description;

    @ManyToOne()
    @Column(nullable = false)
    private User createdUser;

    @ManyToOne
    private User assignedUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut statut;

}

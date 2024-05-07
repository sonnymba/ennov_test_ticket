package com.ennov.ticketApi.entities;

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

    @ManyToOne()
    private User createdUser;

    @ManyToOne
    private User assignedUser;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

}

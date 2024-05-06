package com.ennov.ticketApi.dto.request;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketRequestDTO {
    private Long id;
    private String titre;
    private String description;
    private String statut;

}

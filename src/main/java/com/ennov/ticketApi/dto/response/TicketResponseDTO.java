package com.ennov.ticketApi.dto.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketResponseDTO {
    private Long id;
    private String titre;
    private String description;
    private String statut;
}

package com.ennov.ticketapi.dto.request;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketRequestDTO {
    private Long id;
    private String title;
    private String description;
    private String status;

    public TicketRequestDTO(String title, String description, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }
}

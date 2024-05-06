package com.ennov.ticketApi.dto.request;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketRequestDTO {
    private String title;
    private String description;
    private String status;

}

package com.ennov.ticketApi.dto.request;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AssignTicketRequestDTO {
    private Long id;
    private Long UserId;
}

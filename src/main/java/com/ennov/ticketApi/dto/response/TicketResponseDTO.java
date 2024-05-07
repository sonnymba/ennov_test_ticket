package com.ennov.ticketApi.dto.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class TicketResponseDTO {
    private Long id;
    private String title;
    private String description;
    private SmallUserDTO createdUser;
    private SmallUserDTO assignUser;
}

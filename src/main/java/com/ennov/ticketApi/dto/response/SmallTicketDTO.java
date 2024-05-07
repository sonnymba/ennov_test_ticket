package com.ennov.ticketApi.dto.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SmallTicketDTO {
    private Long id;
    private String title;
    private String description;
    private UserResponseDTO createdUser;
}

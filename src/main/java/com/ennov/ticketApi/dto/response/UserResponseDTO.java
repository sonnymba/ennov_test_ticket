package com.ennov.ticketApi.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
}

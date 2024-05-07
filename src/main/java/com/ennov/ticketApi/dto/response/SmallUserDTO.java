package com.ennov.ticketApi.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SmallUserDTO {
    private Long id;
    private String username;
    private String email;
}

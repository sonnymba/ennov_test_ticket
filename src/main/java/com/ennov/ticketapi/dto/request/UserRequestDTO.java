package com.ennov.ticketapi.dto.request;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserRequestDTO {
    private Long id;
    private String username;
    private String email;
    private String password;

    public UserRequestDTO(String username, String email) {
        this.username = username;
        this.email = email;
    }
}

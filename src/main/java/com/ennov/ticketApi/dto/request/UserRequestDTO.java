package com.ennov.ticketApi.dto.request;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserRequestDTO {
    private String username;
    private String email;
    private String password;
}

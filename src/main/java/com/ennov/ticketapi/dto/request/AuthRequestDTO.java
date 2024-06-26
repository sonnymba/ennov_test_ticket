package com.ennov.ticketapi.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthRequestDTO {
    @NotBlank(message = "Username est obligatoire")
    private String username;
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
}

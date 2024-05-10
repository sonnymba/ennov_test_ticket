package com.ennov.ticketapi.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private Long id;
    private String username;
    private List<String> roles;

    public JwtResponse(String jwt, Long id, String username, List<String> roles) {
        this.accessToken = jwt;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }

    public JwtResponse(String accessToken, String type, Long id, String username, List<String> roles) {
        this.accessToken = accessToken;
        this.type = type;
        this.id = id;
        this.username = username;
        this.roles = roles;
    }
}

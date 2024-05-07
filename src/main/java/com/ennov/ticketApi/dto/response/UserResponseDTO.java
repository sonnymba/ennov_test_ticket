package com.ennov.ticketApi.dto.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    List<SmallTicketDTO> tickets = new ArrayList<>();
}

package com.ennov.ticketApi.dto.response;

import com.ennov.ticketApi.dto.RoleDTO;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DetailsUserResponseDTO {
    private String username;
    private String email;
    private RoleDTO role;
    private Instant createdDate;
    private Instant lastModifiedDate;
}

package com.ennov.ticketApi.dto.response;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class DetailsTicketResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String statut;
    private UserResponseDTO createdUser;
    private UserResponseDTO assignUser;
    private Instant createdDate;
    private Instant lastModifiedDate;

}

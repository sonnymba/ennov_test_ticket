package com.ennov.ticketApi.dto;

import com.ennov.ticketApi.entities.BaseAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@ToString
@Entity
public class RoleDTO{
    private Long id;
    private String libelle;
    private Boolean isAdmin = false;
    private Instant createdDate;
    private Instant lastModifiedDate;
}

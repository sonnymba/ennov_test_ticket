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
public class RoleDTO{
    private Long id;
    private String name;
}

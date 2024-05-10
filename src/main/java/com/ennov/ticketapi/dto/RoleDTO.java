package com.ennov.ticketapi.dto;


import com.ennov.ticketapi.entities.Role;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RoleDTO{
    private Long id;
    private String name;

    public RoleDTO(Role role) {
        if(role != null) {
            this.id = role.getId();
            this.name = role.getName();
        }
    }
}

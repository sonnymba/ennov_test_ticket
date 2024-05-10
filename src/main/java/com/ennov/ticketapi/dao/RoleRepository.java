package com.ennov.ticketapi.dao;

import com.ennov.ticketapi.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);

    @Query("SELECT DISTINCT r FROM Role r WHERE r.name = :name")
    Optional<Role> getByName(String name);
}

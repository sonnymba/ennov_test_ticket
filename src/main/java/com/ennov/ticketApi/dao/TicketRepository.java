package com.ennov.ticketApi.dao;

import com.ennov.ticketApi.entities.Ticket;
import com.ennov.ticketApi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Ticket findByTitle(String title);
    @Query("SELECT DISTINCT t FROM Ticket t WHERE t.title = :title")
    Optional<Ticket> getByTitle(String title);
    List<Ticket> findByAssignedTo(User assignedUser);

}

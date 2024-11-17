package com.Ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ticket.model.TicketModel;



@Repository
public interface TicketRepository extends JpaRepository<TicketModel, Integer>{

}

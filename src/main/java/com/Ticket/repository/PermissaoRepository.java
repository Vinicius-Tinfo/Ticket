package com.Ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Ticket.model.PermissaoModel;



@Repository
public interface PermissaoRepository extends JpaRepository<PermissaoModel, Long>{

}

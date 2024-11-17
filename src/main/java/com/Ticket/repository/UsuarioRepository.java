package com.Ticket.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.Ticket.model.PermissaoModel;
import com.Ticket.model.UsuarioModel;



@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {
	
	UsuarioModel findByCpf(String cpf);

  
	
	
	
	  boolean existsByCpf(String cpf);
	  boolean existsByEmail(String email);
	  UsuarioModel getOneByCpf(String cpf);
	  List<UsuarioModel> findAllByPermissoesIn(List<PermissaoModel> permissoes, Sort nome);
	 
	  @Modifying
	  @Query(value ="update horario.usuarios "
	  		+ "set senha = ?1 "
	  		+ "where cpf=?2 ", nativeQuery = true)
	  @Transactional
	  void alterarSenha(String novaSenha, String cpf);
	  
	  
	
	  // Busca todos os usuários com suas permissões associadas
	    @Query("SELECT u FROM UsuarioModel u JOIN FETCH u.permissoes")
	    List<UsuarioModel> findAllUsuariosWithPermissoes();
	  
}
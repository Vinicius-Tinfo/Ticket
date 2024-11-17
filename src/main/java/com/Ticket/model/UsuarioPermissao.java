package com.Ticket.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "usuario_permissao", schema = "ticket")
public class UsuarioPermissao {


	@Id
    @Column(name = "usuario_id_usuario")
    private Long usuarioId;

	
    @Column(name = "permissao_id_permissao") 
    private long permissaoId; // 

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

	public long getPermissaoId() {
		return permissaoId;
	}

	public void setPermissaoId(long permissaoId) {
		this.permissaoId = permissaoId;
	}

	
}

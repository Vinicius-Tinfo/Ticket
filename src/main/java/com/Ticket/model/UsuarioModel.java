package com.Ticket.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "usuario", schema = "ticket")
public class UsuarioModel implements Serializable,UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id_usuario;


	@Column(name = "cpf") 
	private String cpf;

	@Column(name = "email") 
	private String email;
	
	@Column(name = "nome") 
	private String nome;

	@Column(name = "senha") 
	private String senha;
	
	@Column(name = "permissao") 
	private int permissao;
	
	@Column(name = "data_nascimento") 
	private Date data_nascimento;
		
	
	public Date getData_nascimento() {
		return data_nascimento;
	}
	public void setData_nascimento(Date data_nascimento) {
		this.data_nascimento = data_nascimento;
	}
	public int getPermissao() {
		return permissao;
	}
	public void setPermissao(int permissao) {
		this.permissao = permissao;
	}
	
	
	@Column(name = "data_expiracao") 
	private Date dataExpiracao;

	@ManyToMany
	@JoinTable(name="usuario_permissao",
    joinColumns={@JoinColumn(name="usuario_id_usuario", referencedColumnName = "id_usuario")},
    inverseJoinColumns={@JoinColumn(name="permissao_id_permissao", referencedColumnName = "id_permissao")})
	private List<PermissaoModel> permissoes = new ArrayList<>();
	
    
	
	public Long getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(Long id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public List<PermissaoModel> getPermissoes() {
		return permissoes;
	}
	public void setPermissoes(List<PermissaoModel> permissoes) {
		this.permissoes = permissoes;
	}
	
	public Date getDataExpiracao() {
		return dataExpiracao;
	}
	public void setDataExpiracao(Date dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.permissoes;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub	   
		return this.senha;
	   
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.cpf;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


}
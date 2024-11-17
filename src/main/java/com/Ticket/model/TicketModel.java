package com.Ticket.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
	@Table(name="ticket", schema = "ticket")
	public class TicketModel {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "id")
		private int id;
		
		@Column(name = "nome")
		private String nome;
		
		@Column(name = "email")
		private String email;

		@Column(name = "telefone")
		private String telefone;
		
		@Column(name = "titulo")
		private String titulo;
		
		@Column(name = "descricao")
		private String descricao;
		
		@Column(name = "resolucao")
		private String resolucao;

		public String getResolucao() {
			return resolucao;
		}

		public void setResolucao(String resolucao) {
			this.resolucao = resolucao;
		}

		public LocalDateTime getData_fechamento() {
			return data_fechamento;
		}

		public void setData_fechamento(LocalDateTime data_fechamento) {
			this.data_fechamento = data_fechamento;
		}

		@Column(name = "situacao")
		private boolean situacao;
		
	
		
		
		@Column(name = "data_criacao")
		private LocalDateTime data_criacao;
		
		@Column(name = "data_fechamento")
		private LocalDateTime data_fechamento;


		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getTelefone() {
			return telefone;
		}

		public void setTelefone(String telefone) {
			this.telefone = telefone;
		}

		public String getTitulo() {
			return titulo;
		}

		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public boolean isSituacao() {
			return situacao;
		}

		public void setSituacao(boolean situacao) {
			this.situacao = situacao;
		}

		public LocalDateTime getData_criacao() {
			return data_criacao;
		}

		public void setData_criacao(LocalDateTime data_criacao) {
			this.data_criacao = data_criacao;
		}	
	
}

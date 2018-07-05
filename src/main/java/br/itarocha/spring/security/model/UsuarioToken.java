package br.itarocha.spring.security.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity()
public class UsuarioToken {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Email(message = "Email inválido")
	@NotEmpty(message = "Email é obrigatório")
	private String email;
	
	@Length(min = 5, message = "A senha deve ter pelo menos 5 caracteres")
	@NotEmpty(message = "Senha é obrigatória")
	private String token;
	
	@NotNull(message="Data Hora da Criação é obrigatória")
	@Temporal(TemporalType.TIMESTAMP )
	@DateTimeFormat(pattern="dd/MM/yyyy hh:mm:ss")
	private Date dataHoraCriacao;
	
	@NotNull(message="Data Hora de Validade é obrigatória")
	@Temporal(TemporalType.TIMESTAMP )
	@DateTimeFormat(pattern="dd/MM/yyyy hh:mm:ss")
	private Date dataHoraValidade;
	
	@NotNull(message = "Status é obrigatório")
	private Boolean ativo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDataHoraCriacao() {
		return dataHoraCriacao;
	}

	public void setDataHoraCriacao(Date dataHoraCriacao) {
		this.dataHoraCriacao = dataHoraCriacao;
	}

	public Date getDataHoraValidade() {
		return dataHoraValidade;
	}

	public void setDataHoraValidade(Date dataHoraValidade) {
		this.dataHoraValidade = dataHoraValidade;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
}

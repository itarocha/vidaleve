package br.itarocha.tendavisitante.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="funcao_lideranca")
public class FuncaoLideranca {

	public FuncaoLideranca(){
	}
	
	public FuncaoLideranca(String descricao) {
		this.descricao = descricao;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="Descrição é obrigatória")
	@Size(max = 32, message="Descrição do perfil não pode ter mais que 32 caracteres")
	@NotNull(message="Descrição é não pode ser nula")
	private String descricao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

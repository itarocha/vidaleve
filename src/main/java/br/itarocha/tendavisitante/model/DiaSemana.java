package br.itarocha.tendavisitante.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="dia_semana")
public class DiaSemana {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OrderBy("numero")
	private int numero;
	
	//@Size(max = 32, message="Descrição nome não pode conter mais que 32 caracteres")
	//@OrderBy("descricao")
	@NotEmpty(message="Descrição é obrigatório")
	private String descricao;
	
	@NotEmpty(message="Descrição Reduzida é obrigatório")
	//@Size(min = 3, max = 3, message="Descrição Reduzida deve ter 3 caracteres")
	//@OrderBy("descricao")
	private String descricaoReduzida;
	
	public DiaSemana(){}
	
	public DiaSemana(int numero, String descricao, String descricaoReduzida){
		this.numero = numero;
		this.descricao = descricao;
		this.descricaoReduzida = descricaoReduzida;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricaoReduzida() {
		return descricaoReduzida;
	}

	public void setDescricaoReduzida(String descricaoReduzida) {
		this.descricaoReduzida = descricaoReduzida;
	}

	
}

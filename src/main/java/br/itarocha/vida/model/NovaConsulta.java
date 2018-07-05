package br.itarocha.vida.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class NovaConsulta {

	@NotNull(message="Código precisa ser preenchido")
	private Long id;
	
	@NotNull(message="Data de Agenda precisa ser preenchida")
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dataAgenda;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataAgenda() {
		return dataAgenda;
	}

	public void setDataAgenda(Date dataAgenda) {
		this.dataAgenda = dataAgenda;
	}

}

package br.itarocha.tendavisitante.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity(name="horario_culto")
public class HorarioCulto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne()
	@NotNull(message="Dia da Semana não pode ser nulo")
	private DiaSemana diaSemana;
	
	@Size(min=5, max = 5, message="O horário deve conter 5 caracteres")
	@Pattern(regexp="([01]?[0-9]|2[0-3]):[0-5][0-9]", message="Horário deve seguir o padrão HH:MM")
	@NotNull(message="Horário não pode ser nulo")
	private String horario;
	
	public HorarioCulto(){}
	
	public HorarioCulto(DiaSemana diaSemana, String horario){
		this.diaSemana = diaSemana;
		this.horario = horario;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public DiaSemana getDiaSemana() {
		return this.diaSemana;
	}

	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}
	
	@Transient
	public String getDescricao() {
		return String.format("%s - %s", 
				this.diaSemana == null ? "" : this.diaSemana.getDescricao(),
				this.getHorario());
	}
}

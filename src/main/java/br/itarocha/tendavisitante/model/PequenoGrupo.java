package br.itarocha.tendavisitante.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name="pequeno_grupo")
public class PequenoGrupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne()
	private Endereco endereco;

	//@NotEmpty(message="Responsável é obrigatório")
	@ManyToOne()
	private Pessoa responsavel;
	
	//@NotEmpty(message="Perfil é obrigatório")
	@ManyToOne()
	private PerfilPequenoGrupo perfil;
	
	//@NotEmpty(message="Dia da Semana é obrigatório")
	@ManyToOne()
	private DiaSemana diaSemana;
	
	@NotEmpty(message="Horário é obrigatório")
	@Size(min=5, max = 5, message="O horário não pode conter mais que 5 caracteres")
	@Pattern(regexp="([01]?[0-9]|2[0-3]):[0-5][0-9]")
	private String horario;
	

	public PequenoGrupo(){
		this.endereco = new Endereco();
	}
	
	public Pessoa getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Pessoa responsavel) {
		this.responsavel = responsavel;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public PerfilPequenoGrupo getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilPequenoGrupo perfil) {
		this.perfil = perfil;
	}

	public DiaSemana getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(DiaSemana diaSemana) {
		this.diaSemana = diaSemana;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}
}

package br.itarocha.tendavisitante.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.itarocha.spring.security.model.Usuario;

@Entity(name="visita")
public class Visita {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String culto;
	
	//@Valid
	//@OneToOne()
	//private Pessoa pessoa;

	@NotNull(message="Data da visita é obrigatória")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dataVisita;
		
	// Refatorar
	@Valid
	@ManyToOne(fetch=FetchType.EAGER)
	private HorarioCulto horarioCulto;
	
	private Boolean interessadoPg;
	
	private Boolean possuiAcompanhante;
	
	@Size(max = 64, message="Nome de Acompanhante deve ter no máximo 64 caracteres")
	private String nomeAcompanhante;
	  
	private Boolean disponivelSegunda;
	
	private Boolean disponivelTerca;
	
	private Boolean disponivelQuarta;
	
	private Boolean disponivelQuinta;
	
	private Boolean disponivelSexta;
	
	private Boolean disponivelSabado;
	
	private Boolean disponivelDomingo;
	
	private Boolean filhosMenores;
	
	private Integer idadeFilho1; 

	private Integer idadeFilho2; 
	
	private Integer idadeFilho3; 
	
	@Valid
	@ManyToOne()
	private Usuario usuarioCadastro;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraCadastro;	

	private StatusVisita status;
	
	public StatusVisita getStatus() {
		return status;
	}

	public void setStatus(StatusVisita status) {
		this.status = status;
	}

	public Date getDataHoraStatus() {
		return dataHoraStatus;
	}

	public void setDataHoraStatus(Date dataHoraStatus) {
		this.dataHoraStatus = dataHoraStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHoraStatus;	

	public Usuario getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(Usuario usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public Date getDataHoraCadastro() {
		return dataHoraCadastro;
	}

	public void setDataHoraCadastro(Date dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}

	public Visita(){
		//this.setPessoa(new Pessoa());
	}
	
	public Visita(String culto){
		this.culto = culto;
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCulto() {
		return culto;
	}


	public void setCulto(String culto) {
		this.culto = culto;
	}

	/*
	public Pessoa getPessoa() {
		return pessoa;
	}


	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	*/

	/*
	@Override
    public String toString() {
        return String.format("Visitante[id=%d, culto='%s', pessoa='%s']",id, culto, pessoa);
    }
	*/
	
	public Date getDataVisita() {
		return dataVisita;
	}

	public void setDataVisita(Date dataVisita) {
		this.dataVisita = dataVisita;
	}

	public HorarioCulto getHorarioCulto() {
		return this.horarioCulto;
	}

	public void setHorarioCulto(HorarioCulto horarioCulto) {
		this.horarioCulto = horarioCulto;
	}

	public Boolean getInteressadoPg() {
		return interessadoPg;
	}

	public void setInteressadoPg(Boolean interessadoPg) {
		this.interessadoPg = interessadoPg;
	}

	public Boolean getPossuiAcompanhante() {
		return possuiAcompanhante;
	}

	public void setPossuiAcompanhante(Boolean possuiAcompanhante) {
		this.possuiAcompanhante = possuiAcompanhante;
	}

	public String getNomeAcompanhante() {
		return nomeAcompanhante;
	}

	public void setNomeAcompanhante(String nomeAcompanhante) {
		this.nomeAcompanhante = nomeAcompanhante;
	}

	public Boolean getDisponivelSegunda() {
		return disponivelSegunda;
	}

	public void setDisponivelSegunda(Boolean disponivelSegunda) {
		this.disponivelSegunda = disponivelSegunda;
	}

	public Boolean getDisponivelTerca() {
		return disponivelTerca;
	}

	public void setDisponivelTerca(Boolean disponivelTerca) {
		this.disponivelTerca = disponivelTerca;
	}

	public Boolean getDisponivelQuarta() {
		return disponivelQuarta;
	}

	public void setDisponivelQuarta(Boolean disponivelQuarta) {
		this.disponivelQuarta = disponivelQuarta;
	}

	public Boolean getDisponivelQuinta() {
		return disponivelQuinta;
	}

	public void setDisponivelQuinta(Boolean disponivelQuinta) {
		this.disponivelQuinta = disponivelQuinta;
	}

	public Boolean getDisponivelSexta() {
		return disponivelSexta;
	}

	public void setDisponivelSexta(Boolean disponivelSexta) {
		this.disponivelSexta = disponivelSexta;
	}

	public Boolean getDisponivelSabado() {
		return disponivelSabado;
	}

	public void setDisponivelSabado(Boolean disponivelSabado) {
		this.disponivelSabado = disponivelSabado;
	}

	public Boolean getDisponivelDomingo() {
		return disponivelDomingo;
	}

	public void setDisponivelDomingo(Boolean disponivelDomingo) {
		this.disponivelDomingo = disponivelDomingo;
	}

	public Boolean getFilhosMenores() {
		return filhosMenores;
	}

	public void setFilhosMenores(Boolean filhosMenores) {
		this.filhosMenores = filhosMenores;
	}

	public Integer getIdadeFilho1() {
		return idadeFilho1;
	}

	public void setIdadeFilho1(Integer idadeFilho1) {
		this.idadeFilho1 = idadeFilho1;
	}

	public Integer getIdadeFilho2() {
		return idadeFilho2;
	}

	public void setIdadeFilho2(Integer idadeFilho2) {
		this.idadeFilho2 = idadeFilho2;
	}

	public Integer getIdadeFilho3() {
		return idadeFilho3;
	}

	public void setIdadeFilho3(Integer idadeFilho3) {
		this.idadeFilho3 = idadeFilho3;
	}	

}

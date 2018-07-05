package br.itarocha.vida.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.ColumnResult;

//import com.fasterxml.jackson.annotation.JsonFormat;

public class ConsultaGrafico implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Date dataAgenda;
	
	//@DateTimeFormat(pattern="dd/MM/yyyy")
	//@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataConsulta;
	
	private String realizada;

	private BigDecimal peso;
	
	private BigDecimal indiceMassaCorporal;	
	
	private BigDecimal pctGorduraCorporal;

	private BigDecimal pctMuscular;	
	
	private BigDecimal pctGorduraVisceral;	

	
	public ConsultaGrafico() {
		
	}

	public ConsultaGrafico(Long id, Date dataAgenda, Date dataConsulta, String realizada, BigDecimal peso, BigDecimal indiceMassaCorporal, 
			BigDecimal pctGorduraCorporal, BigDecimal pctMuscular, BigDecimal  pctGorduraVisceral) {
		this.id = id;
		this.dataAgenda = dataAgenda;
		this.dataConsulta = dataConsulta;
		this.realizada = realizada;
		this.peso = peso;
		this.indiceMassaCorporal = indiceMassaCorporal; 
		this.pctGorduraCorporal = pctGorduraCorporal;
		this.pctMuscular = pctMuscular;
		this.pctGorduraVisceral	= pctGorduraVisceral;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getPeso() {
		return peso;
	}
	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public Date getDataAgenda() {
		return dataAgenda;
	}
	public void setDataAgenda(Date dataAgenda) {
		this.dataAgenda = dataAgenda;
	}

	public Date getDataConsulta() {
		return dataConsulta;
	}
	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public String getRealizada() {
		return realizada;
	}

	public void setRealizada(String realizada) {
		this.realizada = realizada;
	}

	public BigDecimal getIndiceMassaCorporal() {
		return indiceMassaCorporal;
	}

	public void setIndiceMassaCorporal(BigDecimal indiceMassaCorporal) {
		this.indiceMassaCorporal = indiceMassaCorporal;
	}

	public BigDecimal getPctGorduraCorporal() {
		return pctGorduraCorporal;
	}

	public void setPctGorduraCorporal(BigDecimal pctGorduraCorporal) {
		this.pctGorduraCorporal = pctGorduraCorporal;
	}

	public BigDecimal getPctMuscular() {
		return pctMuscular;
	}

	public void setPctMuscular(BigDecimal pctMuscular) {
		this.pctMuscular = pctMuscular;
	}

	public BigDecimal getPctGorduraVisceral() {
		return pctGorduraVisceral;
	}

	public void setPctGorduraVisceral(BigDecimal pctGorduraVisceral) {
		this.pctGorduraVisceral = pctGorduraVisceral;
	}

	public String getDataAgendaFmt() {
		if (this.dataAgenda != null) {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yy");
			return dt.format(this.dataAgenda);
		} 
		return "??";
	}

	public String getDataConsultaFmt() {
		if (this.dataConsulta != null) {
			SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yy");
			return dt.format(this.dataConsulta);
		} 
		return "??";
	}
}

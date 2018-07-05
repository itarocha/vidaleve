package br.itarocha.vida.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsultaAgenda {
	private Long id;
	private Date data;
	private Date dataAgenda;	
	private Date dataConsulta;
	private String realizada;
	private Long clienteId;
	private String nome;
	private String telefone;

	public ConsultaAgenda(){}
	
	public ConsultaAgenda(Long id, Date data, Date dataAgenda, Date dataConsulta, String realizada, Long clienteId, String nome, String telefone){
		this.id = id;
		this.data = data;
		this.dataAgenda = dataAgenda;	
		this.dataConsulta = dataConsulta;
		this.realizada = realizada;
		this.clienteId = clienteId;
		this.nome = nome;
		this.telefone = telefone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public String getTitle() {
		return this.nome;
	}
	
	public boolean getCompleted() {
		return "S".equals(this.realizada);
	}
	
	public String getStart() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(this.data);
	}

	public String getColor() {
		return ( "S".equals(this.realizada)) ? "#66ff66" : "#ff9999"; 
	}

	public String getBorderColor() {
		return ("S".equals(this.realizada)) ? "#00cc00" : "#cc6600";
	}
	
	public Date getDataCalculada() {
		return "S".equals(this.realizada) ? this.dataConsulta : this.dataAgenda;
	}
	
}

package br.itarocha.vida.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.ConstructorResult;
import javax.persistence.ColumnResult;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

@Entity(name="consulta")
@SqlResultSetMappings({
	@SqlResultSetMapping(
			name = "ConsultaGraficoMapping",
			classes = @ConstructorResult(
					targetClass = ConsultaGrafico.class,
					columns = {
							@ColumnResult(name = "id", type = Long.class),
							@ColumnResult(name = "dataAgenda", type = Date.class),
							@ColumnResult(name = "dataConsulta", type = Date.class),
							@ColumnResult(name = "realizada", type = String.class),
							@ColumnResult(name = "peso", type = BigDecimal.class),
							@ColumnResult(name = "indiceMassaCorporal", type = BigDecimal.class),
							@ColumnResult(name = "pctGorduraCorporal", type = BigDecimal.class),
							@ColumnResult(name = "pctMuscular", type = BigDecimal.class),
							@ColumnResult(name = "pctGorduraVisceral", type = BigDecimal.class)
					})),	 
	
	@SqlResultSetMapping(
			name = "ConsultaAgendaMapping",
			classes = @ConstructorResult(
					targetClass = ConsultaAgenda.class,
					columns = {
							@ColumnResult(name = "id", type = Long.class),
							@ColumnResult(name = "data", type = Date.class),
							@ColumnResult(name = "dataAgenda", type = Date.class),
							@ColumnResult(name = "dataConsulta", type = Date.class),
							@ColumnResult(name = "realizada", type = String.class),
							@ColumnResult(name = "clienteId", type = Long.class),
							@ColumnResult(name = "nome", type = String.class),
							@ColumnResult(name = "telefone", type = String.class)
					}))	 
})
public class Consulta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	//@NotNull(message="Data de Agenda é obrigatório")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dataAgenda;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date dataConsulta;

	@NotNull(message="Peso é obrigatório")
	@Digits(integer=6, fraction=3)
	@Column(nullable=false, precision=6, scale=3) 
	@NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
	private BigDecimal peso;	
	
	@NotNull(message="Altura é obrigatório")
	private Integer altura;	

	@NotNull(message="Idade é obrigatório")
	private Integer idade;	

	@NotNull(message="Cintura é obrigatório")
	private Integer cintura;	

	@NotNull(message="Quadril é obrigatório")
	private Integer quadril;	
	
	@NotNull(message="Índice de Massa Corporal é obrigatório")
	@Digits(integer=5, fraction=2)
	@Column(nullable=false, precision=5, scale=2) 
	@NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
	private BigDecimal indiceMassaCorporal;	
	
	@NotNull(message="Percentual de Gordura Corporal é obrigatório")
	@Digits(integer=5, fraction=2)
	@Column(nullable=false, precision=5, scale=2) 
	@NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
	private BigDecimal pctGorduraCorporal;

	@NotNull(message="Percentual Muscular é obrigatório")
	@Digits(integer=5, fraction=2)
	@NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
	@Column(nullable=false, precision=5, scale=2) 
	private BigDecimal pctMuscular;	
	
	@NotNull(message="Percentual de Gordura Visceral é obrigatório")
	@Digits(integer=5, fraction=2)
	@Column(nullable=false, precision=5, scale=2) 
	@NumberFormat(style = Style.NUMBER, pattern = "#,##0.00")
	private BigDecimal pctGorduraVisceral;	
	
	@Size(max = 1)
	private String realizada;
	
	@Lob 
	@Basic(fetch=FetchType.LAZY)
	private String laudo;
	
	@Lob 
	@Basic(fetch=FetchType.LAZY)
	private String receituario;
	
	// Sempre tem que ter os dados do cliente 
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cliente_id")
	private Cliente cliente;

	
	@Transient
	private List<Consulta> outrasConsultas = new ArrayList<Consulta>();
	
	public List<Consulta> getOutrasConsultas(){
		return this.outrasConsultas;
	}
	
	public void setOutrasConsultas(List<Consulta> lista){
		this.outrasConsultas = lista;
	}
	
	public Consulta(){
		this.realizada = "N";
		this.idade = 1;
		this.altura = this.cliente != null ? this.cliente.getAltura() : 0;
		this.cintura = 1;
		this.quadril = 1;
		this.peso = BigDecimal.ZERO;
		this.indiceMassaCorporal = BigDecimal.ONE;
		this.pctGorduraCorporal = BigDecimal.ONE;
		this.pctGorduraVisceral = BigDecimal.ONE;
		this.pctMuscular = BigDecimal.ONE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataAgenda() {
		return this.dataAgenda;
	}

	public void setDataAgenda(Date dataAgenda) {
		this.dataAgenda = dataAgenda;
	}

	public Date getDataConsulta() {
		return this.dataConsulta;
	}

	public void setDataConsulta(Date dataConsulta) {
		this.dataConsulta = dataConsulta;
	}

	public BigDecimal getPeso() {
		return peso;
	}

	public void setPeso(BigDecimal peso) {
		this.peso = peso;
	}

	public Integer getAltura() {
		return altura;
	}

	public void setAltura(Integer altura) {
		this.altura = altura;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Integer getCintura() {
		return cintura;
	}

	public void setCintura(Integer cintura) {
		this.cintura = cintura;
	}

	public Integer getQuadril() {
		return quadril;
	}

	public void setQuadril(Integer quadril) {
		this.quadril = quadril;
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

	public String getRealizada() {
		return realizada;
	}

	public void setRealizada(String realizada) {
		this.realizada = realizada;
	}

	public String getLaudo() {
		return laudo;
	}

	public void setLaudo(String laudo) {
		this.laudo = laudo;
	}

	public String getReceituario() {
		return receituario;
	}

	public void setReceituario(String receituario) {
		this.receituario = receituario;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@DateTimeFormat(pattern="dd/MM/yyyy")
	public Date getDataCalculada() {
		return "S".equals(this.realizada) ? this.dataConsulta : this.dataAgenda;
	}

}

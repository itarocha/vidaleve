package br.itarocha.tendavisitante.model;

public enum EstadoCivil {

	SOLTEIRO("Solteiro"),
	CASADO("Casado"),
	DIVORCIADO("Divorciado"),
	UNIAO("União Estável"),
	VIUVO("Viúvo");
	
	private String descricao;
	
	EstadoCivil(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
}

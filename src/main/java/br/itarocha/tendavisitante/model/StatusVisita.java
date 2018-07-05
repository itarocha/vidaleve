package br.itarocha.tendavisitante.model;

public enum StatusVisita {
	
    SEM_INTERESSE("Sem Interesse"),
    AGUARDANDO_VINCULACAO_PG("Aguradando Vinculação PG"),
    AGUARDANDO_CONTATO_LIDER("Aguardando Contato com Líder"),
    AGUARDANDO_RETORNO_LIDER("Aguardando Retorno Líder"),
    ENCAMINHAMENTO_CANCELADO("Encaminhamento Cancelado"),
    ENCAMINHADO("Encaminhado"),
    NAO_ENCAMINHADO("Não Encaminhado");
	
	private String descricao;
	
	StatusVisita(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
}

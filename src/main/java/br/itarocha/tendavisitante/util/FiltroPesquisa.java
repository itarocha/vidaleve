package br.itarocha.tendavisitante.util;

import java.util.ArrayList;
import java.util.List;

import br.itarocha.tendavisitante.model.StatusVisita;

public class FiltroPesquisa {
	
	private String descricao;
	
	private List<StatusVisita> status = new ArrayList<StatusVisita>(); 

	public List<StatusVisita> getStatus() {
		return status;
	}

	public void setStatus(List<StatusVisita> status) {
		this.status = status;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getQuery() {
		return this.getDescricao() == null ? "" : "?descricao="+getDescricao();		
	}

}

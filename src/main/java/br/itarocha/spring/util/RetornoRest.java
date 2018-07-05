package br.itarocha.spring.util;

public class RetornoRest {

	private String retorno;
	private String mensagem;
	private Object data;

	public RetornoRest() {}
	
	public RetornoRest(String retorno, String mensagem) {
		this.retorno = retorno;
		this.mensagem = mensagem;
	}

	public RetornoRest(String retorno, String mensagem, Object data) {
		this.retorno = retorno;
		this.mensagem = mensagem;
		this.setData(data);
	}
	
	public String getRetorno() {
		return retorno;
	}
	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}

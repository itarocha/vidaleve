package br.itarocha.spring.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class Mensagens {

	@Autowired
	private MessageSource messageSource;

	public String getMensagemGravacaoSucesso(String nomeClasse) {
		String msg = messageSource.getMessage("mensagem.sucesso", null, Locale.US); // Locale("pt", "BR")
		return String.format(msg, nomeClasse); 
	}
	
}

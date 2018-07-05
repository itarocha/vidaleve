package br.itarocha.util.email;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl {

	@Autowired
	EmailService emailService;
	
	@Autowired
	MailGunService ms;

	/*
	private void sendTextMail() {

		String from = "pavan@localhost";
		String to = "itarocha@gmail.com";
		String subject = "Java Mail with Spring Boot - Plain Text";

		EmailTemplate template = new EmailTemplate("hello-world-plain.txt");

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("user", "Pavan");
		replacements.put("today", String.valueOf(new Date()));

		String message = template.getTemplate(replacements);

		Email email = new Email(from, to, subject, message);

		emailService.send(email);
	}
	*/
	
	public void redefinirSenha(String emailDestinatario, String nome, String token) {
		String url = "http://petrasistemas.com.br:8080/vittalev";
        String link = String.format("%s/criar-senha/%s/",url, token);
        
		String from  = "Petra Sistemas <postmaster@petrasistemas.com>";
		String to = emailDestinatario;
		String subject = "Criação de nova senha";

		EmailTemplate template = new EmailTemplate("redefinir-senha.html");

		Map<String, String> replacements = new HashMap<String, String>();
		replacements.put("usuario", nome);
		replacements.put("link", link);
		//replacements.put("today", String.valueOf(new Date()));

		String message = template.getTemplate(replacements);

		Email email = new Email(from, to, subject, message);
		email.setHtml(true);
		
		ms.sendHtmlMail(email);
	}

	 // https://github.com/ozimov/spring-boot-email-tools/blob/master/examples/sending-mime-email-with-thymeleaf-example/src/main/java/com/test/TestService.java
	 // http://www.opencodez.com/java/java-mail-framework-using-spring-boot.htm
	 // https://memorynotfound.com/spring-mail-sending-email-thymeleaf-html-template-example/
	 // https://stackoverflow.com/questions/33208788/thymeleaf-rich-html-email-error

}

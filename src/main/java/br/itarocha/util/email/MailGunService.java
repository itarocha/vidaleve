package br.itarocha.util.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Component
@PropertySource("classpath:env/mail.properties")
public class MailGunService {

	@Autowired
	private Environment env;
	
    public JsonNode sendHtmlMail(Email eParams) {
    	
    	String domainName = env.getProperty("mail.mailgun.domain.name");
    	String apiKey = env.getProperty("mail.mailgun.apikey"); 
    	
        HttpResponse<JsonNode> request = null;
		try {
			request = Unirest.post("https://api.mailgun.net/v3/" + domainName + "/messages")
			        .basicAuth("api", apiKey)
			        .queryString("from", eParams.getFrom())
			        //.queryString("to", eParams.getTo().toArray(new String[eParams.getTo().size()]))
			        .queryString("to", eParams.getTo())
			        //.queryString("to", "itarocha@gmail.com")
			        //.queryString("to", "beraldo.fernanda@gmail.com")
			        //.queryString("cc", "itarocha@gmail.com")
			        //.queryString("bcc", "joe@example.com")
			        .queryString("subject", eParams.getSubject())
			        //.queryString("text", "Testando Email Mailgun - Deu certo!")
			        .queryString("html", eParams.getMessage())
			        //.field("attachment", new File("/temp/folder/test.txt"))
			        .asJson();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
		System.out.println(eParams.getTo());
		System.out.println(request.getBody());

        return request.getBody();
    }
	
	
    /*
	// https://documentation.mailgun.com/en/latest/user_manual.html#sending-via-api
    public static JsonNode sendComplexMessage() {
    	
        HttpResponse<JsonNode> request = null;
		try {
			request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages")
			        .basicAuth("api", API_KEY)
			        .queryString("from", "Petra Sistemas <postmaster@petrasistemas.com>")
			        .queryString("to", "itarocha1972@gmail.com")
			        //.queryString("to", "itarocha@gmail.com")
			        //.queryString("to", "beraldo.fernanda@gmail.com")
			        //.queryString("cc", "itarocha@gmail.com")
			        //.queryString("bcc", "joe@example.com")
			        .queryString("subject", "Testando Envio Mail Gun")
			        //.queryString("text", "Testando Email Mailgun - Deu certo!")
			        .queryString("html", "<html><body>Testando: <strong>Itamar Rocha ama Fernanda Beraldo</strong> loucamente!!!!</body></html>")
			        //.field("attachment", new File("/temp/folder/test.txt"))
			        .asJson();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("RETORNANDO  "+request.getBody());
        return request.getBody();
    }
    */
	
}

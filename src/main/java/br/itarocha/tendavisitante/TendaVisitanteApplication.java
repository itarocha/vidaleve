package br.itarocha.tendavisitante;

import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

@SpringBootApplication
@ComponentScan(basePackages={"br.itarocha.spring",
							 "br.itarocha.util",
							 "br.itarocha.tendavisitante",
							 "br.itarocha.vida"})
@EntityScan(basePackages= {	"br.itarocha.spring.security.model", 
							"br.itarocha.spring.model", 
							"br.itarocha.tendavisitante.model",
							"br.itarocha.vida.model"})
@EnableJpaRepositories(basePackages= {	"br.itarocha.spring.repository",
										"br.itarocha.tendavisitante.repository",
										"br.itarocha.vida.repository"})
public class TendaVisitanteApplication {

	@Autowired
	private Carga carga;
	
	public static void main(String[] args) {
		SpringApplication.run(TendaVisitanteApplication.class, args);
	}
	
	@Bean
	public LocaleResolver localeResolver(){
		return new FixedLocaleResolver(new Locale("pt", "BR") );
	}
	
	@Configuration
	public static class MvcConfig extends WebMvcConfigurerAdapter{
		
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addRedirectViewController("/", "/visitantes");
		}
		
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	    	registry.addMapping("/**")
            	.allowedOrigins("*")
            	.allowedMethods("GET", "POST", "PATCH", "DELETE", "PUT", "OPTIONS","HEAD", "TRACE", "CONNECT")
            	.allowedHeaders("X-Requested-With,Content-Type,Accept,Origin");	    }	
	}
	
	@PostConstruct
	public void setup(){
		carga.popular();
	}
}


// $2a$10$aSiy6pKtsAl4c5QRCZQ.6.ssnCJoMt0Jr0u/x07iZai9rQAnAJoCC
// $2a$10$aqPDUVm9xWP7jYxuR05aheD6Bx76x9Y1Yvlyu8XxsH25wsCNrD3ym
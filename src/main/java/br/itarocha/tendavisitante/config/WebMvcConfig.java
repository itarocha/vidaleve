package br.itarocha.tendavisitante.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring.SpringTemplateEngine;
//import org.thymeleaf.spring.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring.view.ThymeleafViewResolver;
//import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

   private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		//messageSource.setBasename("locale/messages");
		messageSource.setBasename("mensagens");
		//messageSource.setBasenames("classpath:/messages", "classpath:/some-other-mvc-messages", "classpath:/another-projects/mvc-messages");
	    messageSource.setUseCodeAsDefaultMessage(true);
		//messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

}	
	


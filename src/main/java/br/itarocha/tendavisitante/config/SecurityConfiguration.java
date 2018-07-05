package br.itarocha.tendavisitante.config;

//import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.itarocha.spring.security.service.ComercialUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	//@Autowired
	//private DataSource dataSource;
	
	
	@Autowired
	private ComercialUserDetailsService ssUserDetailsService;
	
	//@Value("${spring.queries.users-query}")
	//private String usersQuery;
	
	//@Value("${spring.queries.roles-query}")
	//private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder
			.userDetailsService(ssUserDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
		
	}	
	
	
	/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
			jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}
	*/
	
	
	// tendavisitante
	// http://websystique.com/spring-security/spring-security-4-method-security-using-preauthorize-postauthorize-secured-el/
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/", "/criar-senha/**", "/esqueceu-a-senha/**", "/api/**").permitAll() // ver
				.antMatchers("/login").permitAll()
				.antMatchers("/faixasetarias/**", "/igrejas/**").hasAnyAuthority("VOLUNTARIO", "ROOT")
				.antMatchers("/voluntarios/**", "/visitantes/**").hasAnyAuthority("VOLUNTARIO", "ROOT") // deu certo
				.antMatchers("/clientes/**", "/consultas/**", "/agenda/**", "/changepassword").hasAnyAuthority("USUARIO", "ADMIN", "ROOT") // deu certo
				.antMatchers("/registration").hasAnyAuthority("ADMIN","ROOT")
				.antMatchers("/**").hasAnyAuthority("ROOT")
				.anyRequest().authenticated()
				//.antMatchers("/visitantes").access("hasRole('USER')")
				//.anyRequest().fullyAuthenticated()
				//.anyRequest().denyAll()
				//.anyRequest().permitAll()
				.and()
					.formLogin().loginPage("/login").failureUrl("/login?error=true")
					.defaultSuccessUrl("/clientes") // ISSO DEVE SER PADRONIZADO
					.usernameParameter("email")
					.passwordParameter("password")
				.and()
					.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/")
				.and().exceptionHandling()
					.accessDeniedPage("/access-denied");
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/fullcalendar/**", "/fonts/**", "/images/**");
	}

}

/*
http.csrf().disable()
.authorizeRequests()
	.antMatchers("/", "/home", "/about").permitAll()
	.antMatchers("/admin/**").hasAnyRole("ADMIN")
	.antMatchers("/user/**").hasAnyRole("USER")
	.anyRequest().authenticated()
.and()
.formLogin()
	.loginPage("/login")
	.permitAll()
	.and()
.logout()
	.permitAll()
	.and()
.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
*/
package br.itarocha.spring.security.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UsuarioCriarSenha {

	//@Column(name = "email")
	//@Email(message = "*Email inválido")
	//@NotEmpty(message = "Email é obrigatório")
	private String email;
	
	private String token;
	
	//@Column(name = "password")
	@Length(min = 5, message = "A senha deve ter pelo menos 5 caracteres")
	@NotEmpty(message = "Nova Senha é obrigatória")
	private String newPassword;
	
	//@Column(name = "password")
	@Length(min = 5, message = "A Senha de Confirmação deve ter pelo menos 5 caracteres")
	@NotEmpty(message = "A Senha de Confirmação é obrigatória")
	//@Transient
	private String passwordConfirmation;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	
}

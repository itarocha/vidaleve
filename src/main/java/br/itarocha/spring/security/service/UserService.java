package br.itarocha.spring.security.service;

import br.itarocha.spring.security.model.Usuario;

public interface UserService {
	public Usuario findUserByEmail(String email);
	public Usuario findUserByEmailAndPassword(String email, String password);
	public void saveUser(Usuario user);
	public Usuario findUserById(Long id);
	public void redefinirSenha(String string);
	//public void removerPorEmail(String email);
	
}

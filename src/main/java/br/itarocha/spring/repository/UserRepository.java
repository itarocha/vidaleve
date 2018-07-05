package br.itarocha.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.itarocha.spring.security.model.Usuario;

@Service
public interface UserRepository extends JpaRepository<Usuario, Long>{
	
	public Usuario findByEmail(String email);
	
	public Usuario findByEmailAndPassword(String email, String password);
	
}

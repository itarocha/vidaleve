package br.itarocha.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.itarocha.spring.security.model.UsuarioToken;

@Service
public interface UsuarioTokenRepository extends JpaRepository<UsuarioToken, Long>{
	
	public List<UsuarioToken> findByEmail(String email);
	
	public UsuarioToken findByEmailAndTokenAndAtivo(String email, String token, Boolean ativo);

	public UsuarioToken findByTokenAndAtivo(String token, Boolean ativo);
	
	@Transactional
    void deleteByEmail(String email);

}

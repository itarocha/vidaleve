package br.itarocha.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.itarocha.spring.security.model.Role;
import br.itarocha.spring.security.model.Usuario;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	public Role findByRole(String role);
	
	List<Role> findByUsuariosIn(Usuario usuario);

}

package br.itarocha.spring.security.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

import br.itarocha.spring.repository.RoleRepository;
import br.itarocha.spring.repository.UserRepository;
import br.itarocha.spring.security.model.Role;
import br.itarocha.spring.security.model.Usuario;
import br.itarocha.spring.security.model.UsuarioSistema;

@Component
public class ComercialUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository usuarios;

	@Autowired
	private RoleRepository roles;

	@Override
	public UserDetails loadUserByUsername(String username){
		Usuario usuario = usuarios.findByEmail(username);
		
		if (usuario == null) {
			//throw new UsernameNotFoundException("Usuário não encontrado!");
			//throw new Exception("Usuário não encontrado!");
		}
		
		return new UsuarioSistema(usuario.getId(), usuario.getName(), usuario.getEmail(), usuario.getPassword(), authorities(usuario));
	}
	
	public Collection<? extends GrantedAuthority> authorities(Usuario usuario) {
		return authorities(roles.findByUsuariosIn(usuario));
	}
	
	public Collection<? extends GrantedAuthority> authorities(List<Role> lista) {
		Collection<GrantedAuthority> auths = new ArrayList<>();
		
		//for (Grupo grupo: grupos) {
			//List<Role> lista = roles.findByUsuariosIn(usuario) .findByGruposIn(grupo);
			
			for (Role permissao: lista) {
				System.out.println( permissao.getRole() );
				auths.add(new SimpleGrantedAuthority(permissao.getRole()));
			}
		//}
		
		return auths;
	}
	
}
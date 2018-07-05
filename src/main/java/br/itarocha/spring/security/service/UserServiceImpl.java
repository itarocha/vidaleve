package br.itarocha.spring.security.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.itarocha.spring.repository.RoleRepository;
import br.itarocha.spring.repository.UserRepository;
import br.itarocha.spring.repository.UsuarioTokenRepository;
import br.itarocha.spring.security.model.Usuario;
import br.itarocha.spring.security.model.UsuarioToken;
import br.itarocha.util.email.MailServiceImpl;
import br.itarocha.vida.model.Cliente;
import br.itarocha.vida.model.ConsultaGrafico;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private MailServiceImpl mailService;	
	
	@Autowired
	private EntityManager em;
	
	private Usuario usuario;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UsuarioTokenRepository usuarioTokenService;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;	

	@Override
	public Usuario findUserByEmail(String email) {
		Usuario u = userRepository.findByEmail(email);
		this.usuario = u;
		return u;
	}
	
	@Override
	public Usuario findUserByEmailAndPassword(String email, String password) {
		Usuario u = userRepository.findByEmailAndPassword(email, password);
		this.usuario = u;
		return u;
	}

	@Override
	public void saveUser(Usuario user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	    user.setActive(1);
		userRepository.save(user);
	}

	@Override
	public Usuario findUserById(Long id) {
		return userRepository.findOne(id);
	}

	/*
	@Override
	public void removerPorEmail(String email){
		Query q = em.createQuery("delete from UsuarioToken u where u.email = :email");
		q.setParameter("email", email);
		q.executeUpdate();
		em.flush();
	}
	*/
	
	@Override
	public void redefinirSenha(String email) {
		String token = UUID.randomUUID().toString();
		Usuario u = userRepository.findByEmail(email);
		String nome = u != null ? u.getName() : "Sr(a). usuário(a)";
		
		UsuarioToken ut = new UsuarioToken();
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		Calendar cValidade = Calendar.getInstance();
		cValidade.setTime(c.getTime());
		cValidade.add(Calendar.HOUR, 6);
		
		ut.setEmail(email);
		ut.setToken(token);
		ut.setAtivo(true);
		ut.setDataHoraCriacao(c.getTime());
		ut.setDataHoraValidade(cValidade.getTime());
		usuarioTokenService.save(ut);
		
        mailService.redefinirSenha(email, nome, token);
	}	 
	 
}



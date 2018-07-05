package br.itarocha.spring.security.controller;

import java.util.Arrays;
import java.util.HashSet;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.itarocha.spring.repository.RoleRepository;
import br.itarocha.spring.repository.UsuarioTokenRepository;
import br.itarocha.spring.security.model.Role;
import br.itarocha.spring.security.model.Usuario;
import br.itarocha.spring.security.model.UsuarioCriarSenha;
import br.itarocha.spring.security.model.UsuarioMudarSenha;
import br.itarocha.spring.security.model.UsuarioSolicitarMudarSenha;
import br.itarocha.spring.security.model.UsuarioToken;
import br.itarocha.spring.security.service.UserService;
import br.itarocha.spring.service.Mensagens;
import br.itarocha.spring.util.ApiUtil;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UsuarioTokenRepository usuarioTokenService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private RoleRepository roles;

	@Autowired
	private Mensagens mensagens;	
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("security/login/login");
		return modelAndView;
	}
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		Usuario user = new Usuario();
		modelAndView.addObject("model", user);
		modelAndView.setViewName("security/login/registration");
		return modelAndView;
	}
		
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(	@Valid @ModelAttribute("model") Usuario user, 
										BindingResult bindingResult,
										final RedirectAttributes attributes) {
		ModelAndView modelAndView = new ModelAndView();

		if (!"".equals(user.getEmail())) {
			Usuario userExists = userService.findUserByEmail(user.getEmail());
			if (userExists != null) {
				ObjectError error = new ObjectError("email","Já existe um usuário registrado com o e-mail fornecido");
				bindingResult.addError(error);		
			}
		}
		
		if (ApiUtil.isNullOrEmpty(user.getPasswordConfirmation())) {
			ObjectError error = new ObjectError("passwordConfirmation","Confirmação da Senha deve ser preenchido");
			bindingResult.addError(error);		
		} else {
			if (!ApiUtil.isNullOrEmpty(user.getPassword()) && !user.getPassword().equals(user.getPasswordConfirmation()) ) {
				ObjectError error = new ObjectError("passwordConfirmation","A Confirmação da Senha deve ser idêntica a Senha");
				bindingResult.addError(error);		
			}			
		}
		
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("security/login/registration");
		} else {
			Role role = roles.findByRole("USUARIO");
			user.setRoles(new HashSet<Role>(Arrays.asList(role)));
			userService.saveUser(user);
			
			modelAndView.addObject("successMessage", "Usuário registrado com sucesso");
			modelAndView.addObject("model", new Usuario());
			modelAndView.setViewName("security/login/registration");
		}
		return modelAndView;
	}

	@RequestMapping(value="/esqueceu-a-senha", method = RequestMethod.GET)
	public ModelAndView forgotPassword(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("model", new UsuarioSolicitarMudarSenha());
		modelAndView.setViewName("security/login/esqueceu-a-senha");
		return modelAndView;
	}
		
	@RequestMapping(value = "/esqueceu-a-senha", method = RequestMethod.POST)
	public String forgotPasswordPost(	@Valid @ModelAttribute("model") UsuarioSolicitarMudarSenha user, 
										BindingResult bindingResult,
										final RedirectAttributes attributes) {
		ModelAndView modelAndView = new ModelAndView();

		if (!"".equals(user.getEmail())) {
			Usuario userExists = userService.findUserByEmail(user.getEmail());
			if (userExists == null) {
				ObjectError error = new ObjectError("email","Email não cadastrado");
				bindingResult.addError(error);		
			}
		}
		
		if (bindingResult.hasErrors()) {
			return "security/login/esqueceu-a-senha";
		} else {
			userService.redefinirSenha(user.getEmail());
			attributes.addFlashAttribute("mensagem", "Senha enviada para o email. Verifique!");
			modelAndView.addObject("model", new Usuario());
			return "redirect:/login";
		}
	}

	@RequestMapping(value="/criar-senha/{token}", method = RequestMethod.GET)
	public ModelAndView criarSenha(@PathVariable("token") String token) throws Exception{
		ModelAndView mv = new ModelAndView();
		
		UsuarioToken u = usuarioTokenService.findByTokenAndAtivo(token, true);
		if (u == null) {
			throw new Exception("Página não encontrada");
		}
		
		UsuarioCriarSenha user = new UsuarioCriarSenha();
		user.setEmail(u.getEmail());
		user.setToken(token);
		mv.addObject("model", user);
		mv.setViewName("security/login/criar-senha");
		return mv;
	}
	
	//Instruções para criação de nova senha enviadas!
	//Enviamos as instruções para a criação de uma nova senha para o e-mail itarocha@gmail.com.	
	@RequestMapping(value = "/criar-senha", method = RequestMethod.POST)
	public String redefinirSenha(	@Valid @ModelAttribute("model") UsuarioCriarSenha model, 
										BindingResult bindingResult,
										final RedirectAttributes attributes) {
		ModelAndView modelAndView = new ModelAndView();

		/*
		if (!"".equals(user.getEmail())) {
			Usuario userExists = userService.findUserByEmail(user.getEmail());
			if (userExists != null) {
				ObjectError error = new ObjectError("email","Já existe um usuário registrado com o e-mail fornecido");
				bindingResult.addError(error);		
			}
		}
		*/
		
		if (ApiUtil.isNullOrEmpty(model.getPasswordConfirmation())) {
			ObjectError error = new ObjectError("passwordConfirmation","Confirmação da Senha deve ser preenchido");
			bindingResult.addError(error);		
		} else {
			if (!ApiUtil.isNullOrEmpty(model.getNewPassword()) && !model.getNewPassword().equals(model.getPasswordConfirmation()) ) {
				ObjectError error = new ObjectError("passwordConfirmation","A Confirmação da Senha deve ser idêntica a Senha");
				bindingResult.addError(error);		
			}			
		}

		Usuario userExists = userService.findUserByEmail(model.getEmail() );
		if (userExists == null) {
			ObjectError error = new ObjectError("password","Usuário inexistente");
			bindingResult.addError(error);		
		}
		
		if (bindingResult.hasErrors()) {
			return "security/login/criar-senha";
		} else {
			userExists.setPassword(model.getNewPassword());
			userService.saveUser(userExists);
			UsuarioToken ut = usuarioTokenService.findByTokenAndAtivo(model.getToken(), true);
			if (ut != null) {
				usuarioTokenService.deleteByEmail(ut.getEmail());
			}
			attributes.addFlashAttribute("mensagem", "Senha alterada com sucesso!");
			return "redirect:/login";
		}
	}
	
	@RequestMapping("/changepassword")
	public ModelAndView alterarSenha(){
		ModelAndView mv = new ModelAndView("security/login/changepassword");
		mv.addObject("model", new UsuarioMudarSenha());
		return mv;
	}
	
	@PostMapping("/changepassword")
	public String alterarSenhaPost(	@Valid @ModelAttribute("model") UsuarioMudarSenha model, 
									BindingResult bindingResult, 
									final RedirectAttributes attributes)
	{
		Usuario userExists = null;
		if (!ApiUtil.isNullOrEmpty(model.getNewPassword()) && !model.getNewPassword().equals(model.getPasswordConfirmation()) ) {
			ObjectError error = new ObjectError("passwordConfirmation","A Confirmação da Senha deve ser idêntica a Nova Senha");
			bindingResult.addError(error);		
		}		
		
		// TODO MUDAR
		model.setEmail("itarocha@gmail.com");
		
		if (!bindingResult.hasErrors()) {
			if (!"".equals(model.getEmail())) {
				userExists = userService.findUserByEmail(model.getEmail() );
				if (userExists == null) {
					ObjectError error = new ObjectError("password","Usuário ou Senha inválida");
					bindingResult.addError(error);		
				} else {
					boolean passou = bCryptPasswordEncoder.matches(model.getPassword(), userExists.getPassword()); 
					if (!passou) {
						ObjectError error = new ObjectError("password","Usuário ou Senha inválida");
						bindingResult.addError(error);		
					}
				}
			}
		}
		
		if (bindingResult.hasErrors()){
			return "security/login/changepassword";
		} else {
			try{
				userExists.setPassword( model.getNewPassword() );
				userService.saveUser(userExists);
				attributes.addFlashAttribute("mensagem", mensagens.getMensagemGravacaoSucesso("Usuário"));
				return "redirect:/clientes";
			}catch(IllegalArgumentException e){
				return "security/login/changepassword";
			}
		}
		//return "security/login/changepassword";
	}
	
	/*
	@RequestMapping(value="/admin/home", method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Conteúdo disponível apenas para usuários com função de administrador");
		modelAndView.setViewName("admin/home");
		return modelAndView;
	}
	*/

}

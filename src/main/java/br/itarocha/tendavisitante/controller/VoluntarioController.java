package br.itarocha.tendavisitante.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.itarocha.spring.service.Mensagens;
import br.itarocha.spring.util.PageWrapper;
import br.itarocha.tendavisitante.model.EstadoCivil;
import br.itarocha.tendavisitante.model.FaixaEtaria;
import br.itarocha.tendavisitante.model.FuncaoLideranca;
import br.itarocha.tendavisitante.model.Sexo;
import br.itarocha.tendavisitante.model.UnidadeFederacao;
import br.itarocha.tendavisitante.model.Pessoa;
import br.itarocha.tendavisitante.service.FaixaEtariaService;
import br.itarocha.tendavisitante.service.FuncaoLiderancaService;
import br.itarocha.tendavisitante.service.PessoaService;
import br.itarocha.tendavisitante.util.FiltroPesquisa;

@Controller
@RequestMapping("voluntarios")
public class VoluntarioController {
	
	private static final String PAGINA = "voluntarios";
	private static final String PAGINA_INDEX = "voluntarios/index";
	private static final String PAGINA_EDIT = "voluntarios/edit";
	private static final String PAGINA_REDIRECT = "redirect:/voluntarios";
	private static final String NOME_CLASSE = "Voluntário";
	
	@Autowired
	private PessoaService service;
	
	@Autowired
	private FaixaEtariaService faixasEtariasService;
	
	@Autowired
	private FuncaoLiderancaService funcoes; 
	
	@Autowired
	private Mensagens mensagens;
	
	@RequestMapping
	public ModelAndView pesquisar(	Authentication auth, 
									@ModelAttribute("filtro") 
									FiltroPesquisa filtro,
									@PageableDefault(size=20, sort= {"nome"}) Pageable pageRequest)
	{
		/*
		//UsuarioSistema userDetails = (UsuarioSistema) auth.getPrincipal();
		//System.out.println("*** DESCRIÇÃO DO FILTRO"+filtro.getDescricao());
		//auth.getAuthorities();
		
		if ( userHasAuthority("ADMIN", auth) ) {
			///////// System.out.println("******************* O CARA É ADMIN ***************");
		};

		for(GrantedAuthority g : auth.getAuthorities() ) {
			/////////// System.out.println("******************* "+g.toString());
		}
		*/
		
		ModelAndView mv = new ModelAndView(PAGINA_INDEX);
		//PageWrapper<Pessoa> page = new PageWrapper<Pessoa>(service.filtrarVoluntarios(filtro, pageRequest), "/voluntarios");
		
		PageWrapper<Pessoa> page = new PageWrapper<Pessoa>(
				service.filtrarVoluntarios(filtro, pageRequest), 
				String.format("%s%s",PAGINA,filtro.getQuery()));
		
		mv.addObject("lista",page);
		return mv;
	}
	
	/*
	private boolean userHasAuthority(String authority, Authentication auth)
	{
	    for (GrantedAuthority grantedAuthority :  auth.getAuthorities()) {
	        if (authority.equals(grantedAuthority.getAuthority())) {
	            return true;
	        }
	    }
	    return false;
	}	
	*/
	
	@RequestMapping("/new")
	public ModelAndView novoVisitante(){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		Pessoa v = service.buildVoluntario();
		mv.addObject("model", v);
		return mv;
	}
	
	@PostMapping()
	public String salvarVisitante(	@Valid @ModelAttribute("model") Pessoa objeto, 
									BindingResult bindingResult, 
									final RedirectAttributes attributes)
	{
		if (bindingResult.hasErrors()){
			return PAGINA_EDIT;
		}
		try{
			service.gravarVoluntario(objeto);
			attributes.addFlashAttribute("mensagem", mensagens.getMensagemGravacaoSucesso(NOME_CLASSE));
			return PAGINA_REDIRECT;
		}catch(IllegalArgumentException e){
			return PAGINA_EDIT;
		}
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView editar(@PathVariable("id") Pessoa model){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		mv.addObject("model", model);
		return mv;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public String excluir(@PathVariable Long id, final RedirectAttributes attributes){
		service.excluir(id);
		attributes.addFlashAttribute("mensagem", "Voluntário excluído");
		return PAGINA_REDIRECT;
	}

	@ModelAttribute("todosSexo")
	public List<Sexo> todosSexo(){
		return Arrays.asList(Sexo.values());
	}
	
	@ModelAttribute("todosEstadoCivil")
	public List<EstadoCivil> todosEstadoCivil(){
		return Arrays.asList(EstadoCivil.values());
	}

	@ModelAttribute("todosUF")
	public List<UnidadeFederacao> todosUF(){
		return Arrays.asList(UnidadeFederacao.values());
	}
	
	@ModelAttribute("listaFaixasEtarias")
	public List<FaixaEtaria> listaFaixasEtarias(){
		return faixasEtariasService.findAll();
	}

	@ModelAttribute("listaFuncoes")
	public List<FuncaoLideranca> listaFuncoes(){
		return funcoes.findAll();
	}
}


//<td th:text="${#dates.format(item.dataVisita, 'dd/MM/yyyy')}"></td>
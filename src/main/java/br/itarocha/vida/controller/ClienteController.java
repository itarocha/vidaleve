package br.itarocha.vida.controller;

import java.util.Arrays;
import java.util.Collections;
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
import br.itarocha.tendavisitante.model.Sexo;
import br.itarocha.tendavisitante.model.UnidadeFederacao;
import br.itarocha.tendavisitante.util.FiltroPesquisa;
import br.itarocha.vida.model.Cliente;
import br.itarocha.vida.model.Consulta;
import br.itarocha.vida.model.ConsultaGrafico;
import br.itarocha.vida.service.ClienteService;
import br.itarocha.vida.service.ConsultaService;

@Controller
@RequestMapping("clientes")
public class ClienteController {
	
	private static final String PAGINA = "clientes";
	private static final String PAGINA_INDEX = "clientes/index";
	private static final String PAGINA_EDIT = "clientes/edit";
	private static final String PAGINA_CONSULTAS = "consultas/index";
	private static final String PAGINA_REDIRECT = "redirect:/clientes";
	private static final String NOME_CLASSE = "Cliente";
	
	@Autowired
	private ClienteService service;

	@Autowired
	private ConsultaService consultasService;
	
	@Autowired
	private Mensagens mensagens;
	
	@RequestMapping
	public ModelAndView pesquisar(	Authentication auth, 
									@ModelAttribute("filtro") 
									FiltroPesquisa filtro,
									@PageableDefault(size=20, sort= {"nome"}) Pageable pageRequest)
	{
		ModelAndView mv = new ModelAndView(PAGINA_INDEX);
		
		PageWrapper<Cliente> page = new PageWrapper<Cliente>(
				service.filtrar(filtro, pageRequest), 
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
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		Cliente v = new Cliente();
		mv.addObject("model", v);
		return mv;
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView editar(@PathVariable("id") Cliente model){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		mv.addObject("model", model);
		return mv;
	}
	
	@PostMapping()
	public String salvar(	@Valid @ModelAttribute("model") Cliente objeto, 
							BindingResult bindingResult, 
							final RedirectAttributes attributes)
	{
		if (bindingResult.hasErrors()){
			return PAGINA_EDIT;
		}
		try{
			service.gravar(objeto);
			attributes.addFlashAttribute("mensagem", mensagens.getMensagemGravacaoSucesso(NOME_CLASSE));
			return PAGINA_REDIRECT;
		}catch(IllegalArgumentException e){
			return PAGINA_EDIT;
		}
	}
	
	@RequestMapping("/consultas/{id}")
	public ModelAndView consultas(@PathVariable("id") Cliente model){
		ModelAndView mv = new ModelAndView(PAGINA_CONSULTAS);
		List<Consulta> consultas = consultasService.buscarConsultasPorCliente(model);
		
		consultas.sort((c1, c2) -> c1.getDataCalculada().compareTo(c2.getDataCalculada()));
		
		List<ConsultaGrafico> grafico = consultasService.getGrafico(model);

		mv.addObject("graficoData", grafico);
		mv.addObject("model", model);
		mv.addObject("consultas", consultas);
		return mv;
	}

	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public String excluir(@PathVariable Long id, final RedirectAttributes attributes){
		service.excluir(id);
		attributes.addFlashAttribute("mensagem", "Cliente excluído");
		return PAGINA_REDIRECT;
	}

	@RequestMapping(value="/consultas/delete/{id}", method=RequestMethod.DELETE)
	public String excluirConsulta(@PathVariable Long id, final RedirectAttributes attributes){
		Consulta c = consultasService.findById(id);
		
		if (c != null) {
			//System.out.println(c.getId() + " - " +c.getDataAgenda());
			consultasService.excluir(id);
			attributes.addFlashAttribute("mensagem", "Consulta excluída");
			String retorno = String.format("redirect:/clientes/consultas/%d", c.getCliente().getId());
			System.out.println(retorno);
			return retorno;
		} else {
			attributes.addFlashAttribute("mensagem", "Consulta não encontrada");
			return "redirect:/clientes";
		}
	}
	
	@ModelAttribute("todosSexo")
	public List<Sexo> todosSexo(){
		return Arrays.asList(Sexo.values());
	}
	
	@ModelAttribute("todosUF")
	public List<UnidadeFederacao> todosUF(){
		return Arrays.asList(UnidadeFederacao.values());
	}
	
}


//<td th:text="${#dates.format(item.dataVisita, 'dd/MM/yyyy')}"></td>
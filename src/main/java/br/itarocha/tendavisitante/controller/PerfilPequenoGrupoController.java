package br.itarocha.tendavisitante.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
import br.itarocha.tendavisitante.model.PerfilPequenoGrupo;
import br.itarocha.tendavisitante.service.PerfilPequenoGrupoService;
import br.itarocha.tendavisitante.util.FiltroPesquisa;

@Controller
@RequestMapping("perfispequenosgrupos")
public class PerfilPequenoGrupoController {
	
	private static final String PAGINA = "perfispequenosgrupos";
	private static final String PAGINA_INDEX = "perfispequenosgrupos/index";
	private static final String PAGINA_EDIT = "perfispequenosgrupos/edit";
	private static final String PAGINA_REDIRECT = "redirect:/perfispequenosgrupos";
	private static final String NOME_CLASSE = "Perfil de Pequeno Grupo";
	
	@Autowired
	private PerfilPequenoGrupoService service;

	@Autowired
	private Mensagens mensagens;
	
	@RequestMapping
	public ModelAndView pesquisar(
			@ModelAttribute("filtro") FiltroPesquisa filtro, 
			@PageableDefault(size=5) Pageable pageRequest)
	{
		ModelAndView mv = new ModelAndView(PAGINA_INDEX);
		PageWrapper<PerfilPequenoGrupo> page = new PageWrapper<PerfilPequenoGrupo>(
				service.filtrar(filtro, pageRequest), 
				String.format("%s%s",PAGINA,filtro.getQuery()));
		mv.addObject("lista",page);
		return mv;
	}
	
	@RequestMapping("/new")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		PerfilPequenoGrupo obj = new PerfilPequenoGrupo();
		mv.addObject("model", obj);
		return mv;
	}

	@PostMapping()
	public String salvar(
			@Valid @ModelAttribute("model") PerfilPequenoGrupo objeto, 
			BindingResult result, 
			final RedirectAttributes attributes)
	{
		if (result.hasErrors()){
			return PAGINA_EDIT;
		}
		try{
			service.salvar(objeto);
			attributes.addFlashAttribute("mensagem", mensagens.getMensagemGravacaoSucesso(NOME_CLASSE));
			return PAGINA_REDIRECT;
		}catch(IllegalArgumentException e){
			return PAGINA_EDIT;
		}
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView editar(@PathVariable("id") PerfilPequenoGrupo model){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		mv.addObject("model", model);
		return mv;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes){
		service.excluir(id);
		attributes.addFlashAttribute("mensagem", "Perfil de Pequeno Grupo excluído com sucesso");
		return PAGINA_REDIRECT;
	}
}

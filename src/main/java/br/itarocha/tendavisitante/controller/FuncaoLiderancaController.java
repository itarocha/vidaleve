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
import br.itarocha.tendavisitante.model.FuncaoLideranca;
import br.itarocha.tendavisitante.service.FuncaoLiderancaService;
import br.itarocha.tendavisitante.util.FiltroPesquisa;

@Controller
@RequestMapping("funcoeslideranca")
public class FuncaoLiderancaController {
	
	private static final String PAGINA = "funcoeslideranca";
	private static final String PAGINA_INDEX = "funcoeslideranca/index";
	private static final String PAGINA_EDIT = "funcoeslideranca/edit";
	private static final String PAGINA_REDIRECT = "redirect:/funcoeslideranca";
	private static final String NOME_CLASSE = "Função de Liderança";
	
	@Autowired
	private FuncaoLiderancaService service;
	
	@Autowired
	private Mensagens mensagens;
	
	@RequestMapping
	public ModelAndView pesquisar(
			@ModelAttribute("filtro") FiltroPesquisa filtro, 
			@PageableDefault(size=10) Pageable pageRequest)
	{
		ModelAndView mv = new ModelAndView(PAGINA_INDEX);
		PageWrapper<FuncaoLideranca> page = new PageWrapper<FuncaoLideranca>(
				service.filtrar(filtro, pageRequest), 
				String.format("%s%s",PAGINA,filtro.getQuery()));
		mv.addObject("lista",page);
		return mv;
	}

	@RequestMapping("/new")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		FuncaoLideranca obj = new FuncaoLideranca();
		mv.addObject("model", obj);
		return mv;
	}
	
	@PostMapping()
	public String salvar(
			@Valid @ModelAttribute("model") FuncaoLideranca objeto, 
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
	public ModelAndView editar(@PathVariable("id") FuncaoLideranca model){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		mv.addObject("model", model);
		return mv;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public String excluir(@PathVariable Long id, final RedirectAttributes attributes){
		service.excluir(id);
		attributes.addFlashAttribute("mensagem", "Função de Liderança excluída com sucesso");
		return PAGINA_REDIRECT;
	}
	
}

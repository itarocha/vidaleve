package br.itarocha.tendavisitante.controller;

import java.util.List;
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
import br.itarocha.tendavisitante.model.DiaSemana;
import br.itarocha.tendavisitante.model.HorarioCulto;
import br.itarocha.tendavisitante.repository.DiaSemanaRepository;
import br.itarocha.tendavisitante.service.HorarioCultoService;
import br.itarocha.tendavisitante.util.FiltroPesquisa;

@Controller
@RequestMapping("horarios")
public class HorarioCultoController {
	
	private static final String PAGINA = "horarios";
	private static final String PAGINA_INDEX = "horarios/index";
	private static final String PAGINA_EDIT = "horarios/edit";
	private static final String PAGINA_REDIRECT = "redirect:/horarios";
	private static final String NOME_CLASSE = "Horário de Culto";

	@Autowired
	private HorarioCultoService service;

	@Autowired
	private DiaSemanaRepository dias;

	@Autowired
	private Mensagens mensagens;	
	
	@RequestMapping
	public ModelAndView pesquisar(
			@ModelAttribute("filtro") FiltroPesquisa filtro, 
			@PageableDefault(size=10) Pageable pageRequest)
	{
		ModelAndView mv = new ModelAndView(PAGINA_INDEX);
		PageWrapper<HorarioCulto> page = new PageWrapper<HorarioCulto>(
				service.filtrar(filtro, pageRequest), 
				String.format("%s%s",PAGINA,filtro.getQuery()));
		mv.addObject("lista",page);
		return mv;
	}
	
	@RequestMapping("/new")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		HorarioCulto obj = new HorarioCulto();
		mv.addObject("model", obj);
		return mv;
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView editar(@PathVariable("id") HorarioCulto model){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		mv.addObject("model", model);
		return mv;
	}
	
	@PostMapping()
	public String salvar(
			@Valid @ModelAttribute("model") HorarioCulto objeto, 
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
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes){
		service.excluir(id);
		attributes.addFlashAttribute("mensagem", "Horário de Culto excluído com sucesso");
		return PAGINA_REDIRECT;
	}
	
	@ModelAttribute("listaDias")
	public List<DiaSemana> todosDias(){
		return dias.findAll();
	}
	
}

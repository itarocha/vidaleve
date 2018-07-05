package br.itarocha.tendavisitante.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.itarocha.spring.util.CampoPesquisa;
import br.itarocha.spring.util.PageWrapper;
import br.itarocha.spring.util.Pesquisa;
import br.itarocha.tendavisitante.model.DiaSemana;
import br.itarocha.tendavisitante.model.PequenoGrupo;
import br.itarocha.tendavisitante.model.PerfilPequenoGrupo;
import br.itarocha.tendavisitante.model.Pessoa;
import br.itarocha.tendavisitante.model.UnidadeFederacao;
import br.itarocha.tendavisitante.repository.DiaSemanaRepository;
import br.itarocha.tendavisitante.service.PequenoGrupoService;
import br.itarocha.tendavisitante.service.PerfilPequenoGrupoService;
import br.itarocha.tendavisitante.service.PessoaService;
import br.itarocha.tendavisitante.util.GeolocationBuilder;

@Controller
@RequestMapping("pequenosgrupos")
public class PequenoGrupoController {
	
	private static final String PG = "pequenosgrupos";
	private static final String PG_INDEX = "pequenosgrupos/index";
	private static final String PG_EDIT = "pequenosgrupos/edit";
	
	@Autowired
	private PequenoGrupoService service;

	@Autowired
	private PessoaService voluntarios;

	@Autowired
	private PerfilPequenoGrupoService perfis;

	@Autowired
	private DiaSemanaRepository dias;

	@RequestMapping
	public ModelAndView pesquisarArray(@ModelAttribute("pesquisa") Pesquisa pesquisa,
								       @PageableDefault(size=20) Pageable pageRequest) {
		
		ModelAndView mv = new ModelAndView(PG_INDEX);
		
		/*
		for (LinhaPesquisa p : pesquisa.getFiltro()) {
			if (p.getTipo() == "list") {
				p.setExpressao(p.getExpressaolista());
				p.setComplemento(p.getComplementolista());
			} 
		}
		*/
		
		PageWrapper<PequenoGrupo> page = new PageWrapper<PequenoGrupo>(service.filtrar(pesquisa, pageRequest), "/pequenosgrupos");
		
		mv.addObject("lista",page);
		mv.addObject("pesquisa",pesquisa);
		//mv.addObject("pesquisa",new Pesquisa());
		return mv;
	}
	
	@RequestMapping("/new")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(PG_EDIT);
		PequenoGrupo obj = new PequenoGrupo();
		mv.addObject("pequenogrupo", obj);
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated PequenoGrupo model, BindingResult bindingResult, RedirectAttributes attributes){
		
		if (bindingResult.hasErrors()){
			return PG_EDIT;
		}
		try{
			GeolocationBuilder.resolve(model.getEndereco());
			service.salvar(model);
			attributes.addFlashAttribute("mensagem", "Salvo com sucesso");
			return "redirect:/"+PG;
		}catch(IllegalArgumentException e){
			System.out.println("ERRO: "+e.getMessage());
			return PG_EDIT;
		}
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView editar(@PathVariable("id") PequenoGrupo model){
		ModelAndView mv = new ModelAndView(PG_EDIT);
		mv.addObject("pequenogrupo", model);
		return mv;
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	public String excluir(@PathVariable Long id, RedirectAttributes attributes){
		service.excluir(id);
		attributes.addFlashAttribute("mensagem", "Pequeno Grupo excluído com sucesso");
		return "redirect:/"+PG;
	}

	@ModelAttribute("todosVoluntarios")
	public List<Pessoa> todosVoluntarios(){
		return voluntarios.getListaVoluntarios();
	}

	@ModelAttribute("todosUF")
	public List<UnidadeFederacao> todosUF(){
		return Arrays.asList(UnidadeFederacao.values());
	}

	@ModelAttribute("listaPerfis")
	public List<PerfilPequenoGrupo> listaPerfis(){
		return perfis.findAll();
	}

	@ModelAttribute("listaDias")
	public List<DiaSemana> todosDias(){
		return dias.findAll();
	}
	
	// https://stackoverflow.com/questions/28874135/dynamic-spring-data-jpa-repository-query-with-arbitrary-and-clauses
	
	// http://www.baeldung.com/rest-api-search-language-spring-data-querydsl
	@ModelAttribute("camposPesquisa")
	public List<CampoPesquisa> listaCamposPesquisa(){
		return service.getListaCamposPesquisa();
	}
}

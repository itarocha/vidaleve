package br.itarocha.tendavisitante.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.itarocha.tendavisitante.filter.VisitanteFilter;
import br.itarocha.tendavisitante.model.EstadoCivil;
import br.itarocha.tendavisitante.model.HorarioCulto;
import br.itarocha.tendavisitante.model.Sexo;
import br.itarocha.tendavisitante.model.Visita;
import br.itarocha.tendavisitante.service.PessoaService;

@RestController
@RequestMapping("/api/visitantes")
public class ApiVisitanteController {
	
	private static final String CADASTRO_VISITANTE = "CadastroVisitante";
	private static final String PESQUISA_VISITANTE = "PesquisaVisitante";
	
	@Autowired
	private PessoaService visitanteService;

	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> salvar(@RequestBody Visita visitante){
		//System.out.println("Nome: "+visitante.getNome());
		
		/*
		if (errors.hasErrors()){
			return CADASTRO_VISITANTE;
		}
		*/
		
		try{
			//visitanteService.salvar(visitante);
			//attributes.addFlashAttribute("mensagem", "Salvo com sucesso");
			//return "redirect:/api/visitantes/novo";
			return new ResponseEntity<Visita>(visitante, HttpStatus.OK);
		}catch(Exception e){
			//errors.rejectValue("dataVencimento",null, e.getMessage());
			//return CADASTRO_VISITANTE;
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/novo")
	public ModelAndView novo(){
		
		ModelAndView mv = new ModelAndView(CADASTRO_VISITANTE);
		//mv.addObject("titulo", new Titulo());
		
		return mv;
	}
	
	@RequestMapping
	public List<Visita> pesquisar(@ModelAttribute("filtro") VisitanteFilter filtro){
		//List<Visitante> listagem = visitanteService.filtrar(filtro);
		
		List<Visita> listagem = new ArrayList<Visita>();
		Visita v = new Visita();
		//v.setNome("Itamar Rocha");
		//v.setSexo(Sexo.MASCULINO);
		//v.setEstadoCivil(EstadoCivil.CASADO);
		listagem.add(v);
		return listagem;
	}
	
	
	@RequestMapping("{codigo}")
	public ModelAndView editar(@PathVariable("codigo") Visita visitante){
		ModelAndView mv = new ModelAndView(CADASTRO_VISITANTE);
		mv.addObject("visitante", visitante);
		return mv;
	}
	
	/*
	@RequestMapping(value="{codigo}", method=RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes){
		visitanteService.excluir(codigo);
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso");
		return "redirect:/api/visitantes";
	}
	*/
	
	@ModelAttribute("todosHorarioCulto")
	public List<HorarioCulto> todosHorarioCulto(){
		//return Arrays.asList(HorarioCulto.values());
		return null;
	}
	
}

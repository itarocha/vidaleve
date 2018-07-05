package br.itarocha.vida.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.itarocha.spring.service.Mensagens;
import br.itarocha.tendavisitante.model.UnidadeFederacao;
import br.itarocha.vida.model.Cliente;
import br.itarocha.vida.model.Consulta;
import br.itarocha.vida.model.ConsultaGrafico;
import br.itarocha.vida.repository.ConsultaRepository;
import br.itarocha.vida.service.ConsultaService;

@Controller
@RequestMapping("consultas")
public class ConsultaController {
	
	//private static final String PAGINA = "consultas";
	//private static final String PAGINA_INDEX = "consultas/index";
	private static final String PAGINA_EDIT = "consultas/edit";
	//private static final String PAGINA_CONSULTAS = "consultas/index";
	//private static final String PAGINA_REDIRECT = "redirect:/consultas";
	private static final String NOME_CLASSE = "Consulta";
	
	@Autowired
	private ConsultaService service;
	
	@Autowired
	private Mensagens mensagens;
	
	
	@RequestMapping("/edit/{id}")
	public ModelAndView editar(@PathVariable("id") Consulta model){
		ModelAndView mv = new ModelAndView(PAGINA_EDIT);
		model.setOutrasConsultas(this.datasConsultas(model.getCliente(), model.getId(), true));
		mv.addObject("model", model);
		//mv.addObject("consultas", this.datasConsultas(model.getCliente()));
		return mv;
	}

	@PostMapping()
	public String salvar(@Valid @ModelAttribute("model") Consulta objeto, 
			BindingResult bindingResult, 
			final RedirectAttributes attributes)
	{
		Long idCliente = (objeto.getCliente() != null) ? objeto.getCliente().getId() : 0;
		
		if (objeto.getPeso() != null && objeto.getPeso().doubleValue() <= 0) {
			ObjectError error = new ObjectError("peso","Peso deve ser maior que 0");
			bindingResult.addError(error);		
		}

		if (objeto.getPctGorduraCorporal() != null && objeto.getPctGorduraCorporal().doubleValue() >= 100) {
			ObjectError error = new ObjectError("pctGorduraCorporal","Percentual de gordura corporal deve ser inferior a 100%");
			bindingResult.addError(error);		
		}
		
		if (objeto.getPctMuscular() != null && objeto.getPctMuscular().doubleValue() >= 100) {
			ObjectError error = new ObjectError("pctMuscular","Percentual muscular deve ser inferior a 100%");
			bindingResult.addError(error);		
		}
		
		if (objeto.getPctGorduraVisceral() != null && objeto.getPctGorduraVisceral().doubleValue() >= 100) {
			ObjectError error = new ObjectError("pctGorduraVisceral","Percentual de gordura visceral deve ser inferior a 100%");
			bindingResult.addError(error);		
		}

		if ( "S".equals(objeto.getRealizada()) && objeto.getDataConsulta() == null ) {
			ObjectError error = new ObjectError("dataConsulta","Data de Consulta deve ser preenchida quando Realizada");
			bindingResult.addError(error);		
		}
		
		if (bindingResult.hasErrors()){
			objeto.setOutrasConsultas(this.datasConsultas(objeto.getCliente(), objeto.getId(), true));
			return PAGINA_EDIT;
		}
		
		if ( !"S".equals(objeto.getRealizada()) ) {
			objeto.setDataConsulta(null);
		}
		
		try{
			service.gravar(objeto, objeto.getCliente());
			attributes.addFlashAttribute("mensagem", mensagens.getMensagemGravacaoSucesso(NOME_CLASSE));
			return String.format("redirect:/clientes/consultas/%d",idCliente);
		}catch(IllegalArgumentException e){
			return PAGINA_EDIT;
		}
	}

	private List<Consulta> datasConsultas(Cliente c, Long consultaExceto, boolean removerNaoRealizadas){
		List<Consulta> consultas = service.buscarConsultasPorCliente(c);
		consultas.sort((c1, c2) -> c1.getDataCalculada().compareTo(c2.getDataCalculada()));
		consultas.removeIf(cns -> consultaExceto.equals(cns.getId()));
		if (removerNaoRealizadas) {
			consultas.removeIf(cns -> !"S".equals(cns.getRealizada()));
		}
		return consultas;
	}
}


//<td th:text="${#dates.format(item.dataVisita, 'dd/MM/yyyy')}"></td>
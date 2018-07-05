package br.itarocha.vida.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.itarocha.spring.util.RetornoRest;
import br.itarocha.vida.model.Consulta;
import br.itarocha.vida.model.ConsultaAgenda;
import br.itarocha.vida.model.NovaConsulta;
import br.itarocha.vida.service.ClienteService;
import br.itarocha.vida.service.ConsultaService;

@RestController
@RequestMapping("api/consulta")
public class ApiConsultaController {

	@Autowired
	private ClienteService clientes;
	
	@Autowired
	private ConsultaService consultasService;
	
	@PostMapping("/new")
	public RetornoRest novaConsulta(	@Valid @ModelAttribute("model") NovaConsulta model, 
									BindingResult bindingResult)
	{
		if (bindingResult.hasErrors()){
			String objeto = bindingResult.getAllErrors().get(0).getObjectName();
			return new RetornoRest("ERRO",bindingResult.getAllErrors().get(0).getDefaultMessage());
		}
		try{
			Consulta c = clientes.novaConsulta(model);
			return new RetornoRest("SUCESSO","Consulta gravada com sucesso. Código "+c.getId());
		}catch(IllegalArgumentException e){
			return new RetornoRest("ERRO",e.getMessage());
		} catch (Exception e) {
			return new RetornoRest("ERRO",e.getMessage());
		}
	}
	
	//@RequestMapping("/eventos")
	//public List<ConsultaAgenda> eventos(@RequestParam String start, @RequestParam String end, HttpSession session){
	
	
	@CrossOrigin()
	@GetMapping("/info/{id}")
	public RetornoRest getInfo(@PathVariable Long id) {
		Consulta c = consultasService.findById(id);
		// Para prevenir de loop eterno
		c.setCliente(null);
		// {"retorno":"SUCESSO","mensagem":"Consulta encontrada com sucesso.","data":{"id":121,"nome":"Itamar","idade":45,"situacao":false}}
		return new RetornoRest("SUCESSO","Consulta encontrada com sucesso.",c);
	}
	
	private static class Retorno{
		public Integer id;
		public String nome;
		public Integer idade;
		public boolean situacao;
	} 
	
	
}

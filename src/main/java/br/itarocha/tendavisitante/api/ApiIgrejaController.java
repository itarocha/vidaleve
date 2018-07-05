package br.itarocha.tendavisitante.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.itarocha.spring.error.EntityNotFoundException;
import br.itarocha.tendavisitante.model.FaixaEtaria;
import br.itarocha.tendavisitante.service.FaixaEtariaService;

@RestController
@RequestMapping("/api/faixaetaria")
public class ApiIgrejaController {

	@Autowired
	private FaixaEtariaService svc;
	
    @GetMapping(value = "/{id}")
    public FaixaEtaria getById(@PathVariable("id") Long id) throws EntityNotFoundException {
        return svc.getById(id);
    }	
}

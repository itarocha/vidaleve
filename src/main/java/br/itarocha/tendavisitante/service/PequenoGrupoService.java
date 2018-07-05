package br.itarocha.tendavisitante.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import br.itarocha.spring.util.CampoPesquisa;
import br.itarocha.spring.util.ItemOpcao;
import br.itarocha.spring.util.LinhaPesquisa;
import br.itarocha.spring.util.Pesquisa;
import br.itarocha.tendavisitante.model.DiaSemana;
import br.itarocha.tendavisitante.model.Endereco;
import br.itarocha.tendavisitante.model.PequenoGrupo;
import br.itarocha.tendavisitante.model.PerfilPequenoGrupo;
import br.itarocha.tendavisitante.model.Pessoa;
import br.itarocha.tendavisitante.repository.DiaSemanaRepository;
import br.itarocha.tendavisitante.repository.EnderecoRepository;
import br.itarocha.tendavisitante.repository.PequenoGrupoRepository;
import br.itarocha.tendavisitante.util.FiltroPesquisa;

@Service
public class PequenoGrupoService {

	private final String CAMPO_DIA = "dia.descricao";
	private final String CAMPO_RESPONSAVEL = "responsavel.nome";
	private final String CAMPO_BAIRRO = "endereco.bairro";
	private final String CAMPO_PERFIL = "perfil.descricao";
	private final String CAMPO_HORARIO = "horario";
	
	@Autowired
	private DiaSemanaRepository diasRepository;
	
	@Autowired
	private PequenoGrupoRepository repositorio;
	
	@Autowired
	private EnderecoRepository enderecos;
	
	public PequenoGrupo salvar(PequenoGrupo pg){
		try{
			enderecos.save(pg.getEndereco());
			return repositorio.save(pg);
		}catch(Exception e){
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public PequenoGrupo salvar(Pessoa p, DiaSemana dia, String horario, PerfilPequenoGrupo perfil){
		
		 //Predicate predicate = perfil.getDescricao().equalsIgnoreCase("dave");
					//.and(perfil.getDescricao().startsWith("mathews");

				//userRepository.findAll(predicate);
		//repositorio.findAll
		
		try{
			Endereco ep = p.getEndereco();
			Endereco e = new Endereco(ep.getLogradouro(), ep.getNumero(), ep.getComplemento(), ep.getBairro(), ep.getCep(), ep.getCidade(), ep.getUf());
			PequenoGrupo pg = new PequenoGrupo();
			pg.setResponsavel(p);
			pg.setDiaSemana(dia);
			pg.setHorario(horario);
			pg.setPerfil(perfil);
			pg.setEndereco(e);
			enderecos.save(e);
			return repositorio.save(pg);
		}catch(Exception e){
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public void excluir(Long id) {
		repositorio.delete(id);
	}
	
	public Page<PequenoGrupo> filtrar(FiltroPesquisa filtro, Pageable pageRequest) {
		
		//repositorio.findAll
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		Page<PequenoGrupo> lista = repositorio.findByEnderecoBairroContainingOrderByResponsavelNome(descricao, pageRequest);
		//Page<PequenoGrupo> lista = repositorio.findAll(spec, pageRequest); //repositorio.findByEnderecoBairroContainingOrderByResponsavelNome(descricao, pageRequest);
		return lista;
	}

	public Page<PequenoGrupo> filtrar(Pesquisa pesquisa, Pageable pageRequest) {
		Page<PequenoGrupo> lista = null;
		Specification<PequenoGrupo> theSpec = null;
		int i = 0;
		for(LinhaPesquisa filtro : pesquisa.getFiltro()) {
		
			String expressao = filtro.getExpressao();
			String complemento = filtro.getComplemento();
			if ("list".equalsIgnoreCase(filtro.getTipo()) ) {
				expressao = filtro.getExpressaolista();
				complemento = filtro.getComplementolista();
			}
			
			if (expressao != null) {
				i++;
				Specification<PequenoGrupo> spec = null;		
				if ( CAMPO_DIA.equals(filtro.getCampo())) {
					int dIni = 0;
					if (expressao != null && !expressao.isEmpty()) {
						DiaSemana diaIni = diasRepository.findFirstByDescricao(expressao);
						dIni = diaIni != null ? diaIni.getNumero() : 0;
					}
					int dFim = 0;
					if (complemento != null && !complemento.isEmpty()) {
						DiaSemana diaFim = diasRepository.findFirstByDescricao(complemento);
						dFim = diaFim != null ? diaFim.getNumero() : 0;
					}
					spec  = PequenoGrupoSpecification.filtroDiaSemana(filtro.getOperador(), dIni, dFim);
				} else
				if ( CAMPO_BAIRRO.equals(filtro.getCampo())) {
					spec  = PequenoGrupoSpecification.filtroBairro(filtro.getOperador(), expressao, complemento);
				} else
				if ( CAMPO_PERFIL.equals(filtro.getCampo())) {
					spec  = PequenoGrupoSpecification.filtroPerfil(filtro.getOperador(), expressao, complemento);
				} else
				if ( CAMPO_HORARIO.equals(filtro.getCampo())) {
					spec  = PequenoGrupoSpecification.filtroHorario(filtro.getOperador(), expressao, complemento);
				} else
				if ( CAMPO_RESPONSAVEL.equals(filtro.getCampo())) {
					spec  = PequenoGrupoSpecification.filtroResponsavelNome(filtro.getOperador(), expressao, complemento);
				} 
				
				theSpec = (i == 1) ? Specifications.where(spec) :  Specifications.where(theSpec).and(spec);
			}
		}
		lista = repositorio.findAll(theSpec, pageRequest); //repositorio.findByEnderecoBairroContainingOrderByResponsavelNome(descricao, pageRequest);
		
		return lista;
	}

	public List<CampoPesquisa> getListaCamposPesquisa() {
		
		List<CampoPesquisa> campos = new ArrayList<CampoPesquisa>();

		campos.add(new CampoPesquisa(CAMPO_RESPONSAVEL,"text", "Nome do Responsável"));
		campos.add(new CampoPesquisa(CAMPO_BAIRRO,"text", "Bairro"));
		campos.add(new CampoPesquisa(CAMPO_PERFIL,"text", "Perfil"));
		
		List<ItemOpcao> lista = new ArrayList<ItemOpcao>();
		lista.add(new ItemOpcao("Domingo", "Domingo"));
		lista.add(new ItemOpcao("Segunda", "Segunda"));
		lista.add(new ItemOpcao("Terça", "Terça"));
		lista.add(new ItemOpcao("Quarta", "Quarta"));
		lista.add(new ItemOpcao("Quinta", "Quinta"));
		lista.add(new ItemOpcao("Sexta", "Sexta"));
		lista.add(new ItemOpcao("Sábado", "Sábado"));
		campos.add(new CampoPesquisa(CAMPO_DIA, "list", "Dia da Semana", lista));
		campos.add(new CampoPesquisa(CAMPO_HORARIO, "time", "Horário"));
		
		return campos;
	}

}

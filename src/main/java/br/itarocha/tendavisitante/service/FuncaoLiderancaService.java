package br.itarocha.tendavisitante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.itarocha.tendavisitante.model.FuncaoLideranca;
import br.itarocha.tendavisitante.repository.FuncaoLiderancaRepository;
import br.itarocha.tendavisitante.util.FiltroPesquisa;

@Service
public class FuncaoLiderancaService {
	
	@Autowired
	private FuncaoLiderancaRepository repositorio;
	
	public void salvar(FuncaoLideranca ppg){
		try{
			repositorio.save(ppg);
		}catch(Exception e){
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public void excluir(Long id) {
		repositorio.delete(id);
	}
	
	public Page<FuncaoLideranca> filtrar( FiltroPesquisa filtro, Pageable pageRequest) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		
		return repositorio.findByDescricaoContainingOrderByDescricaoAsc(descricao, pageRequest);
	}
	
	public List<FuncaoLideranca> findAll(){
		return repositorio.findAll();
	}
}

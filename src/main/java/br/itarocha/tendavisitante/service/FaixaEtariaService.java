package br.itarocha.tendavisitante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.itarocha.spring.error.EntityNotFoundException;
import br.itarocha.tendavisitante.model.FaixaEtaria;
import br.itarocha.tendavisitante.repository.FaixaEtariaRepository;
import br.itarocha.tendavisitante.util.FiltroPesquisa;

@Service
public class FaixaEtariaService {
	
	@Autowired
	private FaixaEtariaRepository repositorio;
	
	public Page<FaixaEtaria> filtrar(FiltroPesquisa filtro, Pageable pageRequest) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		Page<FaixaEtaria> lista = repositorio.findByDescricaoContainingOrderByDescricaoAsc(descricao, pageRequest);
		return lista;
	}
	
    public FaixaEtaria getById(Long id) throws EntityNotFoundException {
        FaixaEtaria bird = repositorio.findOne(id);
        if(bird == null){
            throw new EntityNotFoundException(FaixaEtaria.class, "id", id.toString());
        }
        return bird;
    }
    
	public void salvar(FaixaEtaria model){
		try{
			repositorio.save(model);
		}catch(Exception e){
			//throw new IllegalArgumentException(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	
	public void excluir(Long codigo) {
		try{
			repositorio.delete(codigo);
		} catch(Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}	
	}
	
	public List<FaixaEtaria> findAll(){
		return repositorio.findAll();
	}
	
}

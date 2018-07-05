package br.itarocha.tendavisitante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.itarocha.tendavisitante.model.HorarioCulto;
import br.itarocha.tendavisitante.repository.HorarioCultoRepository;
import br.itarocha.tendavisitante.util.FiltroPesquisa;

@Service
public class HorarioCultoService {
	
	@Autowired
	private HorarioCultoRepository repositorio;
	
	public Page<HorarioCulto> filtrar(FiltroPesquisa filtro, Pageable pageRequest) {
		//String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		//Page<HorarioCulto> lista = repositorio.findAllOrderByDiaHorarioAsc(pageRequest);
		Page<HorarioCulto> lista = repositorio.findAll(pageRequest);
		return lista;
	}
	
	public void salvar(HorarioCulto model){
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
	
	public List<HorarioCulto> findAll(){
		return repositorio.findAll();
	}
	
}

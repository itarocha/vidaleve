package br.itarocha.tendavisitante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.itarocha.tendavisitante.model.PerfilPequenoGrupo;
import br.itarocha.tendavisitante.repository.PerfilPequenoGrupoRepository;
import br.itarocha.tendavisitante.util.FiltroPesquisa;

@Service
public class PerfilPequenoGrupoService {
	
	@Autowired
	private PerfilPequenoGrupoRepository repositorio;
	
	public void salvar(PerfilPequenoGrupo ppg){
		try{
			repositorio.save(ppg);
		}catch(Exception e){
			//throw new IllegalArgumentException(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	public void excluir(Long id) {
		repositorio.delete(id);
	}
	
	public Page<PerfilPequenoGrupo> filtrar(FiltroPesquisa filtro, Pageable pageRequest) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		Page<PerfilPequenoGrupo> lista = repositorio.findByDescricaoContainingOrderByDescricaoAsc(descricao, pageRequest);
		return lista;
	}
	
	public List<PerfilPequenoGrupo> findAll(){
		return repositorio.findAll();
	}
}


/*
 
{
  "codigo": 2,
  "nome": "Itamar Rocha",
  "endereco": "Av. Imbaúba, 1400 Bloco 5 Ap 204",
  "bairro": "Imbaúba",
  "cidade": "Uberlândia",
  "uf": "MG",
  "telefone1": "34984214666",
  "telefone2": null,
  "email": "itarocha@gmail.com",
  "horarioCulto": null,
  "sexo": "MASCULINO",
  "estadoCivil": "CASADO",
  
  "interessadoPg": true,
  "nomeAcompanhante": null,
  "possuiAcompanhante": false,
  "diaSegunda": true,
  "diaTerca": true,
  "diaQuarta": true,
  "diaQuinta": false,
  "diaSexta": true,
  "diaSabado": false,
  "filhosMenores": false
  "idadeFilho1": 0,
  "idadeFilho2": 0,
  "idadeFilho3": 0,
}  
 * */
 
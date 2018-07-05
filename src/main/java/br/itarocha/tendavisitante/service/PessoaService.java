package br.itarocha.tendavisitante.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.itarocha.geo.Geolocation;
import br.itarocha.geo.LatLng;
import br.itarocha.tendavisitante.filter.TituloFilter;
import br.itarocha.tendavisitante.model.Endereco;
import br.itarocha.tendavisitante.model.EstadoCivil;
import br.itarocha.tendavisitante.model.FaixaEtaria;
import br.itarocha.tendavisitante.model.FuncaoLideranca;
import br.itarocha.tendavisitante.model.PerfilPequenoGrupo;
import br.itarocha.tendavisitante.model.Pessoa;
import br.itarocha.tendavisitante.model.Sexo;
import br.itarocha.tendavisitante.model.StatusVisita;
import br.itarocha.tendavisitante.model.Visita;
import br.itarocha.tendavisitante.repository.EnderecoRepository;
import br.itarocha.tendavisitante.repository.VisitaRepository;
import br.itarocha.tendavisitante.repository.PerfilPequenoGrupoRepository;
import br.itarocha.tendavisitante.repository.PessoaRepository;
import br.itarocha.tendavisitante.util.FiltroPesquisa;
import br.itarocha.tendavisitante.util.GeolocationBuilder;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoas;
	
	@Autowired
	private EnderecoRepository enderecos;
	
	@Autowired
	private VisitaRepository visitas;
	
	public void gravarVisitante(Pessoa model){
		try{
			model.setVisitante(true);
			resolveEndereco(model);
			enderecos.save(model.getEndereco());
			visitas.save(model.getVisita());
			pessoas.save(model);
			//repositorio.save(model);
		}catch(Exception e){
			//throw new IllegalArgumentException(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	private void resolveEndereco(Pessoa model) {
		GeolocationBuilder.resolve(model.getEndereco());
		String endereco = model.getEndereco().toString();
		LatLng latlng = Geolocation.getGeoLocation(endereco);
		model.getEndereco().setLatitude(latlng.getLat());
		model.getEndereco().setLongitude(latlng.getLng());
	}

	public void excluir(Long codigo) {
		pessoas.delete(codigo);
	}
	
	
	public Page<Pessoa> filtrarVisitantes(FiltroPesquisa filtro, Collection<StatusVisita> status, Pageable pageRequest) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		//Page<Pessoa> lista = pessoas.findAll(pageRequest);// .findByPessoaNomeContaining(descricao, pageRequest);
		Page<Pessoa> lista = null;
		if (status.size() > 0) {
			lista = pessoas.findByVisitanteAndVoluntario(true, false, status, pageRequest);// .findByPessoaNomeContaining(descricao, pageRequest);	
		} else {
			lista = pessoas.findByVisitanteAndVoluntario(true, false, pageRequest);// .findByPessoaNomeContaining(descricao, pageRequest);
		}
		
		return lista;
	}

	public Page<Pessoa> filtrarVoluntarios(FiltroPesquisa filtro, Pageable pageRequest) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		//Page<Pessoa> lista = pessoas.findVoluntarios(filtro.getDescricao(), pageRequest);// .findByPessoaNomeContaining(descricao, pageRequest);
		//Page<Pessoa> lista = pessoas.findAllWhereVisitanteEqualsTrue(filtro.getDescricao(), pageRequest);// .findByPessoaNomeContaining(descricao, pageRequest);
		Collection<StatusVisita> status = new ArrayList<StatusVisita>();
		status.add(StatusVisita.AGUARDANDO_CONTATO_LIDER);
		
		Page<Pessoa> lista = pessoas.findByVoluntarioOrderByNome(true, pageRequest);
		//Page<Pessoa> lista = pessoas.findByVoluntario(false, true, pageRequest);// .findByPessoaNomeContaining(descricao, pageRequest);
		return lista;
	}

	public List<Pessoa> getListaVoluntarios() {
		//List<Pessoa> lista = new ArrayList<Pessoa>();
		//List<Pessoa> lista = pessoas.findByVoluntarioOrderByNome(true);// .findByPessoaNomeContaining(descricao, pageRequest);
		
		return pessoas.findByVoluntarioOrderByNome(true);
		//return lista;
	}

	
	public Pessoa buildVisitante() {
		Pessoa v = new Pessoa();
		v.setVisita(new Visita());
		v.getVisita().setInteressadoPg(true);
		v.getVisita().setDataVisita(Calendar.getInstance().getTime());
		return v;
	}

	public Pessoa buildVoluntario() {
		Pessoa v = new Pessoa();
		return v;
	}

	public Pessoa buildVoluntario(String nome, String email, String tel1, String tel2, EstadoCivil es, FaixaEtaria fe, FuncaoLideranca fl, Sexo sx, Endereco endereco) {
		Pessoa p = this.buildVoluntario();
		
		p.setNome(nome);
		p.setEmail(email);
		p.setEstadoCivil(es);
		p.setFaixaEtaria(fe);
		p.setFuncaoLideranca(fl);
		p.setSexo(sx);
		p.setTelefone1(tel1);
		p.setTelefone2(tel2);
		p.setEndereco(endereco);
		
		return p;
	}
	
	public Pessoa gravarVoluntario(Pessoa model) {
		try{
			model.setVoluntario(true);
			resolveEndereco(model);
			enderecos.save(model.getEndereco());
			return pessoas.save(model);
			//repositorio.save(model);
		}catch(Exception e){
			//throw new IllegalArgumentException(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	/*
	public List<Igreja> filtrar(VisitanteFilter filtro) {
		String nome = filtro.getNome() == null ? "%" : filtro.getNome();
		List<Igreja> todos = igrejas.findByNomeContaining(nome);
		return todos;
	}
	*/
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
  "nomeAcompanhante": null,
  "idadeFilho1": 0,
  "idadeFilho2": 0,
  "idadeFilho3": 0,
  "possuiAcompanhante": false,
  "interessadoPg": true,
  "diaSegunda": true,
  "diaTerca": true,
  "diaQuarta": true,
  "diaQuinta": false,
  "diaSexta": true,
  "diaSabado": false,
  "filhosMenores": false
}  
 * */
 
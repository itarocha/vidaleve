package br.itarocha.vida.service;

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
import br.itarocha.tendavisitante.model.Endereco;
import br.itarocha.tendavisitante.model.Pessoa;
import br.itarocha.tendavisitante.model.Sexo;
import br.itarocha.tendavisitante.repository.EnderecoRepository;
import br.itarocha.tendavisitante.util.FiltroPesquisa;
import br.itarocha.tendavisitante.util.GeolocationBuilder;
import br.itarocha.vida.model.Cliente;
import br.itarocha.vida.model.Consulta;
import br.itarocha.vida.model.NovaConsulta;
import br.itarocha.vida.repository.ClienteRepository;
import br.itarocha.vida.repository.ConsultaRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clientes;
	
	@Autowired
	private ConsultaRepository consultas;
	
	@Autowired
	private EnderecoRepository enderecos;
	
 	public void gravar(Cliente model){
		try{
			resolveEndereco(model);
			enderecos.save(model.getEndereco());
			//cliente.save(model.getVisita());
			clientes.save(model);
			//repositorio.save(model);
		}catch(Exception e){
			//throw new IllegalArgumentException(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	private void resolveEndereco(Cliente model) {
		GeolocationBuilder.resolve(model.getEndereco());
		String endereco = model.getEndereco().toString();
		LatLng latlng = Geolocation.getGeoLocation(endereco);
		model.getEndereco().setLatitude(latlng.getLat());
		model.getEndereco().setLongitude(latlng.getLng());
	}

	public void excluir(Long codigo) {
		clientes.delete(codigo);
	}

	public Consulta novaConsulta(NovaConsulta model) throws Exception{
		Cliente cli = clientes.getOne(model.getId());
		Consulta cons = new Consulta();
		cons.setCliente(cli);
		cons.setDataAgenda(model.getDataAgenda());
		cons.setAltura(cli.getAltura());
		cons.setRealizada("N");
		consultas.save(cons);
		return cons;
	}
	
	public Page<Cliente> filtrar(FiltroPesquisa filtro, Pageable pageRequest) {
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		Page<Cliente> lista = clientes.findByNomeContainingOrderByNome(descricao, pageRequest);
		return lista;
	}

	public List<Consulta> buscarConsultasPorCliente(Cliente cliente) {
		List<Consulta> lista = consultas.findByClienteOrderByDataConsultaDesc(cliente);
		return lista;
	}
/*

	public List<Pessoa> getListaVoluntarios() {
		return pessoas.findByVoluntarioOrderByNome(true);
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
*/	
}
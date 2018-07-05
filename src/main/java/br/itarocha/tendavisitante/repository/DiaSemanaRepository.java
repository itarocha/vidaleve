package br.itarocha.tendavisitante.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.itarocha.tendavisitante.model.DiaSemana;

public interface DiaSemanaRepository extends JpaRepository<DiaSemana, Long> {
	
	//public List<DiaSemana> findAllOrderByNumero();

	//public List<DiaSemana> findAll();

	public DiaSemana findFirstByNumero(int valor);
	
	public DiaSemana findFirstByDescricaoReduzida(String valor);

	public DiaSemana findFirstByDescricao(String valor);

}

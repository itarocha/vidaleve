package br.itarocha.vida.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.itarocha.vida.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	public Page<Cliente> findByNomeContainingOrderByNome(String nome, Pageable pageable);	

}

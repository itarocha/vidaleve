package br.itarocha.vida.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.itarocha.vida.model.Cliente;
import br.itarocha.vida.model.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
	
	public Page<Consulta> findByClienteNomeContainingOrderByDataConsultaDesc(String nome, Pageable pageable);		

	public List<Consulta> findByClienteOrderByDataConsultaDesc(Cliente cliente);		
	
	//@Query("select p from pessoa p where (p.visitante = ?1 and p.voluntario = ?2) and ( (p.visita is null) or ((p.visita != null) and (p.visita.status in ?3)) ) order by p.nome")
	//public Page<Pessoa> findByVisitanteAndVoluntario(Boolean isVisitante, Boolean isVoluntario, Collection<StatusVisita> status, Pageable pageable);

	@Query("select c from consulta c where (c.cliente = ?1 and c.realizada = ?2 ) order by c.dataConsulta")
	public List<Consulta> findByClienteAndRealizada(Cliente cliente, String realizada);
	
	@Query("select c from consulta c where (c.id = ?1)")
	public Consulta findById(Long id);
	
}

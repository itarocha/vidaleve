package br.itarocha.tendavisitante.repository;

import java.util.Collection;
import java.util.List;

import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.itarocha.tendavisitante.model.Pessoa;
import br.itarocha.tendavisitante.model.StatusVisita;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	//////////public List<Visitante> findByPessoaNomeContaining(String descricao, Sort sort);
	//@Query("select p from Pessoa p where p.visitante = true order by p.nome")
	//
	@Query("select p from pessoa p where (p.visitante = ?1 and p.voluntario = ?2) and ( (p.visita is null) or ((p.visita != null) and (p.visita.status in ?3)) ) order by p.nome")
	public Page<Pessoa> findByVisitanteAndVoluntario(Boolean isVisitante, Boolean isVoluntario, Collection<StatusVisita> status, Pageable pageable);

	@Query("select p from pessoa p where (p.visitante = ?1 and p.voluntario = ?2 and p.visita.status = -1 ) order by p.nome")
	public Page<Pessoa> findByVisitanteAndVoluntario(Boolean isVisitante, Boolean isVoluntario, Pageable pageable);

	public Page<Pessoa> findByVoluntarioOrderByNome(Boolean isVoluntario, Pageable pageable);
	
	//@NamedQuery(name="Pessoa.findVisitante", query="select p from Pessoas p where p.visitante = true order by p.nome")
	public List<Pessoa> findByVoluntarioOrderByNome(Boolean isVoluntario);

	//public Page<Pessoa> findByVoluntarioOrderByNome(Boolean isVoluntario);
	
	//In
	//findByAgeIn(Collection<Age> ages)
	//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
	

	//@Query("select p from Pessoas p where p.visitante = true order by p.nome")
	//public Page<Pessoa> findVoluntarios(String nome, Pageable pageable);


}

/*

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.firstname like %?1")
  List<User> findByFirstnameEndsWith(String firstname);
}
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?1", nativeQuery = true)
  User findByEmailAddress(String emailAddress);
}
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname")
  User findByLastnameOrFirstname(@Param("lastname") String lastname,
                                 @Param("firstname") String firstname);
}
@Modifying
@Query("update User u set u.firstname = ?1 where u.lastname = ?2")
int setFixedFirstnameFor(String firstname, String lastname);  
 
 */
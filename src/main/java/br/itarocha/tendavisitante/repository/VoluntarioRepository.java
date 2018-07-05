package br.itarocha.tendavisitante.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.itarocha.tendavisitante.model.Voluntario;

public interface VoluntarioRepository extends JpaRepository<Voluntario, Long>{
	
	public Page<Voluntario> findByPessoaNomeContainingOrderByPessoaNome(String descricao, Pageable pageable);
}

/*
 * 

http://docs.spring.io/spring-data/jpa/docs/2.0.0.M1/reference/html/#jpa.query-methods.at-query 
  
@Override
public Customer findByEmailAddress(EmailAddress emailAddress) {

	TypedQuery<Customer> query = em.createQuery("select c from Customer c where c.emailAddress = :email",
			Customer.class);
	query.setParameter("email", emailAddress);

	return query.getSingleResult();
}

@Query("select u from User u where u.firstname = :firstname")
	List<User> findByFirstname(String firstname);
*/

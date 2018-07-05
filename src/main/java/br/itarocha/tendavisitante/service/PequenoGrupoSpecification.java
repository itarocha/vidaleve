package br.itarocha.tendavisitante.service;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;

import org.springframework.data.jpa.domain.Specification;

import br.itarocha.tendavisitante.model.PequenoGrupo;
import br.itarocha.tendavisitante.model.PequenoGrupo_;

public class PequenoGrupoSpecification {
	
	private static final String IGUAL = "igual";
	private static final String DIFERENTE = "diferente";
	private static final String SEMELHANTE = "semelhante";
	private static final String ENTRE = "entre";
	
	/*
	public static Specification<PequenoGrupo> horario(String horario){
		
		return new Specification<PequenoGrupo>() {
			@Override
			public Predicate toPredicate(Root<PequenoGrupo> root, 
					CriteriaQuery<?> query, 
					CriteriaBuilder builder) {
				
				return builder.equal(root.<String>get("horario"), horario.trim());
			}
		};
	}
	*/
	
	/*
	@StaticMetamodel(Order.class)

	public class Order_ {

	    public static volatile SingularAttribute<Order, Integer> id;

	    public static volatile SingularAttribute<Order, Customer> customer;

	    public static volatile SetAttribute<Order, Item> items;

	    public static volatile SingularAttribute<Order, BigDecimal> totalCost;

	}
*/
	
	public static Specification<PequenoGrupo> filtroResponsavelNome(String operador, String valor, String valorComplemento){
		
		//PathBuilder<PequenoGrupo> entityPath = new PathBuilder<>(PequenoGrupo.class, "pequenoGrupo"); 
		
		if (operador.toLowerCase().equals(IGUAL)) {
			//return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(PequenoGrupo_.responsavel).get("nome"), valor);	
			return (root, criteriaQuery, criteriaBuilder) -> {
				
				Path<?> x = root;
				x = x.get("responsavel");
				x = x.get("nome");
				
				Expression<?> expressao;
				expressao = root.get("responsavel").get("nome");
				//return criteriaBuilder.equal(expressao, valor);
				
				return criteriaBuilder.equal(x, valor);
				//return criteriaBuilder.equal(root.get("responsavel").get("nome"), valor);
			};	
		}
		if (operador.toLowerCase().equals(DIFERENTE)) {
			//return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(PequenoGrupo_.responsavel).get("nome"), valor);	
			return (root, criteriaQuery, criteriaBuilder) -> {

				Expression<?> expressao;
				expressao = root.get("responsavel").get("nome");
				return criteriaBuilder.notEqual(expressao, valor);
			};
		}
		if (operador.toLowerCase().equals(SEMELHANTE)) {
			//return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(PequenoGrupo_.responsavel).get("nome"), "%"+valor.trim()+"%");	
			return (root, criteriaQuery, criteriaBuilder) -> {

				Path<?> z = root;
				z = z.get("responsavel");
				z = z.get("nome");
				
				return criteriaBuilder.like((Expression<String>)z, "%"+valor.trim()+"%");
				
				//return criteriaBuilder.like(root.get("responsavel").get("nome"), "%"+valor.trim()+"%");
				//return criteriaBuilder.like(x, "%"+valor.trim()+"%");
			};	
		}
		if (operador.toLowerCase().equals(ENTRE)) {
			//return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get(PequenoGrupo_.responsavel).get("nome"), valor, valorComplemento);	
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("responsavel").get("nome"), valor, valorComplemento);	
		}
		return null;
	}

	public static Specification<PequenoGrupo> filtroBairro(String operador, String valor, String valorComplemento){
		
		if (operador.toLowerCase().equals(IGUAL)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(PequenoGrupo_.endereco).get("bairro"), valor);	
		}
		if (operador.toLowerCase().equals(DIFERENTE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(PequenoGrupo_.endereco).get("bairro"), valor);	
		}
		if (operador.toLowerCase().equals(SEMELHANTE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(PequenoGrupo_.endereco).get("bairro"), "%"+valor.trim()+"%");	
		}
		if (operador.toLowerCase().equals(ENTRE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get(PequenoGrupo_.endereco).get("bairro"), valor, valorComplemento);	
		}
		return null;
	}

	public static Specification<PequenoGrupo> filtroPerfil(String operador, String valor, String valorComplemento){
		
		// predicate = cb.equal(root.get(“user”).get(“userid”).get(“userDetail”).get(“firstname”), searchName)
		
		if (operador.toLowerCase().equals(IGUAL)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(PequenoGrupo_.perfil).get("descricao"), valor);	
		}
		if (operador.toLowerCase().equals(DIFERENTE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(PequenoGrupo_.perfil).get("descricao"), valor);	
		}
		if (operador.toLowerCase().equals(SEMELHANTE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(PequenoGrupo_.perfil).get("descricao"), "%"+valor.trim()+"%");	
		}
		if (operador.toLowerCase().equals(ENTRE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get(PequenoGrupo_.perfil).get("descricao"), valor, valorComplemento);			
		}
		return null;
	}

	public static Specification<PequenoGrupo> filtroDiaSemana(String operador, int diaIni, int diaFim){
		if (operador.toLowerCase().equals(IGUAL) || operador.toLowerCase().equals(SEMELHANTE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(PequenoGrupo_.diaSemana).get("numero"), diaIni);	
		}
		if (operador.toLowerCase().equals(DIFERENTE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(PequenoGrupo_.diaSemana).get("numero"), diaIni);	
		}
		if (operador.toLowerCase().equals(ENTRE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get(PequenoGrupo_.diaSemana).get("numero"), diaIni, diaFim);	
		}
		return null;
	}

	public static Specification<PequenoGrupo> filtroHorario(String operador, String valor, String valorComplemento){
		
		if (operador.toLowerCase().equals(IGUAL)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get(PequenoGrupo_.horario), valor);	
		}
		if (operador.toLowerCase().equals(DIFERENTE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(PequenoGrupo_.horario), valor);	
		}
		if (operador.toLowerCase().equals(SEMELHANTE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get(PequenoGrupo_.horario), "%"+valor.trim()+"%");	
		}
		if (operador.toLowerCase().equals(ENTRE)) {
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get(PequenoGrupo_.horario), valor, valorComplemento);	
		}
		return null;
	}

}

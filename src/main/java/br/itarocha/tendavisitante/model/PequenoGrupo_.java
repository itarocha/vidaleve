package br.itarocha.tendavisitante.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PequenoGrupo.class)
public class PequenoGrupo_ {
	public static volatile SingularAttribute<PequenoGrupo, Long> id;
	public static volatile SingularAttribute<PequenoGrupo, Endereco> endereco;
	public static volatile SingularAttribute<PequenoGrupo, Pessoa> responsavel;
	public static volatile SingularAttribute<PequenoGrupo, PerfilPequenoGrupo> perfil;
	public static volatile SingularAttribute<PequenoGrupo, DiaSemana> diaSemana;
	public static volatile SingularAttribute<PequenoGrupo, String> horario;
}

package br.itarocha.tendavisitante.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity(name="voluntario")
public class Voluntario {
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
	*/
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Valid
	@OneToOne()
	private Pessoa pessoa;
	
	public Long getId() {
		return id;
	}

	public Voluntario(){
		this.pessoa = new Pessoa();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@Override
    public String toString() {
        return String.format("Voluntario[id=%d, pessoa='%s']",id, pessoa);
    }

}

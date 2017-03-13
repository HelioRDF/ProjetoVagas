package br.com.projetovagas.domain.localizacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.projetovagas.domain.complementos.GenericDomain;


/**
 * Detalhes:
 * 
 * -Entity - Diz que a classe é uma entidade do hibernate
 * 
 * -Column(
 * | length=Tamanho do campo 
 * | name=define o nome no banco 
 * | nullable= Diz se o campo pode ou não ser nulo "True ou False");
 * 
 * -ManyToOne - Muitas Cidades para 1 Estado 
 * 
 * -JoinColumn - Permite personalizar colunas que são chaves estrangeiras
 * 
 * 
 */

@SuppressWarnings("serial")
@Entity
public class Cidade extends GenericDomain{
	
	@Column()
	private String nome;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Estado estado;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Cidade [nome=" + nome + ", estado=" + estado + "]";
	}
	
	
	

}

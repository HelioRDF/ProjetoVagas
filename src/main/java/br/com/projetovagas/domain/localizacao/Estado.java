package br.com.projetovagas.domain.localizacao;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.projetovagas.domain.complementos.GenericDomain;

/**
 * 
 * @author hfranca

 *
 */
/**
 * [ Detalhes... ]
 * 
 * -Entity - Diz que a classe é uma entidade do hibernate
 * 
 * -Column(
 * | length=Tamanho do campo 
 * | name=define o nome no banco 
 * | nullable= Diz se o campo pode ou não ser nulo "True ou False");
 */
@SuppressWarnings("serial")
@Entity
public class Estado extends GenericDomain {

	@Column(length = 2, name = "sigla", nullable=false)
	private String sigla;
	@Column(length = 50, name = "nomeEstado")
	private String nome;
	
	
	//--------------------------------------------------------------

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Estado [sigla=" + sigla + ", nome=" + nome + "]";
	}
	
	

}

package br.com.projetovagas.domain.complementos;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Detalhes:
 * 
 * MappedSuperclass Específica que a classe não é uma tabela, mas vai ser usada
 *                   por outras classes que são tabelas
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class GenericDomain implements Serializable {

	/**
	 * Detalhes:
	 * 
	 * Id: Define a chave primaria
	 * GeneratedValue: Gera uma chave primária de modo automatico no DB
	 * 
	 */
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private Long codigo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	@Override
	public String toString() {
		return "GenericDomain [codigo=" + codigo + ", getCodigo()=" + getCodigo() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
	
}

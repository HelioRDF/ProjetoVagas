package br.com.projetovagas.domain.usuarios;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.projetovagas.domain.complementos.GenericDomain;

/**
 * [ Detalhes... ]
 * 
 * 
 */

@SuppressWarnings("serial")
@Entity
public class FormacaoAcademica extends GenericDomain {

	// Graduação | Técnico | Primeiro Grau| Segundo Grau| ???

	@Column(length = 55)
	private String instituicao; // Anhembi Morumbi

	@Column(length = 55)
	private String titulo; // Graduação | Técnico | Primeiro Grau| Segundo
							// Grau|?
	
	@Column
	private String status; 
	
	@Column
	private String nomeCurso;// Analise de Sistemas

	@Column
	@Temporal(TemporalType.DATE)
	private Date inicio;

	@Column
	@Temporal(TemporalType.DATE)
	private Date fim;
	
	@ManyToOne
	@JoinColumn(name="usuarioCodigo")
	private Usuario usuario;
	






	// -------------------------------------------------------
	

	public String getInstituicao() {
		return instituicao;
	}

	

	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}



	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}




	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fim == null) ? 0 : fim.hashCode());
		result = prime * result + ((inicio == null) ? 0 : inicio.hashCode());
		result = prime * result + ((instituicao == null) ? 0 : instituicao.hashCode());
		result = prime * result + ((nomeCurso == null) ? 0 : nomeCurso.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormacaoAcademica other = (FormacaoAcademica) obj;
		if (fim == null) {
			if (other.fim != null)
				return false;
		} else if (!fim.equals(other.fim))
			return false;
		if (inicio == null) {
			if (other.inicio != null)
				return false;
		} else if (!inicio.equals(other.inicio))
			return false;
		if (instituicao == null) {
			if (other.instituicao != null)
				return false;
		} else if (!instituicao.equals(other.instituicao))
			return false;
		if (nomeCurso == null) {
			if (other.nomeCurso != null)
				return false;
		} else if (!nomeCurso.equals(other.nomeCurso))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}
	
	
	

}

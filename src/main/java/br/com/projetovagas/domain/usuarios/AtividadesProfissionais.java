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
//QUALIFICAÇÕES E ATIVIDADES PROFISSIONAIS
@SuppressWarnings("serial")
@Entity
public class AtividadesProfissionais extends GenericDomain {

	@Column(length = 60)
	private String nomeCurso;

	@Column(length = 60)
	private String instituicao;

	@Column()
	@Temporal(TemporalType.DATE)
	private Date anoCurso;

	@Column(length = 7)
	private String cargaHoraria;
	
	@ManyToOne
	@JoinColumn(name="usuarioCodigo")
	private Usuario usuario;

	// ----------------------------------------------------------
	
	
	
	
	
	public String getNomeCurso() {
		return nomeCurso;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}


	public Date getAnoCurso() {
		return anoCurso;
	}

	public void setAnoCurso(Date anoCurso) {
		this.anoCurso = anoCurso;
	}

	public String getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(String cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

}

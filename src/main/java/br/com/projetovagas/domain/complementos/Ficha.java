package br.com.projetovagas.domain.complementos;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.projetovagas.domain.usuarios.Usuario;

@SuppressWarnings("serial")
@Entity
public class Ficha extends GenericDomain {

	@Column()
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@JoinColumn(name = "candidato_id" )
	@OneToOne
	private Usuario candidato_id;
	
	@JoinColumn(name = "oportunidade_id")
	@OneToOne
	private Oportunidade oportunidade_id;
	
	@Column
	@Lob
	private String resposta;

	// --------------------------------------------------

	

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Oportunidade getOportunidade_id() {
		return oportunidade_id;
	}

	public void setOportunidade_id(Oportunidade oportunidade_id) {
		this.oportunidade_id = oportunidade_id;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public Usuario getCandidato_id() {
		return candidato_id;
	}

	public void setCandidato_id(Usuario candidato_id) {
		this.candidato_id = candidato_id;
	}
	
	
}

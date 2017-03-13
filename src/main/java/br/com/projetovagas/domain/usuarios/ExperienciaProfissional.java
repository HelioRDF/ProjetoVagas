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
public class ExperienciaProfissional extends GenericDomain {

	@Column(length = 55)
	private String nomeEmpresa;

	@Column(length = 55)
	private String cargo;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date entrada;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date saida;

	@Column(nullable = false)
	private String principaisAtividades;
	
	@ManyToOne
	@JoinColumn(name="usuarioCodigo")
	private Usuario usuario;
	

	// ----------------------------------------------------------------

		
	
	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Date getEntrada() {
		return entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}

	public Date getSaida() {
		return saida;
	}

	public void setSaida(Date saida) {
		this.saida = saida;
	}

	public String getPrincipaisAtividades() {
		return principaisAtividades;
	}

	public void setPrincipaisAtividades(String principaisAtividades) {
		this.principaisAtividades = principaisAtividades;
	}

}

package br.com.projetovagas.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Messages;

import br.com.projetovagas.dao.localizacao.EstadoDAO;
import br.com.projetovagas.domain.localizacao.Estado;


/**
 * [ Detalhes... ]
 * 
 * -Omnifaces Gera mensagems ligando controle e visão
 * Messages.addGlobalInfo("ddd");
 *
 */

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped

public class EstadoBean implements Serializable {

	private Estado estado;
	private EstadoDAO dao;
	private List<Estado> listaEstado;

	


	// Carregar
	// -------------------------------------------------------------------------------------

	public void carregar() {

		try {
			estado = new Estado();
			dao = new EstadoDAO();
			this.listaEstado = dao.listar();
			dao = null;
			estado = null;
			Messages.addGlobalInfo("Lista atualizada com sucesso ");

		} catch (Exception e) {
			Messages.addGlobalError("Falha ao tentar  atualizadar a lista  ");
		}

	}

	

	// -------------------------------------------------

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}

}

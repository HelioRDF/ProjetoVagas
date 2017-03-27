package br.com.projetovagas.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Messages;

import br.com.projetovagas.dao.localizacao.CidadeDAO;
import br.com.projetovagas.domain.localizacao.Cidade;
import br.com.projetovagas.domain.localizacao.Estado;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class CidadeBean implements Serializable {
	private List<Cidade> listaCidade;
	private List<Estado> listaEstado;

	private Cidade cidade;
	private CidadeDAO daoCidade;

	private Estado estado;

	// Carregar
	// -------------------------------------------------------------------------------------

	public void carregar() {

		try {
			cidade = new Cidade();
			daoCidade = new CidadeDAO();
			listaCidade = daoCidade.listar();

			daoCidade = new CidadeDAO();
			cidade = new Cidade();
			Messages.addGlobalInfo("Lista atualizada com sucesso ");

		} catch (Exception e) {
			Messages.addGlobalError("Falha ao tentar  atualizadar a lista  ");
		}

	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}

package br.com.projetovagas.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.projetovagas.dao.localizacao.CidadeDAO;
import br.com.projetovagas.dao.localizacao.EstadoDAO;
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
	private EstadoDAO daoEstado;

	// Salvar
	// -------------------------------------------------------------------------------------

	public void salvar() {

		if (!(cidade == null)) {
			daoCidade = new CidadeDAO();
		}

		daoCidade.merge(cidade);
		Messages.addGlobalInfo("Cidade salva com sucesso: " + cidade.getNome());

		cidade = new Cidade();
	}

	// Novo
	// -------------------------------------------------------------------------------------

	public void novo() {

		
		try {
			RequestContext.getCurrentInstance().reset("formCadastro");
			daoEstado = new EstadoDAO();
			listaEstado = daoEstado.listar("nome");

			cidade = new Cidade();
			daoCidade = new CidadeDAO();

		} catch (Exception e) {
			Messages.addGlobalError("Erro no metodo novo!!!");
		}

	}

	// Fechar
	// -------------------------------------------------------------------------------------

	public void fechar() {
		
		RequestContext.getCurrentInstance().reset("formCadastro");
		cidade = new Cidade();
		estado = new Estado();
		daoCidade = new CidadeDAO();
		daoEstado = new EstadoDAO();

	
	

	}

	// Excluir
	// -------------------------------------------------------------------------------------

	public void excluir(ActionEvent evento) {

		try {

			cidade = (Cidade) evento.getComponent().getAttributes().get("meuSelect");
			CidadeDAO dao = new CidadeDAO();
			Messages.addGlobalInfo("Nome Removido: " + cidade.getNome());
			dao.excluir(cidade);

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Remover: " + estado.getNome());

		}

	}

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

	// Instanciar
	// -------------------------------------------------------------------------------------

	public void getinstancia(ActionEvent evento) {

		try {

			daoEstado = new EstadoDAO();
			listaEstado = daoEstado.listar("nome");
			cidade = (Cidade) evento.getComponent().getAttributes().get("meuSelect");
			Messages.addGlobalInfo("Cidade: " + cidade.getNome());
			Messages.addGlobalInfo("Estado: " + cidade.getEstado().getNome());
		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar: " + cidade.getNome());

		}

	}

	// ------------------------------------------------------------------------------------------
	
	
/*	public void listarInfos() {

		try {

			listaEstado = daoEstado.listar();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}

	}*/

	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	
	
	
	
	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

}

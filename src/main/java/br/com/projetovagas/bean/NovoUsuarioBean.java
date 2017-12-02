package br.com.projetovagas.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;

import br.com.projetovagas.dao.localizacao.CidadeDAO;
import br.com.projetovagas.dao.localizacao.EstadoDAO;
import br.com.projetovagas.dao.usuarios.UsuarioDAO;
import br.com.projetovagas.domain.localizacao.Cidade;
import br.com.projetovagas.domain.localizacao.Estado;
import br.com.projetovagas.domain.usuarios.Usuario;

@ManagedBean
@ViewScoped
public class NovoUsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -125586851442420009L;
	private Usuario usuario;
	UsuarioDAO dao;
	private Boolean statusBoolean = false;

	private Estado estado;
	private List<Estado> listaEstado;
	private String auxEstado = " Selecione um Estado";
	
	Cidade cidade;
	private CidadeDAO cidadeDAO;
	private String auxCidade = "Selecione uma Cidade";
	private List<Cidade> listaCidade;
	

	@PostConstruct
	public void inicia() {
		
		usuario = new Usuario();
		//LISTA ESTADOS 
		EstadoDAO estadoDao = new EstadoDAO();
		listaEstado = estadoDao.listar("nome");
	}

	// Salvar usuário
	// -------------------------------------------------------------------------------------
	public void salvar() {
		dao = new UsuarioDAO();
		Boolean permitir = dao.validarEmail(usuario.getEmail());

		if (!permitir) {
			Messages.addGlobalWarn("O Endereço de e-mail já existe");

			return;

		}

		try {

			// Cria um hash e criptografa a senha
			SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
			usuario.setSenha(hash.toHex());
			usuario.setDataCadastro(new Date());
			usuario.setAdmin(false);
			usuario.setStatus(true);
			
			dao = new UsuarioDAO();
			dao.salvar(usuario);
			Messages.addGlobalInfo("Usuário(a) " + usuario.getNome() + ", salvo com sucesso.");

		} catch (Exception e) {

			Messages.addGlobalError("Não foi possível salvar o usuário, tente novamente mais tarde ... ");

			System.out.println("Erro" + e.getMessage());

		} finally {

		}
	}


	// Buscar Cidades
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void buscarCidade() {

		try {

			cidadeDAO = new CidadeDAO();
			listaCidade = cidadeDAO.buscarPorEstado(estado.getCodigo());

		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}

	}

	// -------------------------------------------------------------------------------------------

	// Filtrar Cidade
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void filtrarCidade() {

		try {

			cidadeDAO = new CidadeDAO();
			listaCidade = cidadeDAO.buscarPorEstado(estado.getCodigo());
			auxCidade = "Selecione uma Cidade";

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// -------------------------------------------------------------------------------------------

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

	public String getAuxEstado() {
		return auxEstado;
	}

	public void setAuxEstado(String auxEstado) {
		this.auxEstado = auxEstado;
	}

	public String getAuxCidade() {
		return auxCidade;
	}

	public void setAuxCidade(String auxCidade) {
		this.auxCidade = auxCidade;
	}

	public List<Cidade> getListaCidade() {
		return listaCidade;
	}

	public void setListaCidade(List<Cidade> listaCidade) {
		this.listaCidade = listaCidade;
	}

	public Boolean getStatusBoolean() {
		return statusBoolean;
	}

	public void setStatusBoolean(Boolean statusBoolean) {
		this.statusBoolean = statusBoolean;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
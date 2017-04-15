package br.com.projetovagas.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.projetovagas.dao.empresas.EmpresaDAO;
import br.com.projetovagas.dao.localizacao.CidadeDAO;
import br.com.projetovagas.dao.localizacao.EstadoDAO;
import br.com.projetovagas.dao.usuarios.UsuarioDAO;
import br.com.projetovagas.domain.empresas.Empresa;
import br.com.projetovagas.domain.localizacao.Cidade;
import br.com.projetovagas.domain.localizacao.Estado;
import br.com.projetovagas.domain.usuarios.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private int tipoLogin = 1;

	private Usuario usuario;
	private static Usuario usuarioLogado;

	private Empresa empresa;
	private  static Empresa empresaLogada;

	private Estado estado;
	private EstadoDAO estadoDao;
	private  static List<Estado> listaEstado;

	private Cidade cidade = new Cidade();
	private CidadeDAO cidadeDao;
	private List<Cidade> listaCidade;

	private String auxCidade = "Selecione uma Cidade";
	private String auxEstado = " Selecione um Estado";

	// Login
	// -------------------------------------------------------------------------------------------

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		empresa = new Empresa();

	}

	public void autenticar() {

		if (tipoLogin == 1) {

			try {

				UsuarioDAO usuarioDAO = new UsuarioDAO();
				usuarioLogado = usuarioDAO.autenticar(usuario.getEmail().trim(), usuario.getSenha());

				if (usuarioLogado == null) {
					Messages.addGlobalWarn("Usuário e/ou senha, incorretos");
					return;

				} else {

					// Verifica se usuário está Ativo
					if (!usuarioLogado.getStatus()) {

						Messages.addGlobalError("Usuário Desativado.");
						usuarioLogado = null;
						return;

					}

				}

				// Usuário Ok...
				Faces.redirect("./pages/administrativas/oportunidades.xhtml");
				buscarEstados();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		} // fim do If

		else {

			try {

				EmpresaDAO empresaDAO = new EmpresaDAO();
				empresaLogada = empresaDAO.autenticar(empresa.getEmail().trim(), usuario.getSenha());

				if (usuarioLogado == null) {
					Messages.addGlobalWarn("Empresa e/ou senha, incorretos");
					return;

				} else {

					// Verifica se usuário está Ativo
					if (!empresaLogada.getStatus()) {

						Messages.addGlobalError("Empresa Desativada.");
						empresaLogada = null;
						return;

					}

				}

				// Empresa Ok...
				Faces.redirect("./pages/administrativas/oportunidades.xhtml");
				buscarEstados();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		}

	}

	// Logoff
	// -------------------------------------------------------------------------------------------

	public void sair() {

		try {

			usuarioLogado = null;
			empresaLogada = null;

			// Destroi as sessões após loggof do usuário.
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
			session.invalidate();

			// Redireciona para a página de login
			Faces.redirect("./pages/publicas/login.xhtml");
			Messages.addGlobalInfo("Logout");

			return;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Listar Estado
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void buscarEstados() {

		try {

			auxEstado = usuarioLogado.getCidade().getEstado().getNome().toString().toUpperCase();
			auxCidade = usuarioLogado.getCidade().getNome().toString().toUpperCase();

			estadoDao = new EstadoDAO();
			listaEstado = estadoDao.listar("nome");

		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}

	}

	// Listar Cidade
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void buscarCidade() {

		try {

			cidadeDao = new CidadeDAO();
			listaCidade = cidadeDao.buscarPorEstado(estado.getCodigo());

		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}

	}

	public void teste() {

		System.out.println("Tipo de login: " + tipoLogin);

	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------


	
	

	public Empresa getEmpresa() {
		return empresa;
	}

	public static Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public static void setUsuarioLogado(Usuario usuarioLogado) {
		LoginBean.usuarioLogado = usuarioLogado;
	}

	public static List<Estado> getListaEstado() {
		return listaEstado;
	}

	public static void setListaEstado(List<Estado> listaEstado) {
		LoginBean.listaEstado = listaEstado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public static void setEmpresaLogada(Empresa empresaLogada) {
		LoginBean.empresaLogada = empresaLogada;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Empresa getEmpresaLogada() {
		return empresaLogada;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getTipoLogin() {
		return tipoLogin;
	}

	public void setTipoLogin(int tipoLogin) {
		this.tipoLogin = tipoLogin;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}


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

	public String getAuxCidade() {
		return auxCidade;
	}

	public void setAuxCidade(String auxCidade) {
		this.auxCidade = auxCidade;
	}

	public String getAuxEstado() {
		return auxEstado;
	}

	public void setAuxEstado(String auxEstado) {
		this.auxEstado = auxEstado;
	}
	
	

	// ------------------------------------------------------------

}

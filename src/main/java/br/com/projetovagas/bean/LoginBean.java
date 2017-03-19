package br.com.projetovagas.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.projetovagas.dao.usuarios.UsuarioDAO;
import br.com.projetovagas.domain.localizacao.Cidade;
import br.com.projetovagas.domain.localizacao.Estado;
import br.com.projetovagas.domain.usuarios.Usuario;

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class LoginBean implements Serializable {

	private Usuario usuario;
	private static Usuario usuarioLogado;
	private static Estado auxEstadoObj;
	private static Cidade auxCidadeObj;

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
	}

	public void autenticar() {

		try {

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuario.getEmail().trim(), usuario.getSenha());

			if (usuarioLogado == null) {
				Messages.addGlobalWarn("Usuário e/ou senha, incorretos");
				return;

			}else {

		
			// Verifica se usuário está Ativo
			if (!usuarioLogado.getStatus()) {

				Messages.addGlobalError("Usuário Desativado.");
				usuarioLogado = null;
				return;

			}
			
			}
			auxEstadoObj = usuarioLogado.getCidade().getEstado();
			auxCidadeObj = usuarioLogado.getCidade();

			Faces.redirect("./pages/administrativas/oportunidades.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

	public void sair() {

		try {

			usuarioLogado = null;
			Faces.redirect("./pages/publicas/login.xhtml");
			Messages.addGlobalInfo("Logout");

			return;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ------------------------------------------------------------

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public Estado getAuxEstadoObj() {
		return auxEstadoObj;
	}

	public Cidade getAuxCidadeObj() {
		return auxCidadeObj;
	}

}

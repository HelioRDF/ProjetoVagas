
package br.com.projetovagas.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.projetovagas.dao.localizacao.CidadeDAO;
import br.com.projetovagas.dao.localizacao.EstadoDAO;
import br.com.projetovagas.dao.usuarios.AtividadesProfissionaisDAO;
import br.com.projetovagas.dao.usuarios.ExperienciaProfissionalDAO;
import br.com.projetovagas.dao.usuarios.FormacaoAcademicaDAO;
import br.com.projetovagas.dao.usuarios.UsuarioDAO;
import br.com.projetovagas.domain.localizacao.Cidade;
import br.com.projetovagas.domain.localizacao.Estado;
import br.com.projetovagas.domain.usuarios.AtividadesProfissionais;
import br.com.projetovagas.domain.usuarios.ExperienciaProfissional;
import br.com.projetovagas.domain.usuarios.FormacaoAcademica;
import br.com.projetovagas.domain.usuarios.Usuario;

/**
 * [ Detalhes... ]
 * 
 * -Mensagen |FacesContext.getCurrentInstance().addMessage(null,new
 * |FacesMessage(FacesMessage.SEVERITY_WARN, "A confirmação de |senha está
 * incorreta", null));
 */

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private Estado estado;
	private FormacaoAcademica formacaoAcademica;
	private ExperienciaProfissional experienciaProfissional;
	private AtividadesProfissionais atividadesProfissionais;
	Cidade cidade = new Cidade();
	Cidade cidadeAux = new Cidade();

	private UsuarioDAO dao;
	private CidadeDAO cidadeDao;
	private EstadoDAO estadoDao;
	private FormacaoAcademicaDAO daoFormacao;
	private ExperienciaProfissionalDAO daoExperiencia;
	private AtividadesProfissionaisDAO daoAtividades;

	private List<Usuario> listaUsuario;
	private List<Cidade> listaCidade;
	private List<Estado> listaEstado;
	private List<FormacaoAcademica> listaFormacao;
	private List<ExperienciaProfissional> listaExperiencia;
	private List<AtividadesProfissionais> listaAtividades;

	private String auxCidade = "Selecione uma Cidade";
	private String auxEstado = " Selecione um Estado";

	private Boolean telaEditar = false;
	private Boolean botaoEditar = false;
	private Boolean botaoSalvar = false;
	private Boolean botaoFormacao = false;
	private Boolean statusBoolean = false;
	private Boolean botaoAtividades = false;
	private Boolean botaoExperiencia = false;
	private Boolean botaoInfo;

	// Salvar usuário
	// -------------------------------------------------------------------------------------
	public void salvar() {
		Boolean permitir = dao.validarEmail(usuario.getEmail());

		if (!permitir) {
			 Messages.addGlobalError("O Endereço de e-mail já existe ... ");

			return;

		}

		try {

			// Cria um hash e criptografa a senha
			SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
			usuario.setSenha(hash.toHex());
			usuario.setDataCadastro(new Date());
			usuario.setCidade(cidade);

			dao.salvar(usuario);
			Messages.addGlobalInfo("Usuário(a) " + usuario.getNome() + ", salvo com sucesso.");

		} catch (Exception e) {

			Messages.addGlobalError("Não foi possível salvar o usuário, tente novamente mais tarde ... ");

			System.out.println("Erro" + e);

		} finally {

			fechar();

		}
	}

	// Salvar formação
	// -------------------------------------------------------------------------------------
	public void salvarFormacao() {

		try {
			if (botaoFormacao = true) {

				if (statusBoolean.equals(true)) {
					formacaoAcademica.setStatus("Concluído");
				} else {
					formacaoAcademica.setStatus("Incompleto");
				}

				formacaoAcademica.setUsuario(usuario);
				daoFormacao.merge(formacaoAcademica);

				Messages.addGlobalInfo("Formação  salva com sucesso: " + formacaoAcademica.getNomeCurso());
				carregarCurriculo();
			}

		} catch (Exception e) {
			Messages.addGlobalError("Não foi possível salvar a formação, Preencha os campos corretamente. ");

		} finally {

		}
	}

	// Salvar formação
	// -------------------------------------------------------------------------------------
	public void salvarExperiencia() {

		try {
			if (botaoExperiencia = true) {
				experienciaProfissional.setUsuario(usuario);
				daoExperiencia.merge(experienciaProfissional);
				Messages.addGlobalInfo("Experiencia  salva com sucesso: " + experienciaProfissional.getCargo());
				carregarCurriculo();
			}

		} catch (Exception e) {
			Messages.addGlobalError("Não foi possível salvar a formação, Preencha os campos corretamente. ");

		} finally {

		}
	}

	// Salvar Atividades
	// -------------------------------------------------------------------------------------
	public void salvarAtividades() {

		try {
			if (botaoAtividades = true) {
				atividadesProfissionais.setUsuario(usuario);
				daoAtividades.merge(atividadesProfissionais);
				Messages.addGlobalInfo("Qualificação  salva com sucesso: " + atividadesProfissionais.getNomeCurso());
				carregarCurriculo();
			}

		} catch (Exception e) {
			Messages.addGlobalError("Não foi possível salvar a Qualificação, Preencha os campos corretamente. ");

		} finally {

		}
	}

	// Novo
	// -------------------------------------------------------------------------------------------
	public void novo() {

		listarInfos();
		telaEditar = false;
		botaoEditar = false;
		botaoSalvar = true;

		usuario = new Usuario();
		dao = new UsuarioDAO();

		auxCidade = "Selecione uma Cidade";
		auxEstado = " Selecione um Estado";

		System.out.println("Método novo");

	}

	// Fechar
	// -------------------------------------------------------------------------------------------
	public void fechar() {

		RequestContext.getCurrentInstance().reset("dialogform");
		usuario = new Usuario();
		dao = new UsuarioDAO();

		System.out.println("Método fechar");

	}

	// Carregar
	// -------------------------------------------------------------------------------------------
	public void carregar() {

		try {

			dao = new UsuarioDAO();
			listaUsuario = dao.listar();

			Messages.addGlobalInfo("Lista atualizada com sucesso ");

		} catch (Exception e) {
			Messages.addGlobalError("Falha ao tentar  atualizadar a lista  ");
		} finally {
			fechar();
		}

	}

	// Carregar Curriculo
	// -------------------------------------------------------------------------------------------
	public void carregarCurriculo() {

		// Atividades Profissionais
		// ----------------------------------------------------------

		try {

			atividadesProfissionais = new AtividadesProfissionais();
			daoAtividades = new AtividadesProfissionaisDAO();
			listaAtividades = daoAtividades.buscarPorUsuario(usuario.getCodigo());

			if (listaAtividades.size() < 10) {
				botaoAtividades = true;

			} else {
				botaoAtividades = false;
				Messages.addGlobalWarn("Numéro maximo de Qualificações atingido. (max = 10)");

			}

		} catch (Exception e) {
			Messages.addGlobalError("Falha ao tentar  atualizadar a lista  ");
		}

		// Fim Atividades Profissionais
		// ----------------------------------------------------------

		// Experiência
		// ----------------------------------------------------------
		try {

			experienciaProfissional = new ExperienciaProfissional();
			daoExperiencia = new ExperienciaProfissionalDAO();
			listaExperiencia = daoExperiencia.buscarPorUsuario(usuario.getCodigo());

			if (listaExperiencia.size() < 4) {
				botaoExperiencia = true;

			} else {
				botaoExperiencia = false;
				Messages.addGlobalWarn("Numéro maximo de Experiência atingido. (max = 4)");

			}

		} catch (Exception e) {
			Messages.addGlobalError("Falha ao tentar  atualizadar a lista  ");
		}
		// Fim Experiência
		// ----------------------------------------------------------

		// Formação Academica
		// ----------------------------------------------------------
		try {
			formacaoAcademica = new FormacaoAcademica();
			daoFormacao = new FormacaoAcademicaDAO();
			listaFormacao = daoFormacao.buscarPorUsuario(usuario.getCodigo());

			if (listaFormacao.size() < 7) {
				botaoFormacao = true;

			} else {
				botaoFormacao = false;
				Messages.addGlobalWarn("Numéro maximo de Formações atingido. (max = 7)");

			}

		} catch (Exception e) {
			Messages.addGlobalError("Falha ao tentar  atualizadar a lista  ");
		}

		// Fim Formação Academica
		// ----------------------------------------------------------

	}

	// Excluir usuário
	// -------------------------------------------------------------------------------------------
	public void excluir(ActionEvent evento) {

		try {

			usuario = (Usuario) evento.getComponent().getAttributes().get("meuSelect");
			UsuarioDAO dao = new UsuarioDAO();
			Messages.addGlobalInfo("Usuário(a) ' " + usuario.getNome() + "' Removido com sucesso!!!");
			dao.excluir(usuario);

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Remover: " + usuario.getNome());

		} finally {
			fechar();
		}

	}

	// Excluir Formacao
	// -------------------------------------------------------------------------------------------
	public void excluirFormacao(ActionEvent evento) {

		try {

			formacaoAcademica = (FormacaoAcademica) evento.getComponent().getAttributes().get("meuSelect");

			FormacaoAcademicaDAO dao = new FormacaoAcademicaDAO();
			Messages.addGlobalInfo("Formação removida com sucesso: " + formacaoAcademica.getNomeCurso());
			dao.excluir(formacaoAcademica);
			carregarCurriculo();

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Remover: " + formacaoAcademica.getNomeCurso());

		} finally {

		}

	}

	// Excluir Formacao
	// -------------------------------------------------------------------------------------------
	public void excluirExperiencia(ActionEvent evento) {

		try {

			experienciaProfissional = (ExperienciaProfissional) evento.getComponent().getAttributes().get("meuSelect");

			ExperienciaProfissionalDAO dao = new ExperienciaProfissionalDAO();
			Messages.addGlobalInfo("Experiência removida com sucesso: " + experienciaProfissional.getCargo());
			dao.excluir(experienciaProfissional);
			carregarCurriculo();

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Remover: " + experienciaProfissional.getCargo());

		} finally {

		}

	}

	// Excluir Qualificação
	// -------------------------------------------------------------------------------------------
	public void excluirQualificacao(ActionEvent evento) {

		try {

			atividadesProfissionais = (AtividadesProfissionais) evento.getComponent().getAttributes().get("meuSelect");

			AtividadesProfissionaisDAO dao = new AtividadesProfissionaisDAO();
			Messages.addGlobalInfo("Qualificação removida com sucesso: " + atividadesProfissionais.getNomeCurso());
			dao.excluir(atividadesProfissionais);
			carregarCurriculo();

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Remover: " + atividadesProfissionais.getNomeCurso());

		} finally {

		}

	}

	// Editar usuário
	// -------------------------------------------------------------------------------------------
	public void editar() {
		Long id = usuario.getCodigo();
		Boolean permitir =dao.validarEmail(usuario.getEmail(),id);
	
			
		 if(!permitir){

			 Messages.addGlobalError("O Endereço de e-mail já existe ... ");
			 listaUsuario = dao.listar();
		
		 return;
		
		 }

		try {

			dao = new UsuarioDAO();

			if (cidade == null) {
				cidade = cidadeAux;
			}
			System.out.println("\nEditar");
			System.out.println("Cidade >>>" + cidade);
			System.out.println("CidadeAux >>>" + cidade);

			usuario.setCidade(cidade);
			dao.editar(usuario);

			Messages.addGlobalInfo("Usuário(a) ' " + usuario.getNome() + "' Editado com sucesso!!!");

		} catch (Exception e) {
			
			


			Messages.addGlobalError("Erro ao Editar Usuário(a) '" + usuario.getNome() + "'");
			System.out.println("Editar Erro:" + e.getMessage());
			System.out.println("Editar Erro:" + e.getCause());

		} finally {

			fechar();
		}

	}

	// Editar Objetivos
	// -------------------------------------------------------------------------------------------
	public void editarObjetivos() {

		try {

			listarInfos();

			dao = new UsuarioDAO();

			dao.merge(usuario);

			Messages.addGlobalInfo("Usuário(a) ' " + usuario.getNome() + "' Editado com sucesso!!!");

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar Usuário(a) '" + usuario.getNome() + "'");
			System.out.println("Editar Erro:" + e.getMessage());

		} finally {

		}

	}

	// Salvar Senha
	// -------------------------------------------------------------------------------------------
	public void editarSenha() {

		try {

			// Cria um hash e criptografa a senha
			SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());
			usuario.setSenha(hash.toHex());
			dao = new UsuarioDAO();
			dao.merge(usuario);
			Messages.addGlobalInfo("Usuário Editado com sucesso: " + usuario.getNome());

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar: " + usuario.getNome());

		} finally {

			fechar();
		}

	}

	// Instanciar
	// -------------------------------------------------------------------------------------------

	public void getinstancia(ActionEvent evento) {

		try {

			usuario = (Usuario) evento.getComponent().getAttributes().get("meuSelect");
			Messages.addGlobalInfo("Seleção: " + usuario.getNome());

			System.out.println("Usuário:" + usuario.getNome());

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar: " + usuario.getNome());
			System.out.println("Erro getinstancia" + e.getMessage());

		}

	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void telaEditarObj(ActionEvent evento) {

		try {

			System.out.println("\ntelaEditarOBJ");
			botaoSalvar = false;
			botaoEditar = true;
			telaEditar = true;

			CidadeDAO cidDAO = new CidadeDAO();
			Cidade cid = new Cidade();

			usuario = (Usuario) evento.getComponent().getAttributes().get("meuSelect");

			long num = cidDAO.buscarOBJCidadeUsuario(usuario);
			System.out.println("Num:" + num);

			cid = cidDAO.buscarObjCidadePorCodigo(num);
			System.out.println("Cid:" + cid);

			if (cid == null) {

				auxCidade = "Selecione uma Cidade";
				auxEstado = " Selecione um Estado";

			} else {
				auxCidade = cid.getNome();
				auxEstado = cid.getEstado().getNome();
				cidadeAux = cid;
			}

			System.out.println("Estado Aux:" + auxEstado);
			System.out.println("Cidade Aux:" + auxCidade);

		} catch (Exception e) {
			System.out.println("Erro no posGET");
		}

	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void getinstanciaCurriculo(ActionEvent evento) {

		try {

			usuario = (Usuario) evento.getComponent().getAttributes().get("meuSelect");
			Messages.addGlobalInfo("Seleção: " + usuario.getNome());

			carregarCurriculo();

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar: " + usuario.getNome());
			System.out.println("catch do Método Curriculo: " + usuario.getNome());

		}

	}

	// Instancia de Formação
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void getinstanciaFormação(ActionEvent evento) {

		try {
			botaoFormacao = true;

			formacaoAcademica = (FormacaoAcademica) evento.getComponent().getAttributes().get("meuSelect");
			Messages.addGlobalInfo("Seleção: " + formacaoAcademica.getNomeCurso());

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar: " + formacaoAcademica.getNomeCurso());

		}

	}

	// Instancia de Experiencia
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void getinstanciaExperiencia(ActionEvent evento) {

		try {
			botaoExperiencia = true;

			experienciaProfissional = (ExperienciaProfissional) evento.getComponent().getAttributes().get("meuSelect");
			Messages.addGlobalInfo("Seleção: " + experienciaProfissional.getCargo());

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar: " + experienciaProfissional.getCargo());

		}

	}

	// Instancia de Qualificações
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void getinstanciaQualificacao(ActionEvent evento) {

		try {
			botaoAtividades = true;

			atividadesProfissionais = (AtividadesProfissionais) evento.getComponent().getAttributes().get("meuSelect");
			Messages.addGlobalInfo("Seleção: " + atividadesProfissionais.getNomeCurso());

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar: " + atividadesProfissionais.getNomeCurso());

		}

	}

	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void listarInfos() {

		try {

			estadoDao = new EstadoDAO();

			listaEstado = estadoDao.listar("nome");

		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}

	}

	// Filtrar Cidade
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void filtrarCidade() {

		try {

			cidadeDao = new CidadeDAO();
			listaCidade = cidadeDao.buscarPorEstado(estado.getCodigo());
			auxCidade = "Selecione uma Cidade";

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// Filtrar Cidade 2, precisei replicar o método, devido a um erro na linha
	// quando chamada dentro do getinstance, por conta do param (actionevent)
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void filtrarCidadeTwo() {

		try {

			cidadeDao = new CidadeDAO();
			listaCidade = cidadeDao.buscarPorEstado(estado.getCodigo());

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// Listar Estado
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	@PostConstruct
	public void BuscarEstados() {

		try {

			System.out.println("Listando estados...");

			estadoDao = new EstadoDAO();
			listaEstado = estadoDao.listar("nome");

		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}

	}
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public void setListaUsuario(ArrayList<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
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

	public Boolean getBotaoEditar() {
		return botaoEditar;
	}

	public void setBotaoEditar(Boolean botaoEditar) {
		this.botaoEditar = botaoEditar;
	}

	public Boolean getBotaoSalvar() {
		return botaoSalvar;
	}

	public void setBotaoSalvar(Boolean botaoSalvar) {
		this.botaoSalvar = botaoSalvar;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public FormacaoAcademica getFormacaoAcademica() {
		return formacaoAcademica;
	}

	public void setFormacaoAcademica(FormacaoAcademica formacaoAcademica) {
		this.formacaoAcademica = formacaoAcademica;
	}

	public List<FormacaoAcademica> getListaFormacao() {
		return listaFormacao;
	}

	public void setListaFormacao(List<FormacaoAcademica> listaFormacao) {
		this.listaFormacao = listaFormacao;
	}

	public Boolean getBotaoFormacao() {
		return botaoFormacao;
	}

	public void setBotaoFormacao(Boolean botaoFormacao) {
		this.botaoFormacao = botaoFormacao;
	}

	public ExperienciaProfissional getExperienciaProfissional() {
		return experienciaProfissional;
	}

	public void setExperienciaProfissional(ExperienciaProfissional experienciaProfissional) {
		this.experienciaProfissional = experienciaProfissional;
	}

	public List<ExperienciaProfissional> getListaExperiencia() {
		return listaExperiencia;
	}

	public void setListaExperiencia(List<ExperienciaProfissional> listaExperiencia) {
		this.listaExperiencia = listaExperiencia;
	}

	public Boolean getBotaoExperiencia() {
		return botaoExperiencia;
	}

	public void setBotaoExperiencia(Boolean botaoExperiencia) {
		this.botaoExperiencia = botaoExperiencia;
	}

	public AtividadesProfissionais getAtividadesProfissionais() {
		return atividadesProfissionais;
	}

	public void setAtividadesProfissionais(AtividadesProfissionais atividadesProfissionais) {
		this.atividadesProfissionais = atividadesProfissionais;
	}

	public List<AtividadesProfissionais> getListaAtividades() {
		return listaAtividades;
	}

	public void setListaAtividades(List<AtividadesProfissionais> listaAtividades) {
		this.listaAtividades = listaAtividades;
	}

	public Boolean getBotaoAtividades() {
		return botaoAtividades;
	}

	public void setBotaoAtividades(Boolean botaoAtividades) {
		this.botaoAtividades = botaoAtividades;
	}

	public Boolean getStatusBoolean() {
		return statusBoolean;
	}

	public void setStatusBoolean(Boolean statusBoolean) {
		this.statusBoolean = statusBoolean;
	}

	public Boolean getBotaoInfo() {
		return botaoInfo;
	}

	public void setBotaoInfo(Boolean botaoInfo) {
		this.botaoInfo = botaoInfo;
	}

	public Boolean getTelaEditar() {
		return telaEditar;
	}

	public void setTelaEditar(Boolean telaEditar) {
		this.telaEditar = telaEditar;
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

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
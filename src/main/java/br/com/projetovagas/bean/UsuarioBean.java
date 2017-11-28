package br.com.projetovagas.bean;

import java.io.Serializable;
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

@SuppressWarnings("serial")
@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {

	Usuario usuarioLogado = LoginBean.getUsuarioLogado();
	private Usuario usuario = new Usuario();

	UsuarioDAO dao;
	String NomeTest;

	private Boolean statusBoolean = false;

	FormacaoAcademica formacaoAcademica;
	Boolean botaoFormacao = false;
	List<FormacaoAcademica> listaFormacao;
	FormacaoAcademicaDAO formacaoAcademicaDAO;

	private ExperienciaProfissional experienciaProfissional;
	private Boolean botaoExperiencia = false;
	private ExperienciaProfissionalDAO daoExperiencia;
	private List<ExperienciaProfissional> listaExperiencia;

	private AtividadesProfissionais atividadesProfissionais;
	private Boolean botaoAtividades = false;
	private AtividadesProfissionaisDAO daoAtividades;
	private List<AtividadesProfissionais> listaAtividades;

	private Estado estado;
	private List<Estado> listaEstado;
	private String auxEstado = " Selecione um Estado";

	private CidadeDAO cidadeDAO;

	private String auxCidade = "Selecione uma Cidade";
	private List<Cidade> listaCidade;
	Cidade cidade = new Cidade();

	private Boolean telaEditar = false;
	private Boolean botaoEditar = false;
	private Boolean botaoSalvar = false;

	// Editar usuário
	// -------------------------------------------------------------------------------------------

	@PostConstruct
	public void inicia() {
	

		auxCidade = LoginBean.getUsuarioLogado().getCidade().getNome();
		auxEstado = LoginBean.getUsuarioLogado().getCidade().getEstado().getNome();
		listaEstado = LoginBean.getListaEstado();

	}

	public void carregarCurriculo() {

		System.out.println("acionado ..........................................xxxxxxxxxxxxxx");

		formacaoAcademicaDAO = new FormacaoAcademicaDAO();
		formacaoAcademica = new FormacaoAcademica();
		listaFormacao = formacaoAcademicaDAO.listar();
		carregarFormacao();

		daoExperiencia = new ExperienciaProfissionalDAO();
		experienciaProfissional = new ExperienciaProfissional();
		listaExperiencia = daoExperiencia.listar();
		carregarExperiencia();

		daoAtividades = new AtividadesProfissionaisDAO();
		atividadesProfissionais = new AtividadesProfissionais();
		listaAtividades = daoAtividades.listar();
		carregarAtividades();

	}

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

	// Fechar
	// -------------------------------------------------------------------------------------------
	public void fechar() {

		RequestContext.getCurrentInstance().reset("dialogform");
		usuario = new Usuario();
		dao = new UsuarioDAO();

		System.out.println("Método fechar");

	}
	
	// Editar usuário
	// -------------------------------------------------------------------------------------------
	public void editar() {

		try {

			System.out.println("inicio XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			UsuarioDAO dao = new UsuarioDAO();
			dao.editar(usuarioLogado);
			Messages.addGlobalInfo("Usuário(a) ' " + usuarioLogado.getNome() + "' Editado com sucesso!!!");
			System.out.println("Fim XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

		} catch (Exception e) {

			Messages.addGlobalError("Erro ao Editar Usuário(a) '" + usuarioLogado.getNome() + "'");
			System.out.println("------------- Erro Message: " + e.getMessage());
			System.out.println("------------- Erro StackTrace: " + e.getStackTrace());

		} finally {

		}

	}

	// Salvar Senha
	// -------------------------------------------------------------------------------------------
	public void editarSenha() {

		try {

			// Cria um hash e criptografa a senha
			SimpleHash hash = new SimpleHash("md5", usuarioLogado.getSenhaSemCriptografia());
			usuarioLogado.setSenha(hash.toHex());
			dao = new UsuarioDAO();
			dao.merge(usuarioLogado);
			Messages.addGlobalInfo("Usuário Editado com sucesso: " + usuarioLogado.getNome());

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar: " + usuarioLogado.getNome());

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

	// Salvar Formação
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void salvarFormacao() {

		try {
			if (botaoFormacao = true) {

				if (statusBoolean.equals(true)) {
					formacaoAcademica.setStatus("Concluído");
				} else {
					formacaoAcademica.setStatus("Incompleto");
				}

				if (formacaoAcademica.getNomeCurso().trim().isEmpty()
						|| formacaoAcademica.getInstituicao().trim().isEmpty()) {

					Messages.addGlobalWarn("Preencha os campos corretamente.");
					carregarFormacao();

				} else {

					formacaoAcademica.setUsuario(usuarioLogado);
					formacaoAcademicaDAO.merge(formacaoAcademica);

					Messages.addGlobalInfo("Formação  salva com sucesso: " + formacaoAcademica.getNomeCurso());
					carregarFormacao();
				}
			}
		} catch (Exception e) {
			Messages.addGlobalError("Não foi possível salvar a formação, Preencha os campos corretamente. ");

		} finally {

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

	// Excluir Formacao
	// -------------------------------------------------------------------------------------------
	public void excluirFormacao(ActionEvent evento) {

		try {

			formacaoAcademica = (FormacaoAcademica) evento.getComponent().getAttributes().get("meuSelect");

			Messages.addGlobalInfo("Formação removida com sucesso: " + formacaoAcademica.getNomeCurso());
			formacaoAcademicaDAO.excluir(formacaoAcademica);
			carregarFormacao();

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Remover: " + formacaoAcademica.getNomeCurso());

		} finally {

		}

	}

	// -------------------------------------------------------------------------------------------
	// Formação Academica
	// ----------------------------------------------------------

	public void carregarFormacao() {

		try {
			formacaoAcademica = new FormacaoAcademica();
			formacaoAcademicaDAO = new FormacaoAcademicaDAO();
			listaFormacao = formacaoAcademicaDAO.buscarPorUsuario(usuarioLogado.getCodigo());

			if (listaFormacao.size() < 7) {
				botaoFormacao = true;

			} else {
				botaoFormacao = false;
				Messages.addGlobalWarn("Numéro maximo de Formações atingido. (max = 7)");

			}

		} catch (Exception e) {
			Messages.addGlobalError("Falha ao tentar  atualizadar a lista  ");
		}
	}

	// Fim Formação Academica
	// ----------------------------------------------------------

	// Salvar Experiência
	// -------------------------------------------------------------------------------------
	public void salvarExperiencia() {

		try {
			if (botaoExperiencia = true) {

				if (experienciaProfissional.getNomeEmpresa().trim().isEmpty()
						|| experienciaProfissional.getCargo().trim().isEmpty()) {

					Messages.addGlobalWarn("Preencha os campos corretamente.");
					carregarExperiencia();

				} else {

					experienciaProfissional.setUsuario(usuarioLogado);
					daoExperiencia.merge(experienciaProfissional);
					Messages.addGlobalInfo("Experiencia  salva com sucesso: " + experienciaProfissional.getCargo());
					carregarExperiencia();
				}
			}

		} catch (Exception e) {
			Messages.addGlobalError("Não foi possível salvar a formação, Preencha os campos corretamente. ");

		} finally {

		}
	}
	// Carregar Experiência
	// -------------------------------------------------------------------------------------------

	public void carregarExperiencia() {

		// Experiência
		// ----------------------------------------------------------
		try {

			experienciaProfissional = new ExperienciaProfissional();
			daoExperiencia = new ExperienciaProfissionalDAO();
			listaExperiencia = daoExperiencia.buscarPorUsuario(usuarioLogado.getCodigo());

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

	// Excluir Exp
	// -------------------------------------------------------------------------------------------
	public void excluirExperiencia(ActionEvent evento) {

		try {

			experienciaProfissional = (ExperienciaProfissional) evento.getComponent().getAttributes().get("meuSelect");

			ExperienciaProfissionalDAO dao = new ExperienciaProfissionalDAO();
			Messages.addGlobalInfo("Experiência removida com sucesso: " + experienciaProfissional.getCargo());
			dao.excluir(experienciaProfissional);
			carregarExperiencia();

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Remover: " + experienciaProfissional.getCargo());

		} finally {

		}

	}

	// Carregar Curriculo
	// -------------------------------------------------------------------------------------------
	public void carregarAtividades() {

		// Atividades Profissionais
		// ----------------------------------------------------------

		try {

			atividadesProfissionais = new AtividadesProfissionais();
			daoAtividades = new AtividadesProfissionaisDAO();
			listaAtividades = daoAtividades.buscarPorUsuario(usuarioLogado.getCodigo());

			if (listaAtividades.size() < 10) {
				botaoAtividades = true;

			} else {
				botaoAtividades = false;
				Messages.addGlobalWarn("Numéro maximo de Qualificações atingido. (max	 = 10)");

			}

		} catch (Exception e) {
			Messages.addGlobalError("Falha ao tentar atualizadar a lista ");
		}

		// Fim Atividades Profissionais
		// ----------------------------------------------------------

	}

	// Salvar Atividades
	// -------------------------------------------------------------------------------------
	public void salvarAtividades() {

		try {
			if (botaoAtividades = true) {

				if (atividadesProfissionais.getNomeCurso().trim().isEmpty()
						|| atividadesProfissionais.getInstituicao().trim().isEmpty()) {
					Messages.addGlobalWarn("Preencha os campos corretamente.");
					carregarAtividades();
				} else {

					atividadesProfissionais.setUsuario(usuarioLogado);
					daoAtividades.merge(atividadesProfissionais);
					Messages.addGlobalInfo(
							"Qualificação  salva com sucesso: " + atividadesProfissionais.getNomeCurso());
					carregarAtividades();
				}
			}

		} catch (Exception e) {
			Messages.addGlobalError("Não foi possível salvar a Qualificação, Preencha os campos corretamente. ");

		} finally {

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

	// Excluir Qualificação
	// -------------------------------------------------------------------------------------------
	public void excluirQualificacao(ActionEvent evento) {

		try {

			atividadesProfissionais = (AtividadesProfissionais) evento.getComponent().getAttributes().get("meuSelect");

			AtividadesProfissionaisDAO dao = new AtividadesProfissionaisDAO();
			Messages.addGlobalInfo("Qualificação removida com sucesso: " + atividadesProfissionais.getNomeCurso());
			dao.excluir(atividadesProfissionais);
			carregarAtividades();

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Remover: " + atividadesProfissionais.getNomeCurso());

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

	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public String getNomeTest() {
		return NomeTest;
	}

	public void setNomeTest(String nomeTest) {
		NomeTest = nomeTest;
	}

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

	public FormacaoAcademica getFormacaoAcademica() {
		return formacaoAcademica;
	}

	public void setFormacaoAcademica(FormacaoAcademica formacaoAcademica) {
		this.formacaoAcademica = formacaoAcademica;
	}

	public Boolean getBotaoFormacao() {
		return botaoFormacao;
	}

	public void setBotaoFormacao(Boolean botaoFormacao) {
		this.botaoFormacao = botaoFormacao;
	}

	public List<FormacaoAcademica> getListaFormacao() {
		return listaFormacao;
	}

	public void setListaFormacao(List<FormacaoAcademica> listaFormacao) {
		this.listaFormacao = listaFormacao;
	}

	public Boolean getStatusBoolean() {
		return statusBoolean;
	}

	public void setStatusBoolean(Boolean statusBoolean) {
		this.statusBoolean = statusBoolean;
	}

	public ExperienciaProfissional getExperienciaProfissional() {
		return experienciaProfissional;
	}

	public void setExperienciaProfissional(ExperienciaProfissional experienciaProfissional) {
		this.experienciaProfissional = experienciaProfissional;
	}

	public Boolean getBotaoExperiencia() {
		return botaoExperiencia;
	}

	public void setBotaoExperiencia(Boolean botaoExperiencia) {
		this.botaoExperiencia = botaoExperiencia;
	}

	public List<ExperienciaProfissional> getListaExperiencia() {
		return listaExperiencia;
	}

	public void setListaExperiencia(List<ExperienciaProfissional> listaExperiencia) {
		this.listaExperiencia = listaExperiencia;
	}

	public AtividadesProfissionais getAtividadesProfissionais() {
		return atividadesProfissionais;
	}

	public void setAtividadesProfissionais(AtividadesProfissionais atividadesProfissionais) {
		this.atividadesProfissionais = atividadesProfissionais;
	}

	public Boolean getBotaoAtividades() {
		return botaoAtividades;
	}

	public void setBotaoAtividades(Boolean botaoAtividades) {
		this.botaoAtividades = botaoAtividades;
	}

	public List<AtividadesProfissionais> getListaAtividades() {
		return listaAtividades;
	}

	public void setListaAtividades(List<AtividadesProfissionais> listaAtividades) {
		this.listaAtividades = listaAtividades;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean getTelaEditar() {
		return telaEditar;
	}

	public void setTelaEditar(Boolean telaEditar) {
		this.telaEditar = telaEditar;
	}

	public Boolean getBotaoEditar() {
		return botaoEditar;
	}

	public void setBotaoEditar(Boolean botaoEditar) {
		this.botaoEditar = botaoEditar;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Boolean getBotaoSalvar() {
		return botaoSalvar;
	}

	public void setBotaoSalvar(Boolean botaoSalvar) {
		this.botaoSalvar = botaoSalvar;
	}


	

}
package br.com.projetovagas.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.omnifaces.util.Messages;

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

	// Editar usuário
	// -------------------------------------------------------------------------------------------

	@PostConstruct
	public void inicia() {

		auxCidade = LoginBean.getUsuarioLogado().getCidade().getNome();
		auxEstado = LoginBean.getUsuarioLogado().getCidade().getEstado().getNome();
		listaEstado = LoginBean.getListaEstado();
		
		formacaoAcademicaDAO = new FormacaoAcademicaDAO();
		formacaoAcademica = new FormacaoAcademica();
		listaFormacao = formacaoAcademicaDAO.listar();
		
		daoExperiencia = new ExperienciaProfissionalDAO();
		experienciaProfissional = new ExperienciaProfissional();
		listaExperiencia = daoExperiencia.listar();
		
		daoAtividades = new AtividadesProfissionaisDAO();
		atividadesProfissionais = new AtividadesProfissionais();
		listaAtividades = daoAtividades.listar();

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

	public void carregarExperiencia(){
		
		
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
			 listaAtividades =
			 daoAtividades.buscarPorUsuario(usuarioLogado.getCodigo());
			
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
					
					
					if(atividadesProfissionais.getNomeCurso().trim().isEmpty() || atividadesProfissionais.getInstituicao().trim().isEmpty() ){
						Messages.addGlobalWarn("Preencha os campos corretamente.");
						carregarAtividades();
					}else{
					
					atividadesProfissionais.setUsuario(usuarioLogado);
					daoAtividades.merge(atividadesProfissionais);
					Messages.addGlobalInfo("Qualificação  salva com sucesso: " + atividadesProfissionais.getNomeCurso());
					carregarAtividades();
				}}

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
	
	
	

}
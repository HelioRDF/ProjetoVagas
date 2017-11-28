package br.com.projetovagas.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

import br.com.projetovagas.dao.complementos.FichaDAO;
import br.com.projetovagas.dao.complementos.OportunidadeDAO;
import br.com.projetovagas.dao.empresas.EmpresaDAO;
import br.com.projetovagas.dao.localizacao.CidadeDAO;
import br.com.projetovagas.dao.localizacao.EstadoDAO;
import br.com.projetovagas.domain.complementos.Ficha;
import br.com.projetovagas.domain.complementos.Oportunidade;
import br.com.projetovagas.domain.empresas.Empresa;
import br.com.projetovagas.domain.localizacao.Cidade;
import br.com.projetovagas.domain.localizacao.Estado;

@ManagedBean
@SessionScoped
public class OportunidadeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Oportunidade oportunidade;
	private OportunidadeDAO dao;
	private List<Oportunidade> listaOportunidade;
	private List<Oportunidade> minhasOportunidade;
	private String filtrarCargo = "";
	private int totalVagas;
	private int minhasVagas;

	private Estado estado;
	private EstadoDAO estadoDAO;
	private List<Estado> listaEstado = LoginBean.getListaEstado();
	private String auxEstado = " Selecione um Estado";
	private Long meuEstado;

	private CidadeDAO cidadeDAO;
	private String auxCidade = "Selecione uma Cidade";
	private List<Cidade> listaCidade;
	private Long minhaCidade;

	private EmpresaDAO empresaDAO;
	private List<Empresa> listaEmpresas;

	private Boolean botaoEditar = false;
	private Boolean botaoSalvar = false;

	private int filtrarSalario = 0;
	private String filtrarNivel = "";
	private String filtrarModalidade = "";
	private int FiltrarPcD = 3;
	
	private String resposta="";
	private Ficha registro ;
	

	// Salvar usuário
	// -------------------------------------------------------------------------------------
	public void salvar() {

		try {
			

			System.out.println("Método salvar");
			

			if (!oportunidade.getMostrarSalario()) {
				oportunidade.setSalarioAux(new BigDecimal(1.00));
			} else {
				oportunidade.setSalarioAux(oportunidade.getSalario().setScale(2));
			}

			oportunidade.setDataCadastro(new Date());
			oportunidade.setEstado(estado);

			dao.salvar(oportunidade);
			Messages.addGlobalInfo("Salvo com sucesso.");

		} catch (Exception e) {
			Messages.addGlobalError("Não foi possível salvar o usuário, tente novamente mais tarde ... ");
		} finally {

			fechar();

		}
	}

	// Novo
	// -------------------------------------------------------------------------------------------

	public void novo()  {

		botaoEditar = false;
		botaoSalvar = true;
		listarInfos();
		oportunidade = new Oportunidade();
		dao = new OportunidadeDAO();
	
	}

	// Fechar
	// -------------------------------------------------------------------------------------------
	public void fechar() {
		RequestContext.getCurrentInstance().reset("dialogform");
		oportunidade = new Oportunidade();
		dao = new OportunidadeDAO();
		auxCidade = "Selecione uma Cidade";
		auxEstado = " Selecione um Estado";
	}

	// CarregarFiltrando
	// -------------------------------------------------------------------------------------------

	
	public void carregarFiltrando() {
		
		System.out.println("-------------------------Filtrando");

		try {
			dao = new OportunidadeDAO();
			
			listaOportunidade = (List<Oportunidade>) dao.buscarVagas(filtrarCargo, meuEstado, minhaCidade, filtrarSalario, filtrarNivel,
			filtrarModalidade, FiltrarPcD);
		
			
			totalVagas = listaOportunidade.size();
			Messages.addGlobalInfo("Lista atualizada com sucesso ");
			
			
		
			
		} catch (Exception e) {
			Messages.addGlobalError("Falha ao tentar  atualizadar a lista  ");
		} finally {

		}

	}
	
	
	
	// CarregarFiltrando
		// -------------------------------------------------------------------------------------------

		public void carregarMinhasOportunidades() {

			try {
				dao = new OportunidadeDAO();

				minhasOportunidade = dao.buscarMinhasOportunidades();
				minhasVagas = minhasOportunidade.size();
				System.out.println("Metodo retorna..."+minhasOportunidade);
				Messages.addGlobalInfo("Lista atualizada com sucesso ");
				
			
				
			} catch (Exception e) {
				Messages.addGlobalError("Falha ao tentar  atualizadar a lista  ");
			} finally {

			}

		}
	
	
	

	// Excluir usuário
	// -------------------------------------------------------------------------------------------
	public void excluir(ActionEvent evento) {

		try {

			oportunidade = (Oportunidade) evento.getComponent().getAttributes().get("meuSelect");
			OportunidadeDAO dao = new OportunidadeDAO();
			Messages.addGlobalInfo("Removido com sucesso!!!");
			dao.excluir(oportunidade);

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Remover: ");

		} finally {
			fechar();
		}

	}

	// Editar Oportunidade
	// -------------------------------------------------------------------------------------------
	public void editar() {

		try {

			if (!oportunidade.getMostrarSalario()) {
				oportunidade.setSalarioAux(new BigDecimal(1.00));

			} else {
				oportunidade.setSalarioAux(oportunidade.getSalario().setScale(2));
			}

			dao = new OportunidadeDAO();
			oportunidade.setEstado(estado);
			dao.merge(oportunidade);
			Messages.addGlobalInfo(" Editado com sucesso!!!");

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar ");

		} finally {
			fechar();
		}

	}

	// Instanciar
	// -------------------------------------------------------------------------------------------

	public void getinstancia(ActionEvent evento) {

		try {

			botaoSalvar = false;
			botaoEditar = true;
			listarInfos();

			oportunidade = (Oportunidade) evento.getComponent().getAttributes().get("meuSelect");
			auxEstado = oportunidade.getEstado().getNome();
			auxCidade = oportunidade.getCidade().getNome();

			Messages.addGlobalInfo("Vaga de ID:  " + oportunidade.getCodigo());

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar: ");

		}

	}

	// Lista as empresas
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void listarInfos() {

		try {

			empresaDAO = new EmpresaDAO();
			listaEmpresas = empresaDAO.listar();

			estadoDAO = new EstadoDAO();
			listaEstado = estadoDAO.listar("nome");

		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}

	}

	// Listar Estado
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	@PostConstruct
	public void BuscarEstados() {

		try {
			
			CidadeDAO cidadeDAO = new CidadeDAO();
			meuEstado = LoginBean.getUsuarioLogado().getCidade().getEstado().getCodigo();
			listaCidade = cidadeDAO.buscarPorEstado(meuEstado);

			minhaCidade = LoginBean.getUsuarioLogado().getCidade().getCodigo();
			dao = new OportunidadeDAO();
			
			auxCidade = LoginBean.getUsuarioLogado().getCidade().getNome();
			auxEstado = LoginBean.getUsuarioLogado().getCidade().getEstado().getNome();

			listaOportunidade = (List<Oportunidade>) dao.buscarVagas(filtrarCargo, meuEstado, minhaCidade, filtrarSalario, filtrarNivel, filtrarModalidade, FiltrarPcD);
			//listaOportunidade = (List<Oportunidade>) dao.buscarTodasOportunidades();
			
			
			totalVagas = listaOportunidade.size();
			Messages.addGlobalInfo("Lista atualizada com sucesso ");

		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}

	}

	// Listar de Cidade
	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	public void buscarCidade() {

		try {

			CidadeDAO cidadeDAO = new CidadeDAO();
			listaCidade = cidadeDAO.buscarPorEstado(meuEstado);

		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}

	}

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

	// Filtrar Cidade 2, precisei replicar o método, devido a um erro na linha
	// quando chamada dentro do getinstance, por conta do param (actionevent)
	
	
	
	
	// Seleciona uma vaga da table
		// -------------------------------------------------------------------------------------------

		public void selecionarVaga(ActionEvent evento) {

			try {

				oportunidade = (Oportunidade) evento.getComponent().getAttributes().get("meuSelect");

				Messages.addGlobalInfo("Cargo:  " +oportunidade.getCargo() +" - ( ID: "+  oportunidade.getCodigo()+")");

			} catch (Exception e) {
				Messages.addGlobalError("Erro ao Editar: ");

			}

		}

		// Seleciona uma vaga da table
		// -------------------------------------------------------------------------------------------

		public void candidatar() {

			try {
				
				
				Ficha ficha = new Ficha();
				ficha.setCandidato_id(LoginBean.getUsuarioLogado());
				ficha.setDataCadastro(new Date());
				ficha.setResposta(resposta);
				ficha.setOportunidade_id(oportunidade);
				
				FichaDAO dao = new FichaDAO();
				dao.merge(ficha);

				Messages.addGlobalInfo("Ficha enviada com Sucesso!");
				carregarFiltrando();
				resposta=null;

			} catch (Exception e) {
				Messages.addGlobalError("Erro ao enviar ficha ");

			}

		}

	
	
	// ------------------------------------------------------------------------------------------------------------------------------------------------------
	
		public void carregarRegistro(ActionEvent evento){
				
			oportunidade = (Oportunidade) evento.getComponent().getAttributes().get("meuSelect");
			FichaDAO dao = new FichaDAO();
			registro = new Ficha();			
			registro = dao.buscarRegistro(oportunidade);
			
			
		}
		
	// ------------------------------------------------------------------------------------------------------------------------------------------------------
		
	
	
	
	public Boolean getBotaoEditar() {
		return botaoEditar;
	}

	public Oportunidade getOportunidade() {
		return oportunidade;
	}

	public void setOportunidade(Oportunidade oportunidade) {
		this.oportunidade = oportunidade;
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

	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public void setListaEmpresas(List<Empresa> listaEmpresas) {
		this.listaEmpresas = listaEmpresas;
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

	public String getFiltrarCargo() {
		return filtrarCargo;
	}

	public void setFiltrarCargo(String filtrarCargo) {
		this.filtrarCargo = filtrarCargo;
	}



	public Long getMeuEstado() {
		return meuEstado;
	}

	public void setMeuEstado(Long meuEstado) {
		this.meuEstado = meuEstado;
	}

	public Long getMinhaCidade() {
		return minhaCidade;
	}

	public void setMinhaCidade(Long minhaCidade) {
		this.minhaCidade = minhaCidade;
	}

	public int getFiltrarSalario() {
		return filtrarSalario;
	}

	public void setFiltrarSalario(int filtrarSalario) {
		this.filtrarSalario = filtrarSalario;
	}

	public String getFiltrarNivel() {
		return filtrarNivel;
	}

	public void setFiltrarNivel(String filtrarNivel) {
		this.filtrarNivel = filtrarNivel;
	}

	public String getFiltrarModalidade() {
		return filtrarModalidade;
	}

	public void setFiltrarModalidade(String filtrarModalidade) {
		this.filtrarModalidade = filtrarModalidade;
	}

	public int getFiltrarPcD() {
		return FiltrarPcD;
	}

	public void setFiltrarPcD(int filtrarPcD) {
		FiltrarPcD = filtrarPcD;
	}



	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public List<Oportunidade> getMinhasOportunidade() {
		return minhasOportunidade;
	}

	public void setMinhasOportunidade(List<Oportunidade> minhasOportunidade) {
		this.minhasOportunidade = minhasOportunidade;
	}

	public int getTotalVagas() {
		return totalVagas;
	}

	public void setTotalVagas(int totalVagas) {
		this.totalVagas = totalVagas;
	}

	public int getMinhasVagas() {
		return minhasVagas;
	}

	public void setMinhasVagas(int minhasVagas) {
		this.minhasVagas = minhasVagas;
	}

	public Ficha getRegistro() {
		return registro;
	}

	public void setRegistro(Ficha registro) {
		this.registro = registro;
	}

	public List<Oportunidade> getListaOportunidade() {
		return listaOportunidade;
	}

	public void setListaOportunidade(List<Oportunidade> listaOportunidade) {
		this.listaOportunidade = listaOportunidade;
	}

	
	
	

	
	
	
	

}
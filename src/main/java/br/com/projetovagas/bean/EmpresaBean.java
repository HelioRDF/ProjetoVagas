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

import br.com.projetovagas.dao.empresas.EmpresaDAO;
import br.com.projetovagas.dao.localizacao.CidadeDAO;
import br.com.projetovagas.dao.localizacao.EstadoDAO;
import br.com.projetovagas.domain.empresas.Empresa;
import br.com.projetovagas.domain.localizacao.Cidade;
import br.com.projetovagas.domain.localizacao.Estado;



/**
 * [ Detalhes... ]
 * 
 * 
 * -Mensagen |FacesContext.getCurrentInstance().addMessage(null,new
 * |FacesMessage(FacesMessage.SEVERITY_WARN, "A confirmação de |senha está
 * incorreta", null));
 */


@ManagedBean
@SessionScoped
public class EmpresaBean implements Serializable {



	private static final long serialVersionUID = 1L;
	
	private Empresa empresa;
	private Estado estado;
	Cidade cidade = new Cidade();
	Cidade cidadeAux = new Cidade();
	
	private EmpresaDAO dao;
	private CidadeDAO cidadeDao;
	private EstadoDAO estadoDao;
	
	private List<Empresa> listaEmpresa;
	private List<Cidade> listaCidade;
	private List<Estado> listaEstado;
	
	private String auxCidade="Selecione uma Cidade";	
	private String auxEstado=" Selecione um Estado";
	
	
	
	

	private Boolean botaoEditar =false;
	private Boolean botaoSalvar =false;
	private Boolean telaEditar =false;

	
	
	
	
	

	// Salvar
	// -------------------------------------------------------------------------------------
	public void salvar() {
		
		Boolean permitir = dao.validarEmail(empresa.getEmail());
		if (!permitir) {

			 Messages.addGlobalError("O Endereço de e-mail já existe ... ");

			return;

		}

		try {
			
			//Cria um hash e criptografa a senha
			SimpleHash hash = new SimpleHash("md5", empresa.getSenhaSemCriptografia());
			empresa.setSenha(hash.toHex());			
			empresa.setDataCadastro(new Date());
			
			empresa.setCidade(cidade);
			
			
			dao.salvar(empresa);
			Messages.addGlobalInfo("Empresa " + empresa.getNomeEmpresa() + ", salva com sucesso.");

		} catch (Exception e) {
			System.out.println("--------------- Catch: " + e.getCause());
			System.out.println("--------------- Mensagem: " + e.getMessage());
			Messages.addGlobalError("Não foi possível salvar a empresa, tente novamente mais tarde ... ");
		} finally {



			System.out.println("Finally: ");
			fechar();

		}
	}

	// Novo
	// -------------------------------------------------------------------------------------------
	public void novo() {

		listarInfos();
		telaEditar=false;
		botaoEditar=false;
		botaoSalvar=true;
	
		empresa = new Empresa();
		dao = new EmpresaDAO();
		
		auxCidade="Selecione uma Cidade";
		auxEstado=" Selecione um Estado";
		
		System.out.println("Método novo");

	}

	// Fechar
	// -------------------------------------------------------------------------------------------
	public void fechar() {
		RequestContext.getCurrentInstance().reset("dialogform");
		System.out.println("Método Fechar");
		empresa = new Empresa();
		dao = new EmpresaDAO();
	
	}

	// -------------------------------------------------------------------------------------------
	public void carregar() {

		try {
			
			dao = new EmpresaDAO();
			listaEmpresa = dao.listar();

			Messages.addGlobalInfo("Lista atualizada com sucesso ");

		} catch (Exception e) {
			Messages.addGlobalError("Falha ao tentar  atualizadar a lista  ");
		} finally {
			fechar();
		}

	}

	// Excluir
	// -------------------------------------------------------------------------------------------
	public void excluir(ActionEvent evento) {

		try {

			empresa = (Empresa) evento.getComponent().getAttributes().get("meuSelect");
			EmpresaDAO dao = new EmpresaDAO();
			Messages.addGlobalInfo("Empresa ' " + empresa.getNomeEmpresa() + "' Removida com sucesso!!!");
			dao.excluir(empresa);

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Remover: " + empresa.getNomeEmpresa());

		} finally {
			fechar();
		}

	}

	// Editar empresa
	// -------------------------------------------------------------------------------------------
	public void editar() {
		
		Long id = empresa.getCodigo();
		Boolean permitir =dao.validarEmail(empresa.getEmail(),id);
		
			
		 if(!permitir){
	
		 Messages.addGlobalError("O Endereço de e-mail já existe ... ");
		 listaEmpresa = dao.listar();
		
		 return;
		
		 }
		
		try {
				
			dao = new EmpresaDAO();
			
			
			if(cidade==null){
				cidade=cidadeAux;
			}
			System.out.println("\nEditar");
			System.out.println("Cidade >>>"+cidade);
			System.out.println("CidadeAux >>>"+cidade);
			
			
			empresa.setCidade(cidade);
			dao.editar(empresa);
			
			Messages.addGlobalInfo("Empresa: '" + empresa.getNomeEmpresa() + "' Editado com sucesso!!!");

		} catch (NullPointerException e) {
			

			Messages.addGlobalError("Erro ao Editar (Método editar)'" + empresa.getNomeEmpresa() + "'");
	

		} finally {

			
			fechar();

		}

	}

	// Salvar Senha
	// -------------------------------------------------------------------------------------------
	public void editarSenha() {

		try {
			
			
			//Cria um hash e criptografa a senha
			SimpleHash hash = new SimpleHash("md5", empresa.getSenhaSemCriptografia());
			empresa.setSenha(hash.toHex());
			dao = new EmpresaDAO();
			dao.merge(empresa);
			Messages.addGlobalInfo("Usuário Editado com sucesso: " + empresa.getNomeEmpresa());

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar: " + empresa.getNomeEmpresa());

		} finally {

			fechar();

		}

	}

	// Instanciar
	// -------------------------------------------------------------------------------------------

	
	public String getinstancia(ActionEvent evento) {

		try {

			
			
		
			empresa = (Empresa) evento.getComponent().getAttributes().get("meuSelect");
			System.out.println("GetInstancia");
						
		
			
			

			
			Messages.addGlobalInfo("Seleção: " + empresa.getNomeEmpresa());
			return "xxxx";
			

		} catch (Exception e) {
			Messages.addGlobalError("Erro ao Editar: " + empresa.getNomeEmpresa());
			return "xxxx";

		}
		finally{
			
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
				
				empresa = (Empresa) evento.getComponent().getAttributes().get("meuSelect");
				
				

				long num = cidDAO.buscarOBJCidadeEmpresa(empresa);
				System.out.println("Num:"+num);
	
				cid = cidDAO.buscarObjCidadePorCodigo(num);
				System.out.println("Cid:"+cid);
				
				if(cid==null){
					
					auxCidade="Selecione uma Cidade";
					auxEstado=" Selecione um Estado";
					
				}else {
				auxCidade = cid.getNome();
				auxEstado = cid.getEstado().getNome();
				cidadeAux=cid;
				}
				
			
				System.out.println("Estado Aux:"+auxEstado);
				System.out.println("Cidade Aux:"+auxCidade);
				
				
			} catch (Exception e) {
				System.out.println("Erro no posGET");
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

	// ------------------------------------------------------------------------------------------------------------------------------------------------------

	
	
	public void filtrarCidade(){
		
		try {
			
			cidadeDao = new CidadeDAO();
			listaCidade = cidadeDao.buscarPorEstado(estado.getCodigo());	
			auxCidade="Selecione uma Cidade";			
			
			
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

	
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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

	public EmpresaDAO getDao() {
		return dao;
	}

	public void setDao(EmpresaDAO dao) {
		this.dao = dao;
	}
	

	public List<Empresa> getListaEmpresa() {
		return listaEmpresa;
	}

	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
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

	
	


	 
	
	
	
	
	

	// ------------------------------------------------------------------------------------------------------------------------------------------------------

}

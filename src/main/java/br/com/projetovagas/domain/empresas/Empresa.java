package br.com.projetovagas.domain.empresas;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.projetovagas.domain.complementos.GenericDomain;
import br.com.projetovagas.domain.localizacao.Cidade;


/**
 * [ Detalhes... ]
 * 
 * -Entity - Diz que a classe é uma entidade do hibernate
 * 
 * -Column(param)
 * | length = Tamanho do campo 
 * | name = define o nome no banco 
 * | nullable = Diz se o campo pode ou não ser nulo "True ou False"
 * | precision = Define quantidade de digitos
 * | scale= Define quantos digitos ficam após a virgula 
 * 
 * -JoinColumn - Permite personalizar colunas que são chaves estrangeiras
 * 
 * -Sobre o CNPJ:
 *  Tem 14 números.
 *  Possui o seguinte formato:
 *  "XX.XXX.XXX/0001-XX"
 *  {@link http://atendimento.contadorx.com/hc/pt-br/articles/205698465-O-que-%C3%A9-CNPJ-}
 * 
 *  -Temporal(TemporalType.param)
 *  |DATE=Data
 *  |Time=Hora
 *  |TIMESTAMP=Data e hora
 *  
 *  -MD5 - A senha está utilizando MD5 para criptografia.
 * 
 *  -Atributos:
 * 	dataCadastro - status - nomeEmpresa - senha - seguimento - descrição  
 *  cnpj - rua - numero - bairro - cep - complemento - telefone - email
 *
 */


@SuppressWarnings("serial")
@Entity
public class Empresa extends GenericDomain {

	@Column()
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	@Column()
	private Boolean status;

	@Column(length = 65)
	private String nomeEmpresa;

	@Column(length = 32)
	private String senha;
	
	@Transient
	private String senhaSemCriptografia;

	@Column(length = 30)
	private String seguimento;

	@Column(length = 2000)
	@Lob
	private String descricao;

	@Column(length = 22)
	private String cnpj;
	
	@Column(length = 33,unique=true)
	private String email;
	

	@JoinColumn
	@OneToOne
	private Cidade cidade;

	@Column(length = 95)
	private String rua;

	@Column(length = 6)
	private String numero;

	@Column(length = 95)
	private String bairro;

	@Column(length = 11)
	private String cep;

	@Column(length = 90)
	private String complemento;

	@Column(length = 20)
	private String telefone;

	@Column(length = 20)
	private String celular;
	
	

	// -------------------------------------------------------

	
	
	
		
	public Boolean getStatus() {
		
		
		return status;
	}

	public String getSenhaSemCriptografia() {
		return senhaSemCriptografia;
	}

	public void setSenhaSemCriptografia(String senhaSemCriptografia) {
		this.senhaSemCriptografia = senhaSemCriptografia;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getNomeEmpresa() {
		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa) {
		this.nomeEmpresa = nomeEmpresa;
	}

	public String getSeguimento() {
		return seguimento;
	}

	public void setSeguimento(String seguimento) {
		this.seguimento = seguimento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}



	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Empresa [dataCadastro=" + dataCadastro + ", status=" + status + ", nomeEmpresa=" + nomeEmpresa
				+ ", senha=" + senha + ", senhaSemCriptografia=" + senhaSemCriptografia + ", seguimento=" + seguimento
				+ ", descricao=" + descricao + ", cnpj=" + cnpj + ", email=" + email + ", cidade=" + cidade + ", rua="
				+ rua + ", numero=" + numero + ", bairro=" + bairro + ", cep=" + cep + ", complemento=" + complemento
				+ ", telefone=" + telefone + ", celular=" + celular + "]";
	}
	
	

	
	
	

}

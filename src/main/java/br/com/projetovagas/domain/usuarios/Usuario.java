package br.com.projetovagas.domain.usuarios;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
 * -Column(
 * | length = Tamanho do campo 
 * | name = define o nome no banco 
 * | nullable = Diz se o campo pode ou não ser nulo "True ou False");
 * | precision = Define quantidade de digitos
 * | scale= Define quantos digitos ficam após a virgula 
 * 
 * -JoinColumn - Permite personalizar colunas que são chaves estrangeiras
 * 
 *  -Temporal(TemporalType.param)
 *  |DATE=Data
 *  |Time=Hora
 *  |TIMESTAMP=Data e hora
 *  
 *  -MD5 - A senha está utilizando MD5 para criptografia.
 *  
 *  -Atributos
 *  |dataCadastro - status - nome - senha - cpf - rg - dataNascimento - email - curriculo
 * 
 */

@SuppressWarnings("serial")
@Entity
public class Usuario extends GenericDomain {

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimoLogin = new Date();

	@Column(nullable = false)
	private Boolean status;
	
	
	@Column(nullable = false)
	private Boolean admin;
	
	

	@Column(length = 60, nullable = false)
	private String nome;

	@Column(length = 32)
	private String senha;
	
	@Transient
	private String senhaSemCriptografia;

	@Column(length = 14, nullable = false)
	private String cpf;

	@Column(length = 12, nullable = false)
	private String rg;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Column(length = 100, nullable = false, unique=true)
	private String email;

	@Column(length = 20, nullable = false)
	private String sexo;
	
	
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
	
	@Column(length = 100)
	private String cargoPretendido;

	@Column(length = 20)
	private String pretensaoSalarial;
	
	@Column(length = 250)
	private String descricao;
	
	

	

	// -----------------------------------------------------------------------------
	

	

		
	
	
	public Boolean getStatus() {
		return status;
	}


	public String getSenhaSemCriptografia() {
		return senhaSemCriptografia;
	}


	public void setSenhaSemCriptografia(String senhaSemCriptografia) {
		this.senhaSemCriptografia = senhaSemCriptografia;
	}


	public String getCargoPretendido() {
		return cargoPretendido;
	}


	public void setCargoPretendido(String cargoPretendido) {
		this.cargoPretendido = cargoPretendido;
	}


	public String getPretensaoSalarial() {
		return pretensaoSalarial;
	}


	public void setPretensaoSalarial(String pretensaoSalarial) {
		this.pretensaoSalarial = pretensaoSalarial;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	


	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
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


	public Boolean getAdmin() {
		return admin;
	}


	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}


	public Date getUltimoLogin() {
		return ultimoLogin;
	}


	public void setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	
	
	
	

}

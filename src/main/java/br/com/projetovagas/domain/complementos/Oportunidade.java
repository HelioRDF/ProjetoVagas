package br.com.projetovagas.domain.complementos;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.projetovagas.domain.empresas.Empresa;
import br.com.projetovagas.domain.localizacao.Cidade;
import br.com.projetovagas.domain.localizacao.Estado;



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
 */

@SuppressWarnings("serial")
@Entity
public class Oportunidade extends GenericDomain {
	
	
	
	@Column()
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	@Column(nullable = false)
	private String cargo;
	
	
	@JoinColumn
	@OneToOne
	private Estado estado;

	@JoinColumn
	@OneToOne
	private Cidade cidade;
	
	@Column(nullable = false)
	private String horario;
	
	@Column
	private String nivel;	//Junior | Senior | Pleno | Trainee

	@Column(nullable = false)
	@Lob
	private String descricao;
	
	@Column
	@Lob
	private String preRequisitos;
	
	@Column(nullable = false)
	@Lob
	private String beneficios;

	@Column(nullable = false)
	private Short quantidade;

	@Column(nullable = false)
	private BigDecimal salario;
	
	@Column
	private Boolean mostrarSalario;
	
	@Column
	private Boolean mostrarEmpresa;
	
	@Column(name="PcD")
	private Boolean pcd;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Empresa empresa;
	
	@Column
	private String modalidade; //CLT | CLT PCD | PJ | Estágio ???
	
	@Column
	private String area; //MKT | Engenharia | ???
	
	
	//--------------------------------------------------
	
	

	public String getDescricao() {
		return descricao;
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

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getPreRequisitos() {
		return preRequisitos;
	}

	public void setPreRequisitos(String preRequisitos) {
		this.preRequisitos = preRequisitos;
	}



	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Short getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Short quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getSalario() {
		return salario;
	}

	public void setSalario(BigDecimal salario) {
		this.salario = salario;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	
	
	
	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	

	public Boolean getMostrarSalario() {
		return mostrarSalario;
	}

	public void setMostrarSalario(Boolean mostrarSalario) {
		this.mostrarSalario = mostrarSalario;
	}
	
	
	 

	public Boolean getMostrarEmpresa() {
		return mostrarEmpresa;
	}

	public void setMostrarEmpresa(Boolean mostrarEmpresa) {
		this.mostrarEmpresa = mostrarEmpresa;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public String getBeneficios() {
		return beneficios;
	}

	public void setBeneficios(String beneficios) {
		this.beneficios = beneficios;
	}
	

	public Boolean getPcd() {
		return pcd;
	}

	public void setPcd(Boolean pcd) {
		this.pcd = pcd;
	}

	@Override
	public String toString() {
		return "Oportunidade [dataCadastro=" + dataCadastro + ", cargo=" + cargo + ", nivel=" + nivel + ", descricao="
				+ descricao + ", preRequisitos=" + preRequisitos + ", quantidade=" + quantidade + ", salario=" + salario
				+ ", empresa=" + empresa + ", tipo=" + modalidade + ", setor=" + area + "]";
	}
	
	
	
	
	
	

}

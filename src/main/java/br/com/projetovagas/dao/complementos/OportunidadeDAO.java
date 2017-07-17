package br.com.projetovagas.dao.complementos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projetovagas.bean.LoginBean;
import br.com.projetovagas.domain.complementos.Ficha;
import br.com.projetovagas.domain.complementos.Oportunidade;
import br.com.projetovagas.domain.usuarios.Usuario;
import br.com.projetovagas.util.HibernateUtil;

/**
 * [ Detalhes... ] Referencia.
 * http://www.devmedia.com.br/hibernate-api-criteria-realizando-consultas/29627
 * 
 * 
 */

public class OportunidadeDAO extends GenericDAO<Oportunidade> {

	@SuppressWarnings("unchecked")
	public List<Oportunidade> buscarVagas(String cargo, Long estado, Long cidade, int salario, String nivel,
			String modalidade, int pcd) {

		BigDecimal salarioMaior = new BigDecimal(0.00);

		BigDecimal salarioMenor = new BigDecimal(99999.99);

		if (salario == 0) {
			salarioMaior = new BigDecimal(0.00);
			salarioMenor = new BigDecimal(99999.99);
		}

		if (salario == 1) {
			salarioMaior = new BigDecimal(0.00);
			;
			salarioMenor = new BigDecimal(1500.00);
		}

		if (salario == 2) {
			salarioMaior = new BigDecimal(1500.00);
			salarioMenor = new BigDecimal(2000.00);
		}

		if (salario == 3) {
			salarioMaior = new BigDecimal(2000.00);
			salarioMenor = new BigDecimal(2500.00);
		}

		if (salario == 4) {
			salarioMaior = new BigDecimal(2500.00);
			salarioMenor = new BigDecimal(3500.00);
		}

		if (salario == 5) {
			salarioMaior = new BigDecimal(3500.00);
			salarioMenor = new BigDecimal(5000.00);
		}

		if (salario == 6) {
			salarioMaior = new BigDecimal(5000.00);
			salarioMenor = new BigDecimal(99999.99);
		}

		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(Oportunidade.class);
		consulta.add(Restrictions.ilike("cargo", "%" + cargo + "%"));
		consulta.add(Restrictions.eq("estado.codigo", estado));
		consulta.add(Restrictions.eq("cidade.codigo", cidade));

		if (pcd == 1) {

			consulta.add(Restrictions.eq("pcd", true));
		}

		if (pcd == 0) {

			consulta.add(Restrictions.eq("pcd", false));
		}

		// Captura as fichas do usuário e exclui as vagas já cadastradas da
		// busca, utilizando criteria com o id da oportunidade.
		FichaDAO fichaDAO = new FichaDAO();
		List<Ficha> ficha = fichaDAO.buscarFichas(LoginBean.getUsuarioLogado());
		for (Ficha obj : ficha) {
			Long cod = obj.getOportunidade_id().getCodigo();
			consulta.add(Restrictions.ne("codigo", cod));
		}

		consulta.add(Restrictions.ge("salario", salarioMaior));
		consulta.add(Restrictions.lt("salario", salarioMenor));
		consulta.add(Restrictions.ilike("nivel", "%" + nivel + "%"));
		consulta.add(Restrictions.ilike("modalidade", "%" + modalidade + "%"));

		
		
		List<Oportunidade> resultado = (List<Oportunidade>)consulta.list();
		
		
		
		
		for (Oportunidade lista : resultado) {
			System.out.println("Saida de oportunidades: "+ lista.getCodigo());
		}

		sessao.flush();
		return    resultado;

	}
	
	
	public List<Oportunidade> buscarMinhasOportunidades() {
		
		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(Oportunidade.class);
		List<Oportunidade> resultado= new ArrayList<>();
		Oportunidade oportunidade = new Oportunidade();
		
		// Captura as fichas do usuário e busca as vagas já cadastradas.
		FichaDAO fichaDAO = new FichaDAO();
		List<Ficha> ficha = fichaDAO.buscarFichas(LoginBean.getUsuarioLogado());
		for (Ficha obj : ficha) {

			Long cod = obj.getOportunidade_id().getCodigo();
			oportunidade = buscar(cod);
			consulta.add(Restrictions.idEq(oportunidade));
			resultado.add(oportunidade);
		
		}
		
		
		return resultado;
		
	
		
	}

	public List<Oportunidade> buscarVagas(List<Ficha> ficha, Usuario usuario) {

		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
		Criteria consulta = sessao.createCriteria(Oportunidade.class);
		FichaDAO dao = new FichaDAO();
		dao.buscarFichas(usuario);

		@SuppressWarnings("unchecked")
		List<Oportunidade> resultado = consulta.list();

		for (Oportunidade oportunidade : resultado) {
			for (Ficha ficha2 : ficha) {

				if (oportunidade.getCandidatos().contains(ficha2)) {
					System.out.println("\n---------------------------");
					System.out.println("ID: " + oportunidade.getCodigo() + " \t Cargo: " + oportunidade.getCargo());
					oportunidade.setCadastrado(true);
					System.out.println(oportunidade.getCadastrado());
					break;

				}

			}

		}

		System.out.println("\n---------------------------");
		System.out.println("\nTotal Oportunidades = " + resultado.size());

		return resultado;

	}

	public List<Oportunidade> buscarVagasCargo(String cargo) {

		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(Oportunidade.class);
		consulta.add(Restrictions.ilike("cargo", "%" + cargo + "%"));

		@SuppressWarnings("unchecked")
		List<Oportunidade> resultado = consulta.list();

		return resultado;

	}

}
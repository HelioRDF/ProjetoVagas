package br.com.projetovagas.dao.complementos;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projetovagas.domain.complementos.Oportunidade;
import br.com.projetovagas.util.HibernateUtil;



/**
 * [ Detalhes... ]
 * Referencia.
 *  http://www.devmedia.com.br/hibernate-api-criteria-realizando-consultas/29627
 *  
//	public List <Oportunidade> buscarVagasCidade(){
//		
//		
//		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
//		
//		Criteria consulta = sessao.createCriteria(Oportunidade.class);
//		consulta.add(Restrictions.eq("cidade.codigo", 1l));
//		
//		List <Oportunidade> resultado = consulta.list();
//		
//		return resultado;
//		
//		
//	}
//	
//	public List <Oportunidade> buscarVagasEstado(){
//		
//		
//		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
//		
//		Criteria consulta = sessao.createCriteria(Oportunidade.class);
//		consulta.add(Restrictions.eq("estado.codigo", 3l));
//		
//		List <Oportunidade> resultado = consulta.list();
//		
//		return resultado;
//		
//		
//	}
//			
//	public List <Oportunidade> buscarVagasSalario(){
//		
//		
//		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
//		
//		Criteria consulta = sessao.createCriteria(Oportunidade.class);
//		consulta.add(Restrictions.ge("salario", new BigDecimal(6000)));
//		
//		List <Oportunidade> resultado = consulta.list();
//		
//		return resultado;
//		
//		
//	}
//	
//	public List <Oportunidade> buscarVagasNivel(){
//		
//		
//		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
//		
//		Criteria consulta = sessao.createCriteria(Oportunidade.class);
//		consulta.add(Restrictions.eq("nivel", "Pleno"));
//		
//		List <Oportunidade> resultado = consulta.list();
//		
//		return resultado;
//		
//		
//	}
//	
//	public List <Oportunidade> buscarVagasTipo(){
//	
//	
//	Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
//	
//	Criteria consulta = sessao.createCriteria(Oportunidade.class);
//	consulta.add(Restrictions.eq("tipo", "Empreita"));
//	
//	List <Oportunidade> resultado = consulta.list();
//	
//	return resultado;
//	
//	
//}
//
//	public List <Oportunidade> buscarVagasPaginadas(){
//
//
//Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
//
//Criteria consulta = sessao.createCriteria(Oportunidade.class);
//consulta.setFirstResult(0);
//consulta.setMaxResults(10);
//List <Oportunidade> resultado = consulta.list();
//
//return resultado;
//
//
//}
//
//	public List <Oportunidade> buscarVagasOrdenando(){
//	
//	
//	Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
//	
//	Criteria consulta = sessao.createCriteria(Oportunidade.class);
//	consulta.addOrder(org.hibernate.criterion.Order.desc("codigo"));
//	
//	
//	List <Oportunidade> resultado = consulta.list();
//	
//	return resultado;
//	
//	
//}
//
//	//Retorna 1 projeção
//	public List<Oportunidade> projectionVagas(){
//		
//		
//		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
//		
//		Criteria consulta = sessao.createCriteria(Oportunidade.class);
//		consulta.setProjection(Projections.rowCount());
//		
//		
//		List<Oportunidade>  resultado = consulta.list();
//		
//		return resultado;
//		
//		
//	}
//	
//	
//
//    //Retorna 1 lista de  projeção
//	public List<Oportunidade> ListProjectionVagas(){
//		
//		
//		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
//		
//		Criteria consulta = sessao.createCriteria(Oportunidade.class);
//		ProjectionList projectionList = Projections.projectionList();
//		projectionList.add(Projections.avg("salario"));
//		projectionList.add(Projections.max("salario"));
//		projectionList.add(Projections.min("salario"));
//		projectionList.add(Projections.sum("salario"));
//		consulta.setProjection(projectionList);
//		
//		
//		List<Oportunidade>  resultado = consulta.list();
//		
//		return resultado;
//		
//		
//	}
 * 
 */


public class OportunidadeDAO extends GenericDAO<Oportunidade> {

	public List<Oportunidade> buscarVagas(String cargo, Long estado, Long cidade, int salario, String nivel,
			String modalidade, int pcd) {

		BigDecimal salarioMaior = new BigDecimal(0.00);
		;
		BigDecimal salarioMenor = new BigDecimal(99999.99);
		;

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

		consulta.add(Restrictions.ge("salario", salarioMaior));
		consulta.add(Restrictions.lt("salario", salarioMenor));
		consulta.add(Restrictions.ilike("nivel", "%" + nivel + "%"));
		consulta.add(Restrictions.ilike("modalidade", "%" + modalidade + "%"));

		@SuppressWarnings("unchecked")
		List<Oportunidade> resultado = consulta.list();

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
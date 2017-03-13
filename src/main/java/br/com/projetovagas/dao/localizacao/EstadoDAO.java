package br.com.projetovagas.dao.localizacao;



import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projetovagas.dao.complementos.GenericDAO;
import br.com.projetovagas.domain.localizacao.Estado;
import br.com.projetovagas.util.HibernateUtil;

public class EstadoDAO extends GenericDAO<Estado> {
	
	
	public String buscarEstadoPorCodigo(Long codigo){
		
		
		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
		
		Criteria consulta = sessao.createCriteria(Estado.class);
		consulta.add(Restrictions.eq("codigo", codigo));
		
		Estado obj =(Estado) consulta.uniqueResult();
		String resultado = obj.getNome();
		System.out.println("Retornando Estado: "+resultado);
		
		return resultado;
		
		
	}
	

}

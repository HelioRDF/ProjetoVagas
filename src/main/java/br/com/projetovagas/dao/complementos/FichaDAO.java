package br.com.projetovagas.dao.complementos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projetovagas.bean.LoginBean;
import br.com.projetovagas.domain.complementos.Ficha;
import br.com.projetovagas.domain.complementos.Oportunidade;
import br.com.projetovagas.domain.usuarios.Usuario;
import br.com.projetovagas.util.HibernateUtil;

public class FichaDAO extends GenericDAO<Ficha> {

	public List<Ficha> buscarFichas(Usuario usuario) {

		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(Ficha.class);

		consulta.add(Restrictions.eq("candidato_id", usuario));

		@SuppressWarnings("unchecked")
		List<Ficha> resultado = consulta.list();
		System.out.println("\n ------------------ \n");
		for (Ficha ficha : resultado) {
			
			System.out.println("\nFichas: " + ficha.getCandidato_id().getNome());
			System.out.println("Vaga: " + ficha.getOportunidade_id().getCodigo());

		}
		System.out.println("\n ------------------ \n");
		return resultado;

	}
	
	//Busca uma ficha por id da oportunidade
	public Ficha buscarRegistro (Oportunidade id){
		
		
		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();

		Criteria consulta = sessao.createCriteria(Ficha.class);
		consulta.add(Restrictions.eq("oportunidade_id", id));
		consulta.add(Restrictions.eq("candidato_id", LoginBean.getUsuarioLogado()));
		Ficha resultado = (Ficha) consulta.uniqueResult();
			
		return resultado;
		
	}

}

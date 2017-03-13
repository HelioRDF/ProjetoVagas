package br.com.projetovagas.dao.usuarios;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projetovagas.dao.complementos.GenericDAO;
import br.com.projetovagas.domain.usuarios.FormacaoAcademica;
import br.com.projetovagas.util.HibernateUtil;

public class FormacaoAcademicaDAO extends GenericDAO<FormacaoAcademica>{
	
	
	
	
@SuppressWarnings("unchecked")
public List<FormacaoAcademica> buscarPorUsuario(Long usuarioCodigo){
		
		
		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(FormacaoAcademica.class);
			consulta.add(Restrictions.eq("usuario.codigo", usuarioCodigo));
						
			List<FormacaoAcademica> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		}finally{
		sessao.close();	
		}
		
		
	}

}

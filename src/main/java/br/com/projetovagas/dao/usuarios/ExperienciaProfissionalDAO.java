package br.com.projetovagas.dao.usuarios;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projetovagas.dao.complementos.GenericDAO;
import br.com.projetovagas.domain.usuarios.ExperienciaProfissional;
import br.com.projetovagas.util.HibernateUtil;

public class ExperienciaProfissionalDAO extends GenericDAO<ExperienciaProfissional>{
	
	
	@SuppressWarnings("unchecked")
	public List<ExperienciaProfissional> buscarPorUsuario(Long usuarioCodigo){
			
			
			Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
			try {
				Criteria consulta = sessao.createCriteria(ExperienciaProfissional.class);
				consulta.add(Restrictions.eq("usuario.codigo", usuarioCodigo));
							
				List<ExperienciaProfissional> resultado = consulta.list();
				return resultado;

			} catch (RuntimeException erro) {
				throw erro;
			}finally{
			sessao.close();	
			}
			
			
		}
	
	

}

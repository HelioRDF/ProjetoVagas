package br.com.projetovagas.dao.usuarios;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projetovagas.dao.complementos.GenericDAO;
import br.com.projetovagas.domain.usuarios.AtividadesProfissionais;
import br.com.projetovagas.util.HibernateUtil;

public class AtividadesProfissionaisDAO extends GenericDAO<AtividadesProfissionais>{
		
		
		
		
		@SuppressWarnings("unchecked")
		public List<AtividadesProfissionais> buscarPorUsuario(Long usuarioCodigo){
				
				
				Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
				try {
					Criteria consulta = sessao.createCriteria(AtividadesProfissionais.class);
					consulta.add(Restrictions.eq("usuario.codigo", usuarioCodigo));
								
					List<AtividadesProfissionais> resultado = consulta.list();
					return resultado;

				} catch (RuntimeException erro) {
					throw erro;
				}finally{
				sessao.close();	
				}
				
				
			}
		
		

}

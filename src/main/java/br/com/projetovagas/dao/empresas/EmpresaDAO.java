package br.com.projetovagas.dao.empresas;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.projetovagas.dao.complementos.GenericDAO;
import br.com.projetovagas.domain.empresas.Empresa;
import br.com.projetovagas.util.HibernateUtil;

public class EmpresaDAO extends GenericDAO<Empresa> {
	
	
	
	
	public Empresa autenticar(String  email, String senha ){
		
		//Abre uma sessão com Hibernate
		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
		
		try {
	
			Criteria consulta = sessao.createCriteria(Empresa.class);			
			
		//	consulta.createAlias("Empresa", "user");
			consulta.add(Restrictions.eq("email",email ));
			
			
			SimpleHash hash = new SimpleHash("md5",senha);
			consulta.add(Restrictions.eq("senha", hash.toHex() ));
			
			Empresa resultado = (Empresa) consulta.uniqueResult();
			
			return resultado;
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		finally {
			sessao.close();
		}
		
		
	
		
	}
	
	
	public Boolean validarEmail(String email){
		Boolean permitir =false;
		
		//Abre uma sessão com Hibernate
				Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
				
				Criteria consulta = sessao.createCriteria(Empresa.class);	
		
				consulta.add(Restrictions.eq("email",email ));
				
				Empresa resultado = (Empresa) consulta.uniqueResult();
				
				if(resultado==null){
					permitir=true;
				}
		
		return permitir;
		
	}
	
	public Boolean validarEmail(String email, Long id){
		Boolean permitir =false;
		
		//Abre uma sessão com Hibernate
				Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
				
				Criteria consulta = sessao.createCriteria(Empresa.class);	
		
				consulta.add(Restrictions.eq("email",email ));
				
				Empresa resultado = (Empresa) consulta.uniqueResult();
				
				if(resultado==null || id==resultado.getCodigo()){
					permitir=true;
				}
		
		return permitir;
		
	}

}

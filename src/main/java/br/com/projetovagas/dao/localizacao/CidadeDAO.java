package br.com.projetovagas.dao.localizacao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.projetovagas.dao.complementos.GenericDAO;
import br.com.projetovagas.domain.empresas.Empresa;
import br.com.projetovagas.domain.localizacao.Cidade;
import br.com.projetovagas.domain.usuarios.Usuario;
import br.com.projetovagas.util.HibernateUtil;

public class CidadeDAO extends GenericDAO<Cidade> {

	public List<Cidade> buscarPorEstado(Long estadoCodigo) {

		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.add(Restrictions.eq("estado.codigo", estadoCodigo));

			consulta.addOrder(Order.asc("nome"));
			@SuppressWarnings("unchecked")
			List<Cidade> resultado = consulta.list();
			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {

			sessao.close();

		}

	}

	public String buscarCidadePorCodigo(Long codigo) {

		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();

		try {

			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.add(Restrictions.eq("codigo", codigo));

			Cidade obj = (Cidade) consulta.uniqueResult();
			String resultado = obj.getNome();
			System.out.println("Retornando Cidade: " + resultado);

			return resultado;

		} catch (Exception e) {

			return "Erro no buscarCidadePorCodigo";

		} finally {
			sessao.close();

		}

	}
	
	public Cidade buscarObjCidadePorCodigo(Long codigo) {

		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();

		try {

			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.add(Restrictions.eq("codigo", codigo));

			Cidade obj = (Cidade) consulta.uniqueResult();
			
			System.out.println("Retornando Cidade: " + obj);

			return obj;

		} catch (Exception e) {

			return null;

		} finally {
			sessao.close();

		}

	}

	public long buscarOBJCidadeEmpresa(Empresa empresa) {

		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();

		try {
			
			long codigo = empresa.getCidade().getCodigo();

			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.add(Restrictions.eq("codigo", codigo));

			Cidade obj = (Cidade) consulta.uniqueResult();

			System.out.println("Retornando Cidade: " + obj);

			return codigo;

		} catch (Exception e) {
			return 0;
		} finally {

			sessao.close();
		}

	}
	
	public long buscarOBJCidadeUsuario(Usuario usuario) {

		Session sessao = HibernateUtil.getFabricadeSessoes().openSession();

		try {
			
			long codigo = usuario.getCidade().getCodigo();

			Criteria consulta = sessao.createCriteria(Cidade.class);
			consulta.add(Restrictions.eq("codigo", codigo));

			Cidade obj = (Cidade) consulta.uniqueResult();

			System.out.println("Retornando Cidade: " + obj);

			return codigo;

		} catch (Exception e) {
			return 0;
		} finally {

			sessao.close();
		}

	}

	
	
}

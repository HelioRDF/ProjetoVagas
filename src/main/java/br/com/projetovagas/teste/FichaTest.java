package br.com.projetovagas.teste;

import org.junit.Ignore;
import org.junit.Test;

import br.com.projetovagas.dao.complementos.FichaDAO;
import br.com.projetovagas.dao.complementos.OportunidadeDAO;
import br.com.projetovagas.dao.usuarios.UsuarioDAO;
import br.com.projetovagas.domain.usuarios.Usuario;

public class FichaTest {

	@Test
	@Ignore
	public void listarFichar() {

		// Ficha ficha = new Ficha();

		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = new Usuario();
		usuario = usuarioDAO.buscar(3l);

		FichaDAO dao = new FichaDAO();
		dao.buscarFichas(usuario);
		
		
		

	}
	
	
	@Test
	public void validarVagas() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = new Usuario();
		usuario = usuarioDAO.buscar(3l);
		FichaDAO dao = new FichaDAO();
		
		//Oportunidade oportunidade = new Oportunidade();
		//oportunidade.getCandidatos().contains(arg0)
		
		OportunidadeDAO oportunidadeDAO = new OportunidadeDAO();
		oportunidadeDAO.buscarVagas(dao.buscarFichas(usuario), usuario);
		
		

	}

}

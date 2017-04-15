import java.text.ParseException;

import org.junit.Test;

import br.com.projetovagas.dao.usuarios.UsuarioDAO;
import br.com.projetovagas.domain.usuarios.Usuario;


public class usuarioTestDAO {
	
	
	
	
	@Test
	public void editar() throws ParseException{
		//String aniversario="23/03/1989";
		UsuarioDAO dao = new UsuarioDAO();
		Usuario usuario= new Usuario();
		usuario = dao.buscarUsuarioId(33l);
		
		
		

		//usuario.setDataCadastro(new Date());
		//usuario.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(aniversario));
		usuario.setEmail("aline@cultura.com.br");
		usuario.setNome("Aline Cris");
		usuario.setRg("333333333");
		//usuario.setStatus(true);
		//usuario.setSenha("ea416ed0759d46a8de58f63a59077499");
		
		dao.editar(usuario);
		
	}

}

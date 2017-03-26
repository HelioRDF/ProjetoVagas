package br.com.projetovagas.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.projetovagas.bean.LoginBean;
import br.com.projetovagas.domain.usuarios.Usuario;

public class AutenticacaoListener implements PhaseListener {

	private static final long serialVersionUID = 5370242150329490062L;
	long tempoInicial = System.currentTimeMillis(); //Captura o tempo inicial da execução da Classe

	@Override
	public void afterPhase(PhaseEvent event) {
		System.out.println("\n-----------------------");
		
		
		String paginaAtual = Faces.getViewId();
		boolean paginaDeLogin = paginaAtual.contains("login.xhtml");
		LoginBean loginBean =  new LoginBean();
		Usuario usuario = new Usuario();
		
		// Verifica se a tela é publica ou privada
		if (!paginaDeLogin) {

			loginBean = Faces.getSessionAttribute("loginBean");

			// Verifica se o Bean foi criado
			if (loginBean == null) {
				Faces.navigate("/pages/publicas/login.xhtml");
				return;

			}

			// Verifica se o usuário existe
			usuario = loginBean.getUsuarioLogado();
			if (usuario == null) {
				Faces.navigate("/pages/publicas/login.xhtml");

				return;
			}
			
		} 



		System.out.println("\nAfterPhase:" + event.getPhaseId());
		System.out.println("LoginBean:" + loginBean);
		System.out.println("Página Atual:" + paginaAtual);
		System.out.println("Admin:"+usuario.getAdmin());
		
		long tempoAfter = System.currentTimeMillis(); //Captura o tempo final da execução da Classe
		System.out.printf("\n Tempo de execução: %.3f ms%n", (tempoAfter - tempoInicial) / 1000d); //Imprime o tempo de execução da classe em Ms.
	

	}

	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("\n-----------------------");
		tempoInicial = System.currentTimeMillis(); //Captura o tempo inicial da execução da Classe
		System.out.println("BeforePhase:" + event.getPhaseId());
		long tempoBefore = System.currentTimeMillis(); //Captura o tempo final da execução da Classe
		System.out.printf("\n Tempo de execução: %.3f ms%n", (tempoBefore - tempoInicial) / 1000d); //Imprime o tempo de execução da classe em Ms.
		

	}

	@Override
	public PhaseId getPhaseId() {
	
		return PhaseId.RESTORE_VIEW;
	}

}

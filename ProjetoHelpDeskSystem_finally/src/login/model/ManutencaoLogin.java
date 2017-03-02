/**
 * package login.model
 */
package login.model;
/**
 *imports necessario
 */
import java.util.List;

import login.persistencia.PersistenciaLogin;
import login.vo.Login;


public class ManutencaoLogin {
	 
	 private PersistenciaLogin persistenciaLogin;
	
	 
	 /**
	  * metodo construtor da classe
	  *
	  */ 
	public ManutencaoLogin() {
		// TODO Auto-generated constructor stub
		persistenciaLogin = new PersistenciaLogin();
	}
	
	 /**
	  * metodo que retorna os logins cadastrados 
	  * no banco
	  * 
	  * @return lista de logins
	  */ 
	public List<Login> buscarLogins() {
		// TODO Auto-generated method stub
		return persistenciaLogin.buscarLogins();
	}
	
	
}
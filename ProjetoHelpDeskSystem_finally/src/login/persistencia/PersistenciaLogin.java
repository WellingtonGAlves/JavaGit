/**
 * package login.persistencia
 */
package login.persistencia;
/**
 * imports necessarios
 */
import java.util.List;

import login.vo.Login;

public class PersistenciaLogin implements ILoginDAO {
	
	private LoginDAO loginDAO;
	
	/**
	 * Função construtora da classe PersistenciaLogin
	 */
	public PersistenciaLogin() {
		loginDAO = new LoginDAO();
		// TODO Auto-generated constructor stub
	}
	

	/**
	 * metodo responsavel para retornar 
	 * logins cadastrados da classe LoginDAO
	 * @return lista de logins
	 */

	@Override
	public List<Login> buscarLogins() {
		// TODO Auto-generated method stub
		return loginDAO.buscarLogins();
	}


	

}

/**
 * package login.persistencia
 */

package login.persistencia;
/**
 * imports necesarios
 */
import java.util.List;

import login.vo.Login;

/**
 * Interface para definir as operações com o banco de dados 
 * para o objeto Login 
 * 
 */

public interface ILoginDAO {

	
	
	public List<Login> buscarLogins();

}
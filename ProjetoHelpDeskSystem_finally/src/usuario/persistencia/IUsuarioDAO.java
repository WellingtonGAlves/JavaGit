/**
 * package usuario.persistencia
 */
package usuario.persistencia;
/**
 * imports necesario para a classe IUsuariDAO
 */
import java.util.List;

import usuario.vo.Departamento;
import usuario.vo.Usuario;

/**
 * Interface para definir os metodos e comportamento para o cadastro do usuario.
 * 
 * 
* @author Tiago/Wellington/Fernando
 * 
 * @since 03/05/2016
 * 
 * @version 1.0
 * 
 */

interface IUsuarioDAO {

	public Usuario salvar(Usuario usuario);

	public List<Usuario> listar();

	public Usuario atualizar(Usuario usuario);

	public Boolean excluir(Usuario usuario);

	public Integer pesquisar();

	public Usuario pesquisar(Integer codigo);

	public List<Usuario> pesquisar(String nome);
	
	public List<Departamento> buscarDepartamentos();
	public Departamento pesquisarIdDepartamento(Integer idDepartamento);
	
	public Departamento pesquisarDepartamento(String departamento);
	
	

}

/**
 * package usuario.persistencia
 */
package usuario.persistencia;
/**
 * Imports necessarios da classe PersistenciaUsuario
 */
import java.util.List;

import usuario.vo.Departamento;
import usuario.vo.Usuario;

/**
 * Classe para implementar a interface entre o sistema e a camada de acesso a dados.
 * 
 * @author Tiago/Wellington/Fernando
 * 
 * @since 03/05/2016
 * 
 * @version 1.0
 * 
 */

public class PersistenciaUsuario implements IUsuarioDAO {

	/**
	 * Variáveis de instância
	 */

	public UsuarioDAO usuarioDAO;

	/**
	 * Função construtora da classe PersistenciaUsuario
	 */

	public PersistenciaUsuario() {

		usuarioDAO = new UsuarioDAO();
	}

	

	/**
	 * metodo responsavel por passar o objeto usuario para a UsuarioDAO 
	 * salvar no banco
	 * @param objeto Usuario
	 * @return objeto Usuario
	 */
	@Override
	public Usuario salvar(Usuario usuario) {
		// TODO Auto-generated method stub
		return usuarioDAO.salvar(usuario);
	}

	/**
	 * metodo responsavel por buscar usuarios cadastrados no banco
	 * 
	 * @return lista de objeto Usuario
	 */
	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub
		return usuarioDAO.listar();
	}

	/**
	 * metodo responsavel por passar o objeto usuario para a UsuarioDAO  
	 * atualizar no banco
	 * @param objeto Usuario
	 * @return objeto Usuario
	 */
	@Override
	public Usuario atualizar(Usuario usuario) {
		// TODO Auto-generated method stub
		return usuarioDAO.atualizar(usuario);
	}

	/**
	 * metodo responsavel por passar o objeto usuario para a UsuarioDAO 
	 * excluir no banco
	 * @param objeto Usuario
	 * @return flag
	 */
	@Override
	public Boolean excluir(Usuario usuario) {
		// TODO Auto-generated method stub
		return usuarioDAO.excluir(usuario);
	}
	/**
	 * metodo responsavel por buscar atravez da chamada da UsuarioDAO 
	 * o ultimo ususario cadastrado no banco
	 * 
	 * @return idUsuario
	 */
	@Override
	public Integer pesquisar() {
		// TODO Auto-generated method stub
		return usuarioDAO.pesquisar();
	}

	/**
	 * metodo responsavel por passar o id do usuario para a UsuarioDAO 
	 * buscar o usuario correspondente a ele no banco
	 * @param id do usuario
	 * @return objeto Usuario
	 */
	@Override
	public Usuario pesquisar(Integer codigo) {
		// TODO Auto-generated method stub
		return usuarioDAO.pesquisar(codigo);
	}
	/**
	 * metodo responsavel por passar o nome do usuario para a UsuarioDAO 
	 * buscar todos os usuario correspondes a este nome
	 * @param noime do usuario
	 * @return lista de  objeto Usuario
	 */
	@Override
	public List<Usuario> pesquisar(String nome) {
		// TODO Auto-generated method 
		return usuarioDAO.pesquisar(nome);
	}
	/**
	 * metodo responsavel por buscar atravez da UsuarioDAO 
	 * os departamentos cadastrados no banco
	 * @return lista de objeto departamento
	 */
	@Override
	public List<Departamento> buscarDepartamentos() {
		// TODO Auto-generated method stub
		return usuarioDAO.buscarDepartamentos();
	}
	/**
	 * metodo responsavel por buscar atravez da UsuarioDAO 
	 * os departamentos cadastrados no banco atravez do id do departamento
	 * @param id do departamento
	 * @return objeto departamento
	 */
	@Override
	public Departamento pesquisarIdDepartamento(Integer idDepartamento) {
		// TODO Auto-generated method stub
		return usuarioDAO.pesquisarIdDepartamento(idDepartamento);
	}
	/**
	 * metodo responsavel por buscar atravez da UsuarioDAO 
	 * os departamentos cadastrados no banco atravez do nome departamento
	 * @param nome do departamento
	 * @return objeto departamento
	 */
	@Override
	public Departamento pesquisarDepartamento(String departamento) {
		// TODO Auto-generated method stub
		return usuarioDAO.pesquisarDepartamento(departamento);
	}

	

	

}

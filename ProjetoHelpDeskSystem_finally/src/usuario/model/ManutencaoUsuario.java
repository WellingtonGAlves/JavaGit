/**
 * package usuario.model
 */
package usuario.model;

import java.util.List;

import usuario.persistencia.PersistenciaUsuario;
import usuario.vo.Departamento;
import usuario.vo.Usuario;
import static usuario.util.Utilitaria.validarSIMouNAO;
import static usuario.util.Utilitaria.validarSexo;


/**
 * Manutenção do usuario.
 * 
 * @author Tiago/Wellington/Fernando
 * 
 * @since 03/05/2016
 * 
 * @version 1.0
 * 
 */
public class ManutencaoUsuario {

	/*
	 * Variáveis de instância
	 */

	private PersistenciaUsuario persistenciaUsuario;

	/**
	 * Função construtora da classe ManutencaoUsuario
	 */

	public ManutencaoUsuario() {

		persistenciaUsuario = new PersistenciaUsuario();

	}

	
	/**
	 * Metodo utilizado para ajustar as variveis para não ocorrer erro na interação com o banco
	 * e posteriormente salvar o usuario
	 * @param objeto usaurio
	 * @return objeto usuario
	 */
	public Usuario salvar(Usuario usuario) {
		
		String paraValidarNoMetodo = usuario.getIsAnalista();
		String sexo = usuario.getSexo();
		
		
		if(usuario.getSenha().equals("NÃO POSSUE")||usuario.getLogin().equals("NÃO POSSUE")){
			usuario.setSenha("NAO POSSUE");
			usuario.setLogin("NAO POSSUE");
		}
		
		if(validarSIMouNAO(paraValidarNoMetodo) == true){
			usuario.setIsAnalista("S");
		}else{
			usuario.setIsAnalista("N");
		}
		if(validarSexo(sexo) == true){
			usuario.setSexo("M");
		}else{
			usuario.setSexo("F");
		}
		
		
		
		
		return persistenciaUsuario.salvar(usuario);

	}

	/**
	 * Metodo responsavel por buscar todos os usuario cadastrados no banco 
	 * atravez da chamada pela persistenciaUsuario e UsuarioDAO
	 * @return objeto Usuario
	 */
	public List<Usuario> consultar() {

		return persistenciaUsuario.listar();
	}

	/**
	 * Metodo utilizado para ajustar as variveis para não ocorrer erro na interação com o banco
	 * e posteriormente atualizar o usuario
	 * @param objeto Usuario
	 * @return objeto usuario
	 */
	public Usuario atualizar(Usuario usuario) {
		
		String paraValidarNoMetodo = usuario.getIsAnalista();
		String sexo = usuario.getSexo();
		
		
		if(usuario.getSenha().equals("NÃO POSSUE")||usuario.getLogin().equals("NÃO POSSUE")){
			usuario.setSenha("NAO POSSUE");
			usuario.setLogin("NAO POSSUE");
		}
		
		if(validarSIMouNAO(paraValidarNoMetodo) == true){
			usuario.setIsAnalista("S");
		}else{
			usuario.setIsAnalista("N");
		}
		if(validarSexo(sexo) == true){
			usuario.setSexo("M");
		}else{
			usuario.setSexo("F");
		}
		
		
		return persistenciaUsuario.atualizar(usuario);
	}

	/**
	 * Metodo responsavel por excluir um usuario 
	 * atravez da chama da persistenciaUsuario e UsuarioDAO
	 * retornado uma flag para saber se a exclusão ocorreu
	 * @param Objeto usuario
	 * @return flag
	 */
	public Boolean excluir(Usuario usuario) {

		return persistenciaUsuario.excluir(usuario);
	}

	/**
	 * Metodo responsavel por buscar usuarios cadastrados no banco
	 * passando como parametro o id do usuario
	 * @param id do usuario
	 * @return objeto usuario
	 */
	public Usuario pesquisar(Integer idUsuario) {

		return persistenciaUsuario.pesquisar(idUsuario);
	}

	/**
	 * Metodo utilizado para buscar usuarios cadastrados no banco
	 * passando como parametro o nome do  usuario
	 * @param nome do usuario
	 * @return lista do Objeto usuario
	 */
	public List<Usuario> pesquisar(String nome) {

		// Saída da informação

		return persistenciaUsuario.pesquisar(nome);

	}

	/**
	 * Metodo utilizado para buscar usuarios cadastrados no banco
	 * passando como parametro o nome do  usuario
	 * @param nome do usuario
	 * @return lista do Objeto usuario
	 */
	public List<Usuario> pesquisarUsuario(String nome) {
		// TODO Auto-generated method stub
		return persistenciaUsuario.pesquisar(nome);
	}
	/**
	 * Metodo utilizado para buscar departamentos cadastrados no banco
	 * @return lista do Objeto Departamento
	 */
	public List<Departamento> buscarDepartamentos() {
		// TODO Auto-generated method stub
		return persistenciaUsuario.buscarDepartamentos();
	}
	/**
	 * Metodo utilizado para buscar departamentos cadastrados no banco
	 * passando como paramentro o id do departamento
	 * @param id do departameto
	 * @return lista do Objeto Departamento
	 */
	public Departamento pesquisarIdDepartamento(Integer idDepartamento) {
		// TODO Auto-generated method stub
		return persistenciaUsuario.pesquisarIdDepartamento(idDepartamento);
	}
	/**
	 * Metodo utilizado para buscar departamentos cadastrados no banco
	 * passando como paramentro o nome do departamento
	 * @param nome do departamento
	 * @return lista do Objeto Departamento
	 */
	public Departamento pesquisarDepartamento(String departamento){
		
		return persistenciaUsuario.pesquisarDepartamento(departamento);
	}

	

}

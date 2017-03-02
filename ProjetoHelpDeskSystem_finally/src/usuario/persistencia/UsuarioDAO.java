/**
 * package usuario.persistencia
 */
package usuario.persistencia;
/**
 * imports necessarios da classe  UsuarioDAO
 */
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import java.util.List;



import usuario.util.ConnectionFactory;

import static helpdesk.persistencia.Persistencia.CONSULTA_ERRO;
import static helpdesk.persistencia.Persistencia.CONSULTA_NAO_SUCESSO;
import static helpdesk.persistencia.Persistencia.CONSULTA_SUCESSO;
import static helpdesk.persistencia.Persistencia.FLAG;
import static usuario.persistencia.Persistencia.*;

import usuario.vo.Departamento;
import usuario.vo.Usuario;

/**
 * Classe para comunicação direta com o banco de dados.
 * 
* @author Tiago/Wellington/Fernando
 * 
 * @since 03/05/2016
 * 
 * @version 1.0
 * 
 */

class UsuarioDAO implements IUsuarioDAO {

	/**
	 * Variáveis de instância da classe  UsuarioDAO
	 */

	private Connection connection;

	/**
	 * Função construtora da classe  UsuarioDAO
	 */

	public UsuarioDAO() {

	}

	

	/**
	 * metodo resposnsavel por salvar o usuario no banco
	 * @param objeto usuario
	 * @return objeto usuario
	 */
	@Override
	public Usuario salvar(Usuario usuario) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer idUsuario = null;

		Integer count = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "INSERT INTO USUARIO(IDUSUARIO,IDDEPART,PERMISSAO,NOME,DEPARTAMENTO,SEXO,EMAIL,ISANALISTA,NIVEL_ANALISTA,SENHA,LOGIN) VALUES (CONT_IDUSUARIO.NEXTVAL,?,?,?,?,?,?,?,?,?,?)";

			preparedStatement = connection.prepareStatement(query);
			
			preparedStatement.setInt(1, usuario.getDepartamento().getIdDepartamento());
			
			preparedStatement.setString(2,( usuario.getPermissao()));
			
			preparedStatement.setString(3, usuario.getNome());
			
			preparedStatement.setString(4,usuario.getDepartamento().getDepartamento());
			
			preparedStatement.setString(5, usuario.getSexo());
			
			preparedStatement.setString(6, usuario.getEmail());
			
			preparedStatement.setString(7, usuario.getIsAnalista());
			
			preparedStatement.setInt(8, usuario.getNivelAnalista());

			preparedStatement.setString(9, usuario.getSenha());
			
			preparedStatement.setString(10, usuario.getLogin());
			
			
			

			count = new Integer(preparedStatement.executeUpdate());

			if (count != null && count.equals(1)) {

				connection.commit();

				msg = INSERCAO_SUCESSO;

				idUsuario = this.pesquisar();

				if (idUsuario != null && idUsuario.intValue() > 0) {

					usuario = pesquisar(idUsuario);
					if(usuario==null){
						usuario = new Usuario();
						msg = "Não foi possivel recuperar o ultimo usuario cadastrado";
					}
					msg += RECUPERACAO_DADOS_SUCESSO;

				} else {

					msg += RECUPERACAO_DADOS_NAO_SUCESSO;
				}

			} else {

				msg = INSERCAO_NAO_SUCESSO;
			}

		} catch (SQLException e) {

			usuario = null;

			msg = INSERCAO_ERRO + e.getMessage();

			e.printStackTrace();

		} finally {

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s: %s", this.getClass().getSimpleName(), msg));

				if (usuario != null) {

					System.err.println(String.format("\n Dados inserido: %s", usuario.toString()));
				}
			}
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		System.out.println("Conexão fechada");
		return usuario;
	}

	/**
	 * metodo resposnsavel por buscar todos os usuarios cadastrados  no banco
	 * @return lista objeto usuario
	 */
	@Override
	public List<Usuario> listar() {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		Usuario usuario = null;

		List<Usuario> usuarioList = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM USUARIO ORDER BY NOME ASC";

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet.last()) {

				resultSet.beforeFirst();

				usuarioList = new ArrayList<>();

				while (resultSet.next()) {

					usuario = new Usuario();

					usuario.setIdUsuario(new Integer(resultSet.getInt("IDUSUARIO")));
					usuario.getDepartamento().setIdDepartamento(resultSet.getInt("IDDEPART"));
					usuario.setPermissao(resultSet.getString("PERMISSAO"));
					usuario.setNome(resultSet.getString("NOME"));
					usuario.getDepartamento().setDepartamento(resultSet.getString("DEPARTAMENTO"));
					usuario.setSexo(resultSet.getString("SEXO"));
					usuario.setEmail(resultSet.getString("EMAIL"));
					usuario.setIsAnalista(resultSet.getString("ISANALISTA"));
					if(usuario.getIsAnalista().equals("S")){
						usuario.setIsAnalista("SIM");
					}else{
						usuario.setIsAnalista("NÃO");
					}
					usuario.setNivelAnalista(resultSet.getInt("NIVEL_ANALISTA"));
					usuario.setLogin(resultSet.getString("LOGIN"));
					usuario.setSenha(resultSet.getString("SENHA"));
					
					
					
			

					usuarioList.add(usuario);
				}

				msg = CONSULTA_SUCESSO;

			} else {

				msg = CONSULTA_NAO_SUCESSO;
			}

		} catch (Exception e) {
			// TODO: handle exception

			usuarioList = null;

			msg = CONSULTA_ERRO + e.getMessage();

			e.printStackTrace();

		} finally {

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s: %s", this.getClass().getSimpleName(), msg));

				if (usuarioList != null && usuarioList.size() > 0) {

					System.err.println("\n Dados: ");

					for (Usuario person : usuarioList) {

						System.err.println(person.toString());
					}
				}
			}
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		System.out.println("Conexão fechada");
		return usuarioList;
	}

	/**
	 * metodo resposnsavel por atualizar o usuario no banco
	 * @param objeto usuario
	 * @return objeto usuario
	 */
	@Override
	public Usuario atualizar(Usuario usuario) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Integer count = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "UPDATE USUARIO  SET IDDEPART = ?,PERMISSAO = ?, DEPARTAMENTO = ?, ISANALISTA = ?,NIVEL_ANALISTA = ?, EMAIL = ? , LOGIN = ?, SENHA = ? , IDUSUARIO = ?  WHERE IDUSUARIO = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, usuario.getDepartamento().getIdDepartamento());
			preparedStatement.setString(2, usuario.getPermissao());			
			preparedStatement.setString(3, usuario.getDepartamento().getDepartamento());			
			preparedStatement.setString(4, usuario.getIsAnalista());
			preparedStatement.setInt(5, usuario.getNivelAnalista());
			preparedStatement.setString(6, usuario.getEmail());
			preparedStatement.setString(7,usuario.getLogin());
			preparedStatement.setString(8, usuario.getSenha());
			preparedStatement.setInt(9, usuario.getIdUsuario());
			preparedStatement.setInt(10, usuario.getIdUsuario());
			

			count = new Integer(preparedStatement.executeUpdate());

			if (count != null && count.equals(1)) {

				connection.commit();

				msg = ATUALIZACAO_SUCESSO;

				usuario = pesquisar(usuario.getIdUsuario());
				//usuario.setIdAnalista(pesquisarIdanalista(usuario.getIdUsuario()));
				//atualizarAnalista(usuario);
			} else {

				msg = ATUALIZACAO_NAO_SUCESSO;
			}

		} catch (SQLException e) {
			// TODO: handle exception

			usuario = null;

			msg = ATUALIZACAO_ERRO + e.getMessage();

			e.printStackTrace();

		} finally {

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s: %s", UsuarioDAO.class.getSimpleName(), msg));

				if (usuario != null) {

					System.err.println("\n Dados atualizados: " + usuario.toString());
				}

			}
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		System.out.println("Conexão fechada");
		return usuario;
	}
	
	/**
	 * metodo resposnsavel por excluir um usuario no banco
	 * @param objeto usuario
	 * @return flag
	 */
	@Override
	public Boolean excluir(Usuario usuario) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		String query = null;

		String msg = null;

		Boolean flag = null;

		Integer count = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "DELETE FROM USUARIO USUARIO WHERE USUARIO.IDUSUARIO = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, usuario.getIdUsuario());

			count = new Integer(preparedStatement.executeUpdate());

			if (count != null && count.equals(1)) {

				connection.commit();

				flag = true;

				msg = EXCLUSAO_SUCESSO;

			} else {

				flag = false;

				msg = EXCLUSAO_NAO_SUCESSO;
			}

		} catch (SQLException e) {
			// TODO: handle exception

			flag = null;

			msg = EXCLUSAO_ERRO + e.getMessage();

			e.printStackTrace();

		} finally {

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s: %s", this.getClass().getSimpleName(), msg));

				if (flag != null && flag.equals(true)) {

					System.err.println("\n Dados excluídos: " + usuario.toString());

				} else {

					System.err.println("\n Não foi possível excluir dados.");
				}
			}

		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		System.out.println("Conexão fechada");
		return flag;
	}

	/**
	 * metodo resposnsavel por pesquisar o ultimo suuario cadastrado no banco
	 * 
	 * @return id do ultimo usuario casdastrado no banco
	 */
	@Override
	public Integer pesquisar() {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		Integer idUsuario = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT MAX(USUARIO.IDUSUARIO) FROM USUARIO USUARIO";

			statement = connection.createStatement();

			resultSet = statement.executeQuery(query);

			if (resultSet.next()) {

				idUsuario = new Integer(resultSet.getInt(1));
			}

		} catch (SQLException e) {
			// TODO: handle exception

			idUsuario = null;

			e.printStackTrace();

		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		System.out.println("Conexão fechada");
		return idUsuario;
	}

	/**
	 * metodo resposnsavel por buscar o usuario  com o id passado como paramentro no banco
	 * @param id usuario
	 * @return objeto usuario
	 */
	@Override
	public Usuario pesquisar(Integer idUsuario) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Usuario usuario = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM  USUARIO WHERE IDUSUARIO = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, idUsuario.intValue());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				usuario = new Usuario();

				usuario.setIdUsuario(resultSet.getInt("IDUSUARIO"));
				usuario.getDepartamento().setIdDepartamento(resultSet.getInt("IDDEPART"));
				usuario.setPermissao(resultSet.getString("PERMISSAO"));
				usuario.setNome(resultSet.getString("NOME"));
				usuario.getDepartamento().setDepartamento(resultSet.getString("DEPARTAMENTO"));
				usuario.setSexo(resultSet.getString("SEXO"));
				if(usuario.getSexo().equals("M")){
					usuario.setSexo("MASCULINO");
				}else{
					usuario.setSexo("FEMININO");
				}
				usuario.setEmail(resultSet.getString("EMAIL"));
				usuario.setIsAnalista(resultSet.getString("ISANALISTA"));
				if(usuario.getIsAnalista().equals("S")){
					usuario.setIsAnalista("SIM");
				}else{
					usuario.setIsAnalista("NÃO");
				}
				usuario.setNivelAnalista(resultSet.getInt("NIVEL_ANALISTA"));
				usuario.setSenha(resultSet.getString("SENHA"));
				usuario.setLogin(resultSet.getString("LOGIN"));
				
			}

		} catch (SQLException e) {
			// TODO: handle exception

			usuario = null;

			e.printStackTrace();
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		System.out.println("Conexão fechada");
		return usuario;
	}

	/**
	 * metodo resposnsavel por buscar os usuarios  com o nome passado como paramentro no banco
	 * @param nome usuario
	 * @return lista de objeto usuario
	 */
	@Override
	public List<Usuario> pesquisar(String nome) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		List<Usuario> usuarioList = null;

		Usuario usuario = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			nome = "%" + nome.toUpperCase() + "%";

			query = "SELECT * FROM USUARIO WHERE NOME LIKE ? ORDER BY NOME ASC";

			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			preparedStatement.setString(1, nome);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.last()) {

				resultSet.beforeFirst();

				usuarioList = new ArrayList<>();

				while (resultSet.next()) {

					usuario = new Usuario();

					usuario.setIdUsuario(resultSet.getInt("IDUSUARIO"));
					usuario.getDepartamento().setIdDepartamento(resultSet.getInt("IDDEPART"));
					usuario.setPermissao(resultSet.getString("PERMISSAO"));
					if(usuario.getPermissao().equals("NAO POSSUE")){
						usuario.setPermissao("NÃO POSSUE");
					}
					usuario.setNome(resultSet.getString("NOME"));
					usuario.getDepartamento().setDepartamento(resultSet.getString("DEPARTAMENTO"));
					usuario.setSexo(resultSet.getString("SEXO"));
					if(usuario.getSexo().equals("M")){
						usuario.setSexo("MASCULINO");
					}else{
						usuario.setSexo("FEMININO");
					}
					usuario.setEmail(resultSet.getString("EMAIL"));
					usuario.setIsAnalista(resultSet.getString("ISANALISTA"));
					if(usuario.getIsAnalista().equals("S")){
						usuario.setIsAnalista("SIM");
					}else{
						usuario.setIsAnalista("NÃO");
					}
					usuario.setNivelAnalista(new Integer(resultSet.getInt("NIVEL_ANALISTA")));
					usuario.setSenha(resultSet.getString("SENHA"));
					usuario.setLogin(resultSet.getString("LOGIN"));



					

					usuarioList.add(usuario);
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception

			usuarioList = null;

			e.printStackTrace();
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		System.out.println("Conexão fechada");
		return usuarioList;
	}
	
	/**
	 * metodo resposnsavel por buscar todos os departamentos cadastrados no banco
	* @return Lista de objeto departamento
	 */
	@Override
	public List<Departamento> buscarDepartamentos() {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		List<Departamento> departamentoList = null;

		Departamento departamento = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM DEPARTAMENTO DEPARTAMENTO ORDER BY DEPARTAMENTO.DEPARTAMENTO ASC";

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet.last()) {

				resultSet.beforeFirst();

				departamentoList = new ArrayList<>();

				while (resultSet.next()) {

					departamento = new Departamento();

					departamento.setIdDepartamento(resultSet.getInt("IDDEPART"));

					departamento.setDepartamento(resultSet.getString("DEPARTAMENTO"));


					departamentoList.add(departamento);

				}

				msg = CONSULTA_SUCESSO;

			} else {

				msg = CONSULTA_NAO_SUCESSO;
			}

		} catch (Exception e) {
			// TODO: handle exception

			departamentoList = null;

			msg = CONSULTA_ERRO + e.getMessage();

			e.printStackTrace();

		} finally

		{

			if (statement != null) {

				try {

					statement.close();

				} catch (SQLException e2) {
					// TODO: handle exception

					e2.printStackTrace();
				}
			}

			if (FLAG.equals(true)) {

				if (msg != null) {

					System.err.println(String.format("\n %s: %s", this.getClass().getSimpleName(), msg));

					if (departamentoList != null && departamentoList.size() > 0) {

						System.err.print(" Dados: ");

						for (Departamento user : departamentoList) {

							System.err.println(user.toString());
						}
					}
				}
			}
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		return departamentoList;

	}
	/**
	 * metodo resposnsavel por buscar o  departamento correspondente ao id passado como paramentro no banco
	 *@param idDepartamento
	 * @return objeto departamento
	 */
	@Override
	public Departamento pesquisarIdDepartamento(Integer idDepartamento) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Departamento departamento = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM DEPARTAMENTO DEPARTAMENTO WHERE DEPARTAMENTO.IDDEPART = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, idDepartamento.intValue());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				departamento = new Departamento();

				departamento.setIdDepartamento(resultSet.getInt("IDDEPART"));

				departamento.setDepartamento(resultSet.getString("DEPARTAMENTO"));

			}

		} catch (SQLException e) {
			// TODO: handle exception

			departamento = null;

			e.printStackTrace();

		} finally {

			if (preparedStatement != null) {

				try {

					preparedStatement.close();

				} catch (SQLException e2) {
					// TODO: handle exception

					e2.printStackTrace();
				}
			}
		}

		
		ConnectionFactory.closeConnection();
		return departamento;
	}
	/**
	 * metodo resposnsavel por buscar o  departamento correspondente ao nome passado como paramentro no banco
	 *@param nome do departamento
	 * @return objeto departamento
	 */
	@Override
	public Departamento pesquisarDepartamento(String departamento) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Departamento departamentoClass = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM DEPARTAMENTO DEPARTAMENTO WHERE DEPARTAMENTO.DEPARTAMENTO = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setString(1, departamento.toString());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				departamentoClass = new Departamento();

				departamentoClass.setIdDepartamento(resultSet.getInt("IDDEPART"));

				departamentoClass.setDepartamento(resultSet.getString("DEPARTAMENTO"));

			}

		} catch (SQLException e) {
			// TODO: handle exception

			departamento = null;

			e.printStackTrace();

		} finally {

			if (preparedStatement != null) {

				try {

					preparedStatement.close();

				} catch (SQLException e2) {
					// TODO: handle exception

					e2.printStackTrace();
				}
			}
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		return departamentoClass;
	}
	
	

	

}
/**
 * package login.persistencia
 */
package login.persistencia;

/**
 * imports necesarios
 */
import static helpdesk.persistencia.Persistencia.CONSULTA_ERRO;
import static helpdesk.persistencia.Persistencia.CONSULTA_NAO_SUCESSO;
import static helpdesk.persistencia.Persistencia.CONSULTA_SUCESSO;
import static helpdesk.persistencia.Persistencia.FLAG;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import login.vo.Login;
import login.util.ConnectionFactory;


public class LoginDAO implements ILoginDAO {
	
	private Connection connection;
	
	
	/**
	 * função construtor da classe LoginDAO
	 */
	public LoginDAO() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * metodo responsavel por retorna logins cadastrados no banco
	 * @return lista de logins
	 */
	@Override
	public List<Login> buscarLogins() {
		// Metodo que retorna todos os tickets cadastrados e seus campos

		// Declaração de variáveis

		Statement statement = null;

		ResultSet resultSet = null;

		String sql = null;

		String msg = null;

		Login loginClass = null;

		List<Login> loginList = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {
			
			sql = "SELECT * FROM LOGIN WHERE PERMISSAO = 'ADMINISTRADOR' OR PERMISSAO  ='ANALISTA'";
			
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			
			resultSet = statement.executeQuery(sql);
			
			if (resultSet.last()) {

				resultSet.beforeFirst();

				loginList = new ArrayList<>();

				while (resultSet.next()) {

					loginClass = new Login();
					
					loginClass.setIdLogin(resultSet.getInt("IDLOGIN"));
					loginClass.setLogin(resultSet.getString("LOGIN"));
					loginClass.setSenha(resultSet.getString("SENHA"));
					loginClass.setPermissao(resultSet.getString("PERMISSAO"));
					
					
		
					loginList.add(loginClass);
				}

				msg = CONSULTA_SUCESSO;

			} else {

				msg = CONSULTA_NAO_SUCESSO;
			}

		} catch (Exception e) {
			// TODO: handle exception

			loginList = null;

			msg = CONSULTA_ERRO + e.getMessage();

			e.printStackTrace();

		} finally {

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s: %s", this.getClass().getSimpleName(), msg));

				if (loginList != null && loginList.size() > 0) {

					System.err.println("\n Logins: ");

					for (Login loginCadastrado : loginList) {

						System.err.println(loginCadastrado.toString());
					}
				}
			}
		}

		// Saída da informação
		ConnectionFactory.getConnection();
		return loginList;
	}

}

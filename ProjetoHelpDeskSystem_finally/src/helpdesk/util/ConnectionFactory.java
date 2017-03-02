/**
 *package helpdesk.util
 */
package helpdesk.util;

/**
 * Imports necessarios
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe ConnectionFactory é responsável por gerenciar a conexão com o Banco de
 * dados.
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */

public class ConnectionFactory {

	private static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";
	private static final String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521: XE";
	private static final String USERNAME = "system";
	private static final String PASSWORD = "system";

	private static final String CONEXAO_SUCESSO = "Conexão com o Banco de Dados realizada com sucesso.";
	private static final String CONEXAO_NAO_SUCESSO = "NÃO FOI POSSÍVEL CONECTAR-SE COM O BANCO DE DADOS.";
	private static final String DRIVER_NAO_CARREGADO = "NÃO FOI POSSÍVEL CARREGAR O DRIVER.";
	private static final String CONEXAO_CLOSE_SUCESSO = "Conexão com o Banco de Dados fechada com sucesso.";
	private static final String CONEXAO_NAO_CLOSE_SUCESSO = "ERRO AO FECHAR A CONEXÃO COM BANCO DE DADOS.";
	private static final String CONNECTION_IS_NOT_OPEN = "A conexão com o banco de dados não foi realizada.";

	private static Connection connection;

	/**
	 * Metodo construtor reponsavel por abrir conexão com o banco de dados
	 */
	private ConnectionFactory() {

		openConnection();
	}

	/**
	 * Metodo reponsavel por abrir conexão com o banco de dados
	 */

	private void openConnection() {

		// Declaração de variáveis

		String msg = null;

		// Processamento dos dados

		try {

			Class.forName(JDBC_DRIVER);

			// Connect with the data base

			connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

			connection.setAutoCommit(false);

			msg = CONEXAO_SUCESSO;

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			connection = null;

			msg = DRIVER_NAO_CARREGADO;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			connection = null;

			msg = CONEXAO_NAO_SUCESSO;

		} finally {

			if (msg != null) {

				System.err.println("\n " + msg);
			}
		}
	}

	/**
	 * Metodo reponsavel por verficar se existe uma conexão com o banco de dados
	 * aberta
	 * 
	 */

	public static Connection getConnection() {

		// Processamento dos dados

		try {
			if (connection == null || connection.isClosed()) {
				System.out.println("(connection == null || connection.isClosed())");
				new ConnectionFactory();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return connection;
	}

	/**
	 * Metodo reponsavel por fechar conexão com o banco de dados
	 */

	public static void closeConnection() {

		// Declaração de variáveis

		String msg = null;

		// Processametno dos dados

		if (connection != null) {

			try {

				connection.close();
				System.out.println("Conexão fechada com sucesso");

				msg = CONEXAO_CLOSE_SUCESSO;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

				msg = CONEXAO_NAO_CLOSE_SUCESSO;

			} finally {

				if (msg != null) {

					System.err.println("\n " + msg);
					// ConnectionFactory.closeConnection();
				}
			}

		} else {

			System.err.println("\n " + CONNECTION_IS_NOT_OPEN);
		}
	}

}

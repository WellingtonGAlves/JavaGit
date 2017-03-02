/**
 * package helpdesk.persistencia
 */
package helpdesk.persistencia;

/**
 * Imports necessarios
 */
import static helpdesk.persistencia.Persistencia.ATUALIZACAO_ERRO;
import static helpdesk.persistencia.Persistencia.ATUALIZACAO_NAO_SUCESSO;
import static helpdesk.persistencia.Persistencia.ATUALIZACAO_SUCESSO;
import static helpdesk.persistencia.Persistencia.CONSULTA_ERRO;
import static helpdesk.persistencia.Persistencia.CONSULTA_NAO_SUCESSO;
import static helpdesk.persistencia.Persistencia.CONSULTA_SUCESSO;
import static helpdesk.persistencia.Persistencia.FLAG;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import helpdesk.util.ConnectionFactory;
import helpdesk.vo.Analista;
import helpdesk.vo.Categoria;
import helpdesk.vo.Id;
import helpdesk.vo.Prioridade;
import helpdesk.vo.Relatorio;
import helpdesk.vo.Ticket;
import helpdesk.vo.Tipo;
import oracle.jdbc.OracleTypes;

/**
 * Classe TicketDAO.
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
class TicketDAO implements ITicketDAO {

	/**
	 * Variáveis de instância
	 */

	private Connection connection;
	// private List<Ticket> ticketList = null;

	/**
	 * Metodo Construtor TicketDAO
	 */

	public TicketDAO() {

	}

	/**
	 * Metodo responsavel por inserir o objeto ticket na Base de dados.
	 * 
	 * @param ticket
	 * @return ticket
	 */
	@Override
	public Ticket salvarTicket(Ticket ticket) {

		// Declaração de variáveis
		// ticket.setStatus("ABERTO");

		PreparedStatement preparedStatement = null;

		String sql = null;

		String msg = null;
		
		Integer idTicket = null;

		Integer count = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();
		if (connection == null) {

			return null;
		}
		try {
			sql = "INSERT INTO TICKET (IDTICKET,IDTIPO,IDPRIORIDADE,IDUSUARIO,IDANALISTA,IDCATEGORIA,USUARIO,TIPO,CATEGORIA,ASSUNTO,DESCRICAO,SOLUCAO,NIVEL_ANALISTA,ANALISTA,DATA_INICIO,DATA_FIM,STATUS) VALUES (CONT_IDTICKET.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'',?)";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setInt(1, ticket.getTipo().getIdTipo());
			preparedStatement.setInt(2, ticket.getPrioridade().getIdPrioridade());
			preparedStatement.setInt(3, ticket.getUsuario().getIdUsuario());
			preparedStatement.setInt(4, ticket.getAnalista().getIdAnalista());
			preparedStatement.setInt(5, ticket.getCategoria().getIdCategoria());
			preparedStatement.setString(6, ticket.getUsuario().getNome());
			preparedStatement.setString(7, ticket.getTipo().getTipo());
			preparedStatement.setString(8, ticket.getCategoria().getCategoria());
			preparedStatement.setString(9, ticket.getAssunto());
			preparedStatement.setString(10, ticket.getDescricao());
			preparedStatement.setString(11, ticket.getSolucao());
			preparedStatement.setInt(12, ticket.getNivelAnalista());
			preparedStatement.setString(13, ticket.getAnalista().getNome());

			if (ticket.getDataInicio() == null) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH mm ss ");
				String data = null;
				Date dataInicio = new java.sql.Timestamp(new java.util.Date().getTime());
				data = sdf.format(dataInicio);
				try {
					dataInicio = sdf.parse(data);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					for (int i = 0; i < 10; i++) {
						System.out.println("Aconteceu um erro na converção da Data no getDataInicio");
					}
					e.printStackTrace();
				}

				ticket.setDataInicio(dataInicio);

				preparedStatement.setTimestamp(14, new java.sql.Timestamp(new java.util.Date().getTime()));
			}

			preparedStatement.setString(15, ticket.getStatusTicket());

			count = new Integer(preparedStatement.executeUpdate());

			if (count != null && count.equals(1)) {
				connection.commit();
				idTicket = pesquisarUltimoIDTicket();
				ticket = pesquisarTicket(idTicket);
				System.out.println("Dados guardados");
			}
		} catch (SQLException e) {

			ticket = null;
			System.out.println("NAO FOI POSSIVEL SALVAR DADOS");
			e.printStackTrace();

		} finally {

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s: %s", this.getClass().getSimpleName(), msg));

				if (ticket != null) {

					System.err.println(String.format("\n Dados inserido: %s", ticket.toString()));

				}
			}

		}
		// fechar a conexão closeConnection
		// connection = ConnectionFactory.getConnection();
		ConnectionFactory.closeConnection();
		System.out.println("Conexão fechada");
		return ticket;
	}

	/**
	 * Metodo responsavel por consultar o objeto ticket na Base de dados.
	 * 
	 * @param ticket
	 * @return ticket
	 */
	@Override
	public List<Ticket> consultarTicket() {
		// Metodo que retorna todos os tickets cadastrados e seus campos

		// Declaração de variáveis

		Statement statement = null;

		ResultSet resultSet = null;

		String sql = null;

		String msg = null;

		Ticket ticket = null;

		List<Ticket> tickets = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			sql = "SELECT * FROM TICKET  ORDER BY IDTICKET ASC";

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(sql);

			if (resultSet.last()) {

				resultSet.beforeFirst();

				tickets = new ArrayList<>();

				while (resultSet.next()) {

					ticket = new Ticket();
					String data = null;
					ticket.setIdTicket(resultSet.getInt("IDTICKET"));
					ticket.getTipo().setTipo(resultSet.getString("TIPO"));
					ticket.setAssunto(resultSet.getString("ASSUNTO"));
					ticket.getUsuario().setNome(resultSet.getString("USUARIO"));
					ticket.setDescricao(resultSet.getString("DESCRICAO"));
					ticket.setSolucao(resultSet.getString("SOLUCAO"));
					ticket.setNivelAnalista(resultSet.getInt("NIVEL_ANALISTA"));
					ticket.getAnalista().setNome(resultSet.getString("ANALISTA"));

					// convertendo a data do banco para mostrar na view

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH mm ss ");
					data = sdf.format(resultSet.getTimestamp("DATA_INICIO"));
					ticket.setDataInicio(sdf.parse(data));

					ticket.setStatusTicket(resultSet.getString("STATUS"));

					// novamente a conversão
					if (ticket.getStatusTicket().equals("FECHADO")) {
						data = sdf.format(resultSet.getTimestamp("DATA_FIM"));
						ticket.setDataFim(sdf.parse(data));
					}
					ticket.getCategoria().setCategoria(resultSet.getString("CATEGORIA"));

					tickets.add(ticket);
				}

				msg = CONSULTA_SUCESSO;

			} else {

				msg = CONSULTA_NAO_SUCESSO;
			}

		} catch (Exception e) {
			// TODO: handle exception

			tickets = null;

			msg = CONSULTA_ERRO + e.getMessage();

			e.printStackTrace();

		} finally {

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s: %s", this.getClass().getSimpleName(), msg));

				if (tickets != null && tickets.size() > 0) {

					System.err.println("\n Tickets: ");

					for (Ticket ticketCadastrado : tickets) {

						System.err.println(ticketCadastrado.toString());
					}
				}
			}
		}

		// Saída da informação
		ConnectionFactory.getConnection();
		return tickets;
	}

	/**
	 * Metodo responsavel por pesquisar o objeto ticket na Base de dados atraves
	 * do nome do usuario.
	 * 
	 * @param usario
	 * @return ticket
	 */

	/*
	 * Atraves de somente uma letra o metodo fas a busca na coluna usuario e
	 * retorna os tickets que possuem aquela letra no nome do usuario
	 */

	@Override
	public List<Ticket> pesquisarUsuarioTicket(String usuario) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		List<Ticket> tickets = null;

		Ticket ticket = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			usuario = "%" + usuario + "%";

			query = "SELECT * FROM TICKET  WHERE USUARIO LIKE ? ORDER BY USUARIO ASC";

			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			preparedStatement.setString(1, usuario.toString());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.last()) {

				resultSet.beforeFirst();

				tickets = new ArrayList<>();

				while (resultSet.next()) {

					ticket = new Ticket();
					String data = null;
					ticket.setIdTicket(resultSet.getInt("IDTICKET"));
					ticket.getTipo().setTipo(resultSet.getString("TIPO"));
					ticket.setAssunto(resultSet.getString("ASSUNTO"));
					ticket.getUsuario().setNome(resultSet.getString("USUARIO"));
					ticket.setDescricao(resultSet.getString("DESCRICAO"));
					ticket.setSolucao(resultSet.getString("SOLUCAO"));
					ticket.setNivelAnalista(resultSet.getInt("NIVEL_ANALISTA"));
					ticket.getAnalista().setNome(resultSet.getString("ANALISTA"));
					// convertendo a data do banco para mostrar na view
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH mm ss ");
					data = sdf.format(resultSet.getTimestamp("DATA_INICIO"));
					try {
						ticket.setDataInicio(sdf.parse(data));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					ticket.setStatusTicket(resultSet.getString("STATUS"));

					// novamente a conversão
					if (ticket.getStatusTicket().equals("FECHADO")) {
						data = sdf.format(resultSet.getTimestamp("DATA_FIM"));
						try {
							ticket.setDataFim(sdf.parse(data));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					ticket.getCategoria().setCategoria(resultSet.getString("CATEGORIA"));
					ticket.setStatusTicket(resultSet.getString("STATUS"));

					tickets.add(ticket);
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception

			tickets = null;

			e.printStackTrace();
		}

		
		ConnectionFactory.closeConnection();
		return tickets;
	}

	/**
	 * Metodo responsavel por atualizar o objeto ticket na Base de dados.
	 * 
	 * @param ticket
	 * @return ticket
	 */

	/*
	 * Recebe o ticket para atualizar (non-Javadoc)
	 * 
	 * @see helpdesk.persistencia.ITicketDAO#atualizarTicket(helpdesk.vo.Ticket)
	 */
	@Override
	public Ticket atualizarTicket(Ticket ticket) {
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

			query = "UPDATE TICKET  SET NIVEL_ANALISTA = ?,ANALISTA = ?,STATUS = ?,DATA_FIM = ?,	SOLUCAO = ?,DESCRICAO = ?,IDTICKET = ? WHERE IDTICKET = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, ticket.getNivelAnalista().intValue());

			if (ticket.getAnalista() != null) {
				preparedStatement.setString(2, ticket.getAnalista().getNome());
			} else {
				preparedStatement.setNull(2, Types.NULL);
			}

			preparedStatement.setString(3, ticket.getStatusTicket());

			if (ticket.getStatusTicket().equals("FECHADO")) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH mm ss ");
				String data = null;
				Date dataInicio = new java.sql.Timestamp(new java.util.Date().getTime());
				data = sdf.format(dataInicio);
				try {
					dataInicio = sdf.parse(data);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					for (int i = 0; i < 10; i++) {
						System.out.println("Aconteceu um erro na converção da Data no getDataInicio");
					}
					e.printStackTrace();
				}
				ticket.setDataFim(dataInicio);

				preparedStatement.setTimestamp(4, new java.sql.Timestamp(new java.util.Date().getTime()));
			} else {
				preparedStatement.setNull(4, Types.NULL);

			}

			preparedStatement.setString(5, ticket.getSolucao());
			preparedStatement.setString(6, ticket.getDescricao());

			preparedStatement.setInt(7, ticket.getIdTicket().intValue());
			preparedStatement.setInt(8, ticket.getIdTicket().intValue());

			count = new Integer(preparedStatement.executeUpdate());

			if (count != null && count > 0) {

				connection.commit();

				msg = ATUALIZACAO_SUCESSO;

				ticket = pesquisarTicket(ticket.getIdTicket());

			} else {

				msg = ATUALIZACAO_NAO_SUCESSO;
			}

		} catch (SQLException e) {
			// TODO: handle exception

			msg = ATUALIZACAO_ERRO + e.getMessage();
			/*
			 * neste loop mostro na console como estava o Ticket que chegou para
			 * atualizar
			 */
			for (int i = 0; i <= 10; i++) {

				System.out.println("NAO FOI POSSIVEL ATUALIZAR " + ticket.getAnalista().getNome().toString()
						+ ticket.getStatusTicket().toString());
			}

			ticket = null;
			e.printStackTrace();

		} finally {

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s: %s", TicketDAO.class.getSimpleName(), msg));

				if (ticket != null) {

					System.err.println("\n Dados atualizados: " + ticket.toString());
				}

			}
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		return ticket;
	}

	/**
	 * Metodo responsavel por retornar o objeto ticket inserido na Base de
	 * dados.
	 * 
	 * @param idticket
	 * @return ticket
	 */

	/*
	 * com este metodo atravez do "pesquisarIDTicket" conseguimos o id para
	 * usa-lo com pequisa e retornar o usuario referente ao id
	 */
	@Override
	public Ticket pesquisarTicket(Integer id) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Ticket ticket = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM TICKET  WHERE IDTICKET = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, id.intValue());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				ticket = new Ticket();
				String data = null;

				ticket.setIdTicket(resultSet.getInt("IDTICKET"));
				ticket.getTipo().setTipo(resultSet.getString("TIPO"));
				ticket.setAssunto(resultSet.getString("ASSUNTO"));
				ticket.getUsuario().setNome(resultSet.getString("USUARIO"));
				ticket.setDescricao(resultSet.getString("DESCRICAO"));
				ticket.setSolucao(resultSet.getString("SOLUCAO"));
				ticket.setNivelAnalista(resultSet.getInt("NIVEL_ANALISTA"));
				ticket.getAnalista().setNome(resultSet.getString("ANALISTA"));
				// convertendo a data do banco para mostrar na view
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH mm ss ");
				data = sdf.format(resultSet.getTimestamp("DATA_INICIO"));
				try {
					ticket.setDataInicio(sdf.parse(data));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ticket.setStatusTicket(resultSet.getString("STATUS"));

				// novamente a conversão
				if (ticket.getStatusTicket().equals("FECHADO")) {
					data = sdf.format(resultSet.getTimestamp("DATA_FIM"));
					try {
						ticket.setDataFim(sdf.parse(data));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				ticket.getCategoria().setCategoria(resultSet.getString("CATEGORIA"));
				ticket.setStatusTicket(resultSet.getString("STATUS"));

			}

		} catch (SQLException e) {
			// TODO: handle exception

			ticket = null;

			e.printStackTrace();
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		return ticket;
	}

	/**
	 * este metodo retorna o ultimo id registrado no banco
	 * 
	 * @param id
	 * @return ticket
	 */

	// este metodo retorna o ultimo id registrado no banco
	@Override
	public Integer pesquisarUltimoIDTicket() {
		// TODO Auto-generated method stub
		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;
		
		Integer id = null;
		
		String query = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT MAX(TICKET.IDTICKET) FROM TICKET TICKET";

			preparedStatement = connection.prepareStatement(query);

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				id = new Integer(resultSet.getInt("MAX(TICKET.IDTICKET)"));
			}
		} catch (SQLException e) {
			for (int i = 0; i <= 10; i++) {
				System.out.println("NAO FOI POSSIVEL RETORNAR O ULTIMO ID");
			}
			e.printStackTrace();
		}
		ConnectionFactory.closeConnection();
		return id;
	}

	/**
	 * Metodo responsavel por exluir o objeto ticket da base de dados
	 * 
	 * @param ticket
	 * @return ticket
	 */

	public Boolean excluirTicket(Ticket ticket) {
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

			query = "DELETE FROM TICKET  WHERE IDTICKET = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, ticket.getIdTicket());

			count = new Integer(preparedStatement.executeUpdate());

			if (count != null && count.equals(1)) {

				connection.commit();

				flag = true;

				msg = "EXCLUSAO SUCESSO";

			} else {

				flag = false;

				msg = "EXCLUSAO NAO SUCESSO";
			}

		} catch (SQLException e) {
			// TODO: handle exception

			flag = null;

			msg = "EXCLUSAO NAO SUCESSO" + e.getMessage();

			e.printStackTrace();

		} finally {

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s: %s", this.getClass().getSimpleName(), msg));

				if (flag != null && flag.equals(true)) {

					System.err.println("\n Dados excluídos: " + ticket.toString());

				} else {

					System.err.println("\n Não foi possível excluir dados.");
				}
			}

		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		return flag;
	}

	/**
	 * Metodo responsavel por pesquisar o objeto ticket na Base de dados atraves
	 * do nome do analista.
	 * 
	 * @param analista
	 * @return ticket
	 */

	@Override
	public List<Ticket> pesquisarAnalistaTicket(String analista) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		List<Ticket> tickets = null;

		Ticket ticket = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			analista = "%" + analista.toUpperCase() + "%";

			query = "SELECT * FROM TICKET  WHERE ANALISTA LIKE ? ORDER BY IDPRIORIDADE ASC";

			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			preparedStatement.setString(1, analista.toString());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.last()) {

				resultSet.beforeFirst();

				tickets = new ArrayList<>();

				while (resultSet.next()) {

					ticket = new Ticket();
					String data = null;

					ticket.setIdTicket(resultSet.getInt("IDTICKET"));
					ticket.getTipo().setTipo(resultSet.getString("TIPO"));
					ticket.setAssunto(resultSet.getString("ASSUNTO"));
					ticket.getUsuario().setNome(resultSet.getString("USUARIO"));
					ticket.setDescricao(resultSet.getString("DESCRICAO"));
					ticket.setSolucao(resultSet.getString("SOLUCAO"));
					ticket.setNivelAnalista(resultSet.getInt("NIVEL_ANALISTA"));
					ticket.getAnalista().setNome(resultSet.getString("ANALISTA"));
					// convertendo a data do banco para mostrar na view
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH mm ss ");
					data = sdf.format(resultSet.getTimestamp("DATA_INICIO"));
					try {
						ticket.setDataInicio(sdf.parse(data));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					ticket.setStatusTicket(resultSet.getString("STATUS"));

					// novamente a conversão
					if (ticket.getStatusTicket().equals("FECHADO")) {
						data = sdf.format(resultSet.getTimestamp("DATA_FIM"));
						try {
							ticket.setDataFim(sdf.parse(data));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					ticket.getCategoria().setCategoria(resultSet.getString("CATEGORIA"));
					ticket.setStatusTicket(resultSet.getString("STATUS"));

					tickets.add(ticket);
				}
			}

		} catch (SQLException e) {
			// TODO: handle exception

			tickets = null;

			e.printStackTrace();
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		return tickets;
	}

	/**
	 * Metodo responsavel por retornar para o objeto pagina/ticket a prioridade.
	 * 
	 * @param
	 * @return prioridade
	 */

	public List<Prioridade> buscarPrioridades() {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		List<Prioridade> prioridadeList = null;

		Prioridade prioridade = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM PRIORIDADE PRIORIDADE ORDER BY PRIORIDADE.PRIORIDADE ASC";

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet.last()) {

				resultSet.beforeFirst();

				prioridadeList = new ArrayList<>();

				while (resultSet.next()) {

					prioridade = new Prioridade();

					prioridade.setIdPrioridade(resultSet.getInt("IDPRIORIDADE"));

					prioridade.setPrioridade(resultSet.getString("PRIORIDADE"));

					prioridadeList.add(prioridade);

				}

				msg = CONSULTA_SUCESSO;

			} else {

				msg = CONSULTA_NAO_SUCESSO;
			}

		} catch (Exception e) {
			// TODO: handle exception

			prioridadeList = null;

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

					if (prioridadeList != null && prioridadeList.size() > 0) {

						System.err.print(" Dados: ");

						for (Prioridade user : prioridadeList) {

							System.err.println(user.toString());
						}
					}
				}
			}
		}

		
		ConnectionFactory.closeConnection();
		return prioridadeList;

	}

	/**
	 * Metodo responsavel por setar para o objeto ticket a prioridade.
	 * 
	 * @param idPrioridade
	 * @return prioridade
	 */

	public Prioridade pesquisarIdPrioridade(Integer idPrioridade) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Prioridade prioridade = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM PRIORIDADE PRIORIDADE WHERE IDPRIORIDADE = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, idPrioridade.intValue());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				prioridade = new Prioridade();

				prioridade.setIdPrioridade(resultSet.getInt("IDPRIORIDADE"));

				prioridade.setPrioridade(resultSet.getString("PRIORIDADE"));

			}

		} catch (SQLException e) {
			// TODO: handle exception

			prioridade = null;

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
		return prioridade;
	}

	/**
	 * Metodo responsavel por setar para o objeto ticket a categoria.
	 * 
	 * @param idCategoria
	 * @return categoria
	 */

	@Override
	public Categoria pesquisarIdCategoria(Integer idCategoria) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Categoria categoria = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM CATEGORIA CATEGORIA WHERE IDCATEGORIA = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, idCategoria.intValue());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				categoria = new Categoria();

				categoria.setIdCategoria(resultSet.getInt("IDCATEGORIA"));

				categoria.setCategoria(resultSet.getString("CATEGORIA"));

			}

		} catch (SQLException e) {
			// TODO: handle exception

			categoria = null;

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
		return categoria;
	}

	/**
	 * Metodo responsavel por retornar para o objeto pagina/ticket a categoria.
	 * 
	 * @param
	 * @return categoria
	 */

	@Override
	public List<Categoria> buscarCategorias() {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		List<Categoria> categoriaList = null;

		Categoria categoria = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM CATEGORIA CATEGORIA ORDER BY CATEGORIA.CATEGORIA ASC";

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet.last()) {

				resultSet.beforeFirst();

				categoriaList = new ArrayList<>();

				while (resultSet.next()) {

					categoria = new Categoria();

					categoria.setIdCategoria(resultSet.getInt("IDCATEGORIA"));

					categoria.setCategoria(resultSet.getString("CATEGORIA"));

					categoriaList.add(categoria);

				}

				msg = CONSULTA_SUCESSO;

			} else {

				msg = CONSULTA_NAO_SUCESSO;
			}

		} catch (Exception e) {
			// TODO: handle exception

			categoriaList = null;

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

					if (categoriaList != null && categoriaList.size() > 0) {

						System.err.print(" Dados: ");

						for (Categoria user : categoriaList) {

							System.err.println(user.toString());
						}
					}
				}
			}
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		return categoriaList;

	}

	/**
	 * Metodo responsavel por setar para o objeto ticket o tipo.
	 * 
	 * @param idTipo
	 * @return tipo
	 */

	@Override
	public Tipo pesquisarIdTipo(Integer idTipo) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Tipo tipo = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM TIPO_TICKET TIPO WHERE IDTIPO = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, idTipo.intValue());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				tipo = new Tipo();

				tipo.setIdTipo(resultSet.getInt("IDTIPO"));

				tipo.setTipo(resultSet.getString("TIPO"));

			}

		} catch (SQLException e) {
			// TODO: handle exception

			tipo = null;

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
		return tipo;
	}

	/**
	 * Metodo responsavel por retornar para o objeto pagina/ticket o tipo.
	 * 
	 * @param
	 * @return tipo
	 */

	@Override
	public List<Tipo> buscarTipos() {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		List<Tipo> tipoList = null;

		Tipo tipo = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM TIPO_TICKET TIPO ORDER BY TIPO.TIPO ASC";

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet.last()) {

				resultSet.beforeFirst();

				tipoList = new ArrayList<>();

				while (resultSet.next()) {

					tipo = new Tipo();

					tipo.setIdTipo(resultSet.getInt("IDTIPO"));

					tipo.setTipo(resultSet.getString("TIPO"));

					tipoList.add(tipo);

				}

				msg = CONSULTA_SUCESSO;

			} else {

				msg = CONSULTA_NAO_SUCESSO;
			}

		} catch (Exception e) {
			// TODO: handle exception

			tipoList = null;

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

					if (tipoList != null && tipoList.size() > 0) {

						System.err.print(" Dados: ");

						for (Tipo user : tipoList) {

							System.err.println(user.toString());
						}
					}
				}
			}
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		return tipoList;

	}

	/**
	 * Metodo responsavel por setar para o objeto ticket o analista.
	 * 
	 * @param idAnalista
	 * @return analista
	 */

	@Override
	public Analista pesquisarIdAnalista(Integer idAnalista) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		Analista analista = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM ANALISTA  WHERE IDANALISTA = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, idAnalista.intValue());

			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {

				analista = new Analista();

				analista.setIdAnalista(resultSet.getInt("IDANALISTA"));

				analista.setNome(resultSet.getString("NOME"));

				analista.setNivelAnalista(resultSet.getInt("NIVEL_ANALISTA"));

			}

		} catch (SQLException e) {
			// TODO: handle exception

			analista = null;

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
		return analista;
	}

	/**
	 * Metodo responsavel por retornar para o objeto pagina/ticket o Analista.
	 * 
	 * @param
	 * @return analista
	 */

	@Override
	public List<Analista> buscarAnalistas(Integer nivelAnalista) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		List<Analista> analistaList = null;

		Analista analista = null;

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT * FROM ANALISTA  WHERE PERMISSAO = 'ANALISTA' AND NIVEL_ANALISTA = ?";

			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			preparedStatement.setInt(1, nivelAnalista.intValue());

			resultSet = preparedStatement.executeQuery();
			
			
			
			if (resultSet.last()) {
				resultSet.beforeFirst();
				analistaList = new ArrayList<>();
				
				while(resultSet.next()){
					analista = new Analista();
				
					analista.setIdAnalista(resultSet.getInt("IDANALISTA"));
					analista.setNome(resultSet.getString("NOME"));

					analistaList.add(analista);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception

			analistaList = null;

			msg = CONSULTA_ERRO + e.getMessage();

			e.printStackTrace();

		} finally

		{
			if (FLAG.equals(true)) {

				if (msg != null) {

					System.err.println(String.format("\n %s: %s", this.getClass().getSimpleName(), msg));

					if (analistaList != null && analistaList.size() > 0) {

						System.err.print(" Dados: ");

						for (Analista user : analistaList) {

							System.err.println(user.toString());
						}
					}
				}
			}
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		return analistaList;

	}

	/**
	 * Metodo responsavel por retornar para o objeto pagina/ticket a lista de ID
	 * dos tickets.
	 * 
	 * @param
	 * @return idticket
	 */
	@Override
	public List<Id> buscarIds() {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		Statement statement = null;

		ResultSet resultSet = null;

		String query = null;

		String msg = null;

		List<Id> idList = null;

		Id id = null;
		

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT IDTICKET FROM TICKET ORDER BY IDTICKET ASC";

			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			resultSet = statement.executeQuery(query);

			if (resultSet.last()) {

				resultSet.beforeFirst();

				idList = new ArrayList<>();
				

				while (resultSet.next()) {

					id = new Id();
					
					

					id.setId(resultSet.getInt("IDTICKET"));
					
					id.setIdString(id.getId().toString());

					idList.add(id);

				}

				msg = CONSULTA_SUCESSO;

			} else {

				msg = CONSULTA_NAO_SUCESSO;
			}

		} catch (Exception e) {
			// TODO: handle exception

			idList = null;

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

					if (idList != null && idList.size() > 0) {

						System.err.print(" Dados: ");

						for (Id user : idList) {

							System.err.println(user.toString());
						}
					}
				}
			}
		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		return idList;

	}

	/**
	 * Metodo responsavel por retornar o relatorio de tickets fechado.
	 * 
	 * @param status
	 * @return relatorio
	 */

	@Override
	public List<Relatorio> relatorioDeFechados(String status) {
		ResultSet resultSet = null;
		CallableStatement callstmt = null;
		List<Relatorio> relatorioList = null;
		Relatorio relatorio = null;

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {
			callstmt = connection.prepareCall("{ CALL GERA_REL(?,?)}");

			callstmt.setString(1, status.toString());
			callstmt.registerOutParameter(2, OracleTypes.CURSOR);
			callstmt.executeQuery();

			resultSet = (ResultSet) callstmt.getObject(2);

			relatorioList = new ArrayList<>();

			while (resultSet.next()) {

				relatorio = new Relatorio();
				String data = null;
				relatorio.setTipo(resultSet.getString("TIPO"));
				relatorio.setCategoria(resultSet.getString("CATEGORIA"));
				relatorio.setAssunto(resultSet.getString("ASSUNTO"));
				relatorio.setDescricao(resultSet.getString("DESCRICAO"));

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH mm ss ");
				data = sdf.format(resultSet.getTimestamp("DATA_INICIO"));
				try {
					relatorio.setDataInicio(sdf.parse(data));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					for (int i = 0; i < 10; i++) {
						System.out.println(
								"Aconteceu um erro na converção da Data do Relatório (resultSet.getTimestamp(DATA_INICIO))");
					}
				}
				// novamente a conversão
				data = sdf.format(resultSet.getTimestamp("DATA_FIM"));
				try {
					relatorio.setDataFim(sdf.parse(data));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					for (int i = 0; i < 10; i++) {
						System.out.println(
								"Aconteceu um erro na converção da Data do Relatório (resultSet.getTimestamp(DATA_FIM))");
					}
				}

				relatorio.setDataInicio(resultSet.getTimestamp("DATA_INICIO"));
				relatorio.setSolucao(resultSet.getString("SOLUCAO"));

				relatorioList.add(relatorio);

			}
			if (callstmt != null)
				callstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Return code gerado: " + e.getErrorCode());
			System.out.println("Mensagem gerada   : " + e.getMessage());
			return null;
		}
		ConnectionFactory.closeConnection();
		return relatorioList;
	}

	/**
	 * Metodo responsavel por retornar o relatorio de tickets abertos.
	 * 
	 * @param status
	 * @return relatorio
	 */

	@Override
	public List<Relatorio> relatorioDeAbertos(String status) {
		ResultSet resultSet = null;
		CallableStatement callstmt = null;
		List<Relatorio> relatorioList = null;
		Relatorio relatorio = null;

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {
			// callstmt = connection.prepareCall("{ CALL ? :=
			// GERA_REL_ABERTO(?,?)}");
			callstmt = connection.prepareCall("{ CALL GERA_REL(?,?)}");

			callstmt.setString(1, status.toString());
			callstmt.registerOutParameter(2, OracleTypes.CURSOR);
			callstmt.executeQuery();

			resultSet = (ResultSet) callstmt.getObject(2);

			relatorioList = new ArrayList<>();

			while (resultSet.next()) {

				relatorio = new Relatorio();
				String data = null;
				relatorio.setTipo(resultSet.getString("TIPO"));
				relatorio.setCategoria(resultSet.getString("CATEGORIA"));
				relatorio.setAssunto(resultSet.getString("ASSUNTO"));
				relatorio.setDescricao(resultSet.getString("DESCRICAO"));

				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH mm s ");
				data = sdf.format(resultSet.getTimestamp("DATA_INICIO"));
				try {
					relatorio.setDataInicio(sdf.parse(data));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					for (int i = 0; i < 10; i++) {
						System.out.println(
								"Aconteceu um erro na converção da Data do Relatório (resultSet.getTimestamp(DATA_INICIO))");
					}
				}
				
				relatorio.setSolucao(resultSet.getString("SOLUCAO"));

				relatorioList.add(relatorio);

			}
			if (callstmt != null)
				callstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Return code gerado: " + e.getErrorCode());
			System.out.println("Mensagem gerada   : " + e.getMessage());
			return null;
		}
		ConnectionFactory.closeConnection();
		return relatorioList;
	}
	
	/**
	 * Metodo responsavel por relatorio de tickets abertos passando como paramentro o numero
	 * de dias.
	 * 
	 * @param numeroDeDias
	 * @return lista de relatorios 
	 */

	@Override
	public List<Relatorio> relatoriosData(Integer numeroDeDias) {
		ResultSet resultSet = null;
		CallableStatement callstmt = null;
		List<Relatorio> relatorioList = null;
		Relatorio relatorio = null;

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {
			 callstmt = connection.prepareCall("{ CALL ? :=	 retorna_dias()}");
			
			
			callstmt.registerOutParameter(1, OracleTypes.CURSOR);
			callstmt.executeQuery();

			resultSet = (ResultSet) callstmt.getObject(1);

			
			
			
			if (resultSet.next()) {
				
				
				
				relatorioList = new ArrayList<>();
				
				
				while (resultSet.next()){
					
				
					relatorio = new Relatorio();
					String data = null;
					
					relatorio.setIdTicket(resultSet.getInt("IDTICKET"));
					relatorio.setTipo(resultSet.getString("TIPO"));
					relatorio.setCategoria(resultSet.getString("CATEGORIA"));
					relatorio.setAssunto(resultSet.getString("ASSUNTO"));
					relatorio.setDescricao(resultSet.getString("DESCRICAO"));

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH mm ss ");
					data = sdf.format(resultSet.getTimestamp("DATA_INICIO"));
					try {
						relatorio.setDataInicio(sdf.parse(data));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						for (int i = 0; i < 10; i++) {
							System.out.println(
								"Aconteceu um erro na converção da Data do Relatório (resultSet.getTimestamp(DATA_INICIO))");
						}
					}
					relatorio.setDiasAberto(new Integer((int) resultSet.getFloat("DIFERENCA_DATAS")));
					
					if(relatorio.getDiasAberto()>numeroDeDias){
						relatorio.setDiasAberto(relatorio.getDiasAberto());
						//relatorio.setDiasAberto(deixaInteiro(relatorio.getDiasAberto()));
						relatorioList.add(relatorio);
					}
					

				}
			}
				if (callstmt != null){
					callstmt.close();
				}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Return code gerado: " + e.getErrorCode());
			System.out.println("Mensagem gerada   : " + e.getMessage());
			return null;
		}
		
		ConnectionFactory.closeConnection();
		return relatorioList;
	}

	/**
	 * Metodo responsavel por validar no banco se o nome do analista e seu nivel selecionado 
	 * pelo usuario, correspondem
	 * 
	 * @param objeto ticket
	 * @return uma flag(true para verdadeiro e false para falso)
	 */
	@Override
	public Boolean validaAnalistaEnivel(Ticket ticket) {
		// TODO Auto-generated method stub

		// Declaração de variáveis

		PreparedStatement preparedStatement = null;
		ResultSet  resultSet = null;
		
		String query = null;

		String msg = null;

		Boolean flag = null;

		

		// Processamento dos dados

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {

			query = "SELECT NIVEL_ANALISTA,NOME FROM ANALISTA  WHERE NIVEL_ANALISTA = ? AND NOME = ?";

			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1,ticket.getNivelAnalista() );
			preparedStatement.setString(2,ticket.getAnalista().getNome() );

			resultSet =preparedStatement.executeQuery();

			if (resultSet.next()) {

				connection.commit();

				flag = true;

				msg = "NOME E NIVEL DE ANALISTA CONDIZEM";

			} else {

				flag = false;

				msg = "NOME E NIVEL DE ANALISTA NÃO CONDIZEM";
			}

		} catch (SQLException e) {
			// TODO: handle exception

			flag = null;

			msg = "NÃO FOI POSSIVEL CONSULTAR A TABELA ANALISTA" + e.getMessage();

			e.printStackTrace();

		} finally {

			if (FLAG.equals(true) && msg != null) {

				System.err.println(String.format("\n %s: %s", this.getClass().getSimpleName(), msg));

				if (flag != null && flag.equals(true)) {

					System.err.println("\nDADOS VALIDADOS " + ticket.getNivelAnalista().toString()+""+ticket.getAnalista().getNome());

				} else {

					System.err.println("NÃO FOI POSSIVEL CONSULTAR A TABELA ANALISTA");
				}
			}

		}

		// Saída da informação
		ConnectionFactory.closeConnection();
		return flag;
	}

	@Override
	public List<Relatorio> relatoriosDataFechados(Integer numeroDeDiasFechados) {
		ResultSet resultSet = null;
		CallableStatement callstmt = null;
		List<Relatorio> relatorioList = null;
		Relatorio relatorio = null;

		connection = ConnectionFactory.getConnection();

		if (connection == null) {

			return null;
		}

		try {
			 callstmt = connection.prepareCall("{ CALL ? :=	 retorna_dias_Fechados()}");
			
			
			callstmt.registerOutParameter(1, OracleTypes.CURSOR);
			callstmt.executeQuery();

			resultSet = (ResultSet) callstmt.getObject(1);

			
			
			
			if (resultSet.next()) {
				
				
				
				relatorioList = new ArrayList<>();
				
				
				while (resultSet.next()){
					
				
					relatorio = new Relatorio();
					String data = null;
					
					relatorio.setIdTicket(resultSet.getInt("IDTICKET"));
					relatorio.setTipo(resultSet.getString("TIPO"));
					relatorio.setCategoria(resultSet.getString("CATEGORIA"));
					relatorio.setAssunto(resultSet.getString("ASSUNTO"));
					relatorio.setDescricao(resultSet.getString("DESCRICAO"));

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH mm ss ");
					data = sdf.format(resultSet.getTimestamp("DATA_INICIO"));
					try {
						relatorio.setDataInicio(sdf.parse(data));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						for (int i = 0; i < 10; i++) {
							System.out.println(
								"Aconteceu um erro na converção da Data do Relatório (resultSet.getTimestamp(DATA_INICIO))");
						}
					}
					data = null;
					data = sdf.format(resultSet.getTimestamp("DATA_FIM"));
					try {
						relatorio.setDataFim(sdf.parse(data));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						for (int i = 0; i < 10; i++) {
							System.out.println(
								"Aconteceu um erro na converção da Data do Relatório (resultSet.getTimestamp(DATA_FIM))");
						}
					}
					relatorio.setDiasFechados(new Integer((int) resultSet.getFloat("DIFERENCA_DATAS")));
					
					if(relatorio.getDiasFechados()>numeroDeDiasFechados){
						relatorio.setDiasFechados(relatorio.getDiasFechados());
						relatorioList.add(relatorio);
					}
					

				}
			}
				if (callstmt != null){
					callstmt.close();
				}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Return code gerado: " + e.getErrorCode());
			System.out.println("Mensagem gerada   : " + e.getMessage());
			return null;
		}
		
		ConnectionFactory.closeConnection();
		return relatorioList;
	}

}

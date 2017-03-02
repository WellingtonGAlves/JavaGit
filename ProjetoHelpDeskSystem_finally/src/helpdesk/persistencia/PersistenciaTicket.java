/**
 package helpdesk.persistencia
 */
package helpdesk.persistencia;


/**
 * Imports necessarios
 */
import java.util.List;

import helpdesk.vo.Analista;
import helpdesk.vo.Categoria;
import helpdesk.vo.Id;
import helpdesk.vo.Prioridade;
import helpdesk.vo.Relatorio;
import helpdesk.vo.Ticket;
import helpdesk.vo.Tipo;

/**
 * Classe PersistenciaTicket.
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class PersistenciaTicket implements ITicketDAO {

	/**
	 * Variaveis de instancia
	 */
	public TicketDAO ticketDAO;


	public PersistenciaTicket() {

		ticketDAO = new TicketDAO();

	}
	
	
	/**
	 * Metodos que implementam ITicketDAO
	 * Override
	 */

	@Override
	public Ticket salvarTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return ticketDAO.salvarTicket(ticket);
	}

	@Override
	public List<Ticket> consultarTicket() {
		// TODO Auto-generated method stub
		return ticketDAO.consultarTicket();
	}

	@Override
	public List<Ticket> pesquisarUsuarioTicket(String usuario) {
		// TODO Auto-generated method stub
		return ticketDAO.pesquisarUsuarioTicket(usuario);
	}

	@Override
	public Ticket atualizarTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return ticketDAO.atualizarTicket(ticket);
	}

	@Override
	public Integer pesquisarUltimoIDTicket() {
		// TODO Auto-generated method stub
		return ticketDAO.pesquisarUltimoIDTicket();
	}

	@Override
	public Ticket pesquisarTicket(Integer id) {
		// TODO Auto-generated method stub
		return ticketDAO.pesquisarTicket(id);
	}

	@Override
	public Boolean excluirTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return ticketDAO.excluirTicket(ticket);
	}

	@Override
	public List<Ticket> pesquisarAnalistaTicket(String analista) {
		// TODO Auto-generated method stub
		return ticketDAO.pesquisarAnalistaTicket(analista);
	}

	@Override
	public List<Prioridade> buscarPrioridades() {
		// TODO Auto-generated method stub
		return ticketDAO.buscarPrioridades();
	}

	@Override
	public Prioridade pesquisarIdPrioridade(Integer idPrioridade) {
		// TODO Auto-generated method stub
		return ticketDAO.pesquisarIdPrioridade(idPrioridade);
	}

	@Override
	public Categoria pesquisarIdCategoria(Integer idCategoria) {
		// TODO Auto-generated method stub
		return ticketDAO.pesquisarIdCategoria(idCategoria);
	}

	@Override
	public List<Categoria> buscarCategorias() {
		// TODO Auto-generated method stub
		return ticketDAO.buscarCategorias();
	}

	@Override
	public Tipo pesquisarIdTipo(Integer idTipo) {
		// TODO Auto-generated method stub
		return ticketDAO.pesquisarIdTipo(idTipo);
	}

	@Override
	public List<Tipo> buscarTipos() {
		// TODO Auto-generated method stub
		return ticketDAO.buscarTipos();
	}

	@Override
	public Analista pesquisarIdAnalista(Integer idAnalista) {
		// TODO Auto-generated method stub
		return ticketDAO.pesquisarIdAnalista(idAnalista);
	}

	@Override
	public List<Analista> buscarAnalistas(Integer nivelAnalista) {
		// TODO Auto-generated method stub
		return ticketDAO.buscarAnalistas(nivelAnalista);
	}

	@Override
	public List<Relatorio> relatorioDeFechados(String status) {
		// TODO Auto-generated method stub
		return ticketDAO.relatorioDeFechados(status);
	}

	@Override
	public List<Relatorio> relatorioDeAbertos(String status) {
		// TODO Auto-generated method stub
		return ticketDAO.relatorioDeAbertos(status);
	}

	@Override
	public List<Id> buscarIds() {
		// TODO Auto-generated method stub
		return ticketDAO.buscarIds();
	}


	@Override
	public List<Relatorio> relatoriosData(Integer numeroDeDias) {
		// TODO Auto-generated method stub
		return ticketDAO.relatoriosData(numeroDeDias);
	}


	@Override
	public Boolean validaAnalistaEnivel(Ticket ticket) {
		// TODO Auto-generated method stub
		return ticketDAO.validaAnalistaEnivel(ticket);
	}


	@Override
	public List<Relatorio> relatoriosDataFechados(Integer numeroDeDiasFechados) {
		// TODO Auto-generated method stub
		return ticketDAO.relatoriosDataFechados(numeroDeDiasFechados);
	}

}

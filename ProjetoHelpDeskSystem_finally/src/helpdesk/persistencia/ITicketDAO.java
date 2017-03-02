/**
 * package helpdesk.persistencia
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
 * Interface ITicketDao
 * responsavel por definir quais metodos devem ser utilizados 
 * pela classe que vai implementa-la
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */

interface ITicketDAO {

	public Ticket salvarTicket(Ticket ticket);

	public List<Ticket> consultarTicket();

	public Ticket atualizarTicket(Ticket ticket);

	public List<Ticket> pesquisarUsuarioTicket(String usuario);

	public Integer pesquisarUltimoIDTicket();

	public Ticket pesquisarTicket(Integer id);

	public Boolean excluirTicket(Ticket ticket);

	public List<Ticket> pesquisarAnalistaTicket(String analista);

	public Prioridade pesquisarIdPrioridade(Integer idPrioridade);

	public List<Prioridade> buscarPrioridades();

	public Categoria pesquisarIdCategoria(Integer idCategoria);

	public List<Categoria> buscarCategorias();

	public Tipo pesquisarIdTipo(Integer idTipo);

	public List<Tipo> buscarTipos();

	public Analista pesquisarIdAnalista(Integer idAnalista);

	public List<Analista> buscarAnalistas(Integer nivelAnalista);

	public List<Relatorio> relatorioDeFechados(String status);

	public List<Relatorio> relatorioDeAbertos(String status);

	public List<Id> buscarIds();
	
	public List<Relatorio> relatoriosData(Integer numeroDeDias);
	
	public Boolean validaAnalistaEnivel(Ticket ticket);
	
	public List<Relatorio> relatoriosDataFechados(Integer numeroDeDiasFechados);

}
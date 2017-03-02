/**
package helpdesk.model */

package helpdesk.model;

/**
 * Imports necessarios
 */

import java.util.List;

import helpdesk.persistencia.PersistenciaTicket;
import helpdesk.vo.Analista;
import helpdesk.vo.Categoria;
import helpdesk.vo.Id;
import helpdesk.vo.Prioridade;
import helpdesk.vo.Relatorio;
import helpdesk.vo.Ticket;
import helpdesk.vo.Tipo;

import static helpdesk.util.Utilitaria.validarStatus;;

/**
 * Classe Manutenção do Ticket.
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class ManutencaoTicket {

	/**
	 * Variavel de Instancia
	 * 
	 */

	private PersistenciaTicket persistenciaTicket;

	/**
	 * Metodo construtor ManutencaoTicket que instancia novo objeto Persistencia
	 * 
	 */
	public ManutencaoTicket() {

		persistenciaTicket = new PersistenciaTicket();

	}

	/**
	 * Metodo responsavel por salvar o Ticket
	 * 
	 * @param ticket
	 * @return ticket
	 */
	public Ticket salvarTicket(Ticket ticket) {

		ticket.setStatusTicket("ABERTO");
		ticket.setSolucao("A PREENCHER");
		return persistenciaTicket.salvarTicket(ticket);

	}

	/**
	 * Metodo responsavel por consultar o Ticket
	 * 
	 * @param ticket
	 * @return ticket
	 */
	public List<Ticket> consultarTicket() {
		// TODO Auto-generated method stub
		return persistenciaTicket.consultarTicket();
	}

	/**
	 * Metodo responsavel por pesquisar o Ticket
	 * 
	 * @param usuario
	 * @return ticket
	 */
	public List<Ticket> pesquisarUsuarioTicket(String usuario) {

		// Saída da informação

		return persistenciaTicket.pesquisarUsuarioTicket(usuario);

	}

	/**
	 * @param ticket
	 *            the ticket to set
	 * @return persistenciaTicket.atualizar(ticket)
	 */
	public Ticket atualizarTicket(Ticket ticket) {

		if (validarStatus(ticket.getStatusTicket())) {
			ticket.setStatusTicket("ABERTO");
		} else {
			ticket.setStatusTicket("FECHADO");
		}

		return persistenciaTicket.atualizarTicket(ticket);
	}

	/**
	 * Metodo responsavel por pesquisar o ultimo idTicket
	 * 
	 * @param id
	 * @return ticket
	 */

	public Integer pesquisarUltimoIDTicket(Integer id) {

		// TODO Auto-generated method stub
		return persistenciaTicket.pesquisarUltimoIDTicket();
	}

	/**
	 * Metodo responsavel por pesquisar o idTicket
	 * 
	 * @param id
	 * @return ticket
	 */

	public Ticket pesquisarTicket(Integer id) {

		return persistenciaTicket.pesquisarTicket(id);
	}

	/**
	 * Metodo responsavel por excluir o Ticket
	 * 
	 * @param ticket
	 * @return boolean
	 */

	public Boolean excluirTicket(Ticket ticket) {
		// TODO Auto-generated method stub
		return persistenciaTicket.excluirTicket(ticket);
	}

	/**
	 * Metodo responsavel por pequisar o Ticket passando como parametro o nome
	 * do analista
	 * 
	 * @param analista
	 * @return ticket
	 */

	public List<Ticket> pesquisarAnalistaTicket(String analista) {
		// TODO Auto-generated method stub
		return persistenciaTicket.pesquisarAnalistaTicket(analista);
	}

	/**
	 * Metodo responsavel por pequisar o Ticket passando como parametro o
	 * idTicket
	 * 
	 * @param idTicket
	 * @return ticket
	 */

	public Ticket pesquisarIdTicket(Integer idTicket) {
		// TODO Auto-generated method stub
		return persistenciaTicket.pesquisarTicket(idTicket);
	}

	/**
	 * Metodo responsavel por setar as prioridades no Ticket passando como
	 * parametro a prioridade para popular o ticket
	 * 
	 * @param
	 * @return ticket
	 */

	public List<Prioridade> buscarPrioridades() {
		// TODO Auto-generated method stub
		return persistenciaTicket.buscarPrioridades();
	}

	/**
	 * Metodo responsavel por pesquisar as prioridades passando o ID da
	 * prioridade como parametro idPrioridade
	 * 
	 * @param idPrioridade
	 * @return Prioridade
	 */

	public Prioridade pesquisarIdPrioridade(Integer idPrioridade) {
		// TODO Auto-generated method stub
		return persistenciaTicket.pesquisarIdPrioridade(idPrioridade);
	}
	
	
	/**
	 * Metodo responsavel por pesquisar as categorias para
	 * mostrar na comboBox
	 * 
	 * 
	 * @return lista de categorias
	 */
	public List<Categoria> buscarCategorias() {
		// TODO Auto-generated method stub
		return persistenciaTicket.buscarCategorias();
	}

	/**
	 * Metodo responsavel por pesquisar as categorias passando o ID da categoria
	 * como parametro idCategoria
	 * 
	 * @param idCategoria
	 * @return categoria
	 */

	public Categoria pesquisarIdCategoria(Integer idCategoria) {
		// TODO Auto-generated method stub
		return persistenciaTicket.pesquisarIdCategoria(idCategoria);
	}

	/**
	 * Metodo responsavel por popular o objeto ticket e a pagina
	 * 
	 * @param
	 * @return
	 */

	public List<Analista> buscarAnalistas(Integer nivelAnalista) {
		// TODO Auto-generated method stub
		return persistenciaTicket.buscarAnalistas(nivelAnalista);
	}

	/**
	 * Metodo responsavel por popular o objeto ticket e a pagina
	 * 
	 * @param
	 * @return
	 */

	public Analista pesquisarIdAnalista(Integer idAnalista) {
		// TODO Auto-generated method stub
		return persistenciaTicket.pesquisarIdAnalista(idAnalista);
	}

	/**
	 * Metodo responsavel por popular o objeto ticket e a pagina
	 * 
	 * @param
	 * @return
	 */

	public List<Tipo> buscarTipos() {
		// TODO Auto-generated method stub
		return persistenciaTicket.buscarTipos();
	}

	/**
	 * Metodo responsavel por popular o objeto ticket e a pagina
	 * 
	 * @param
	 * @return
	 */

	public Tipo pesquisarIdTipo(Integer idTipo) {
		// TODO Auto-generated method stub
		return persistenciaTicket.pesquisarIdTipo(idTipo);
	}

	/**
	 * Metodo responsavel por popular o objeto ticket e a pagina
	 * 
	 * @param
	 * @return
	 */

	public List<Relatorio> relatorioDeFechados(String status) {
		// TODO Auto-generated method stub
		return persistenciaTicket.relatorioDeFechados(status);
	}

	/**
	 * Metodo responsavel por popular o objeto ticket e a pagina
	 * 
	 * @param
	 * @return
	 */

	public List<Relatorio> relatorioDeAbertos(String status) {
		// TODO Auto-generated method stub
		return persistenciaTicket.relatorioDeAbertos(status);
	}

	/**
	 * Metodo responsavel por popular o objeto ticket e a pagina
	 * 
	 * @param
	 * @return
	 */

	public List<Id> buscarIds() {
		// TODO Auto-generated method stub
		return persistenciaTicket.buscarIds();
	}

	/**
	 * Metodo responsavel por pesquisar tickets abertos 
	 * passndo como paramentro o numero de dias aberto
	 * 
	 * @param numero de dias aberto
	 * @return Relatorio de tickets abertos
	 */
	
	public List<Relatorio> relatoriosData(Integer numeroDeDias) {
		// TODO Auto-generated method stub
		return persistenciaTicket.relatoriosData(numeroDeDias);
	}
	
	/**
	 * Metodo responsavel por validar se o nivel do analista selecionado e o analista correspondem
	 * true para 'sim'
	 * e
	 * false 'não'
	 * @param objeto ticket
	 * @return uma flag 
	 */
	
	public Boolean validaAnalistaEnivel(Ticket ticket) {
		// TODO Auto-generated method stub
		return persistenciaTicket.validaAnalistaEnivel(ticket);
	}

	public List<Relatorio> relatoriosDataFechados(Integer numeroDeDiasFechados) {
		// TODO Auto-generated method stub
		return persistenciaTicket.relatoriosDataFechados(numeroDeDiasFechados);
	}

}

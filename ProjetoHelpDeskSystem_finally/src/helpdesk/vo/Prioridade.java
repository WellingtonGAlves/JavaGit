/**
 * package helpdesk.vo
 */
package helpdesk.vo;

/**
 * Classe Prioridade.
 * Utilizada para popular a pagina de cadastro (combobox) com o parametro prioridade
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class Prioridade {

	/**
	 * Variáveis de instância
	 */

	private Integer idPrioridade;

	private String prioridade;

	

	/**
	 * Função construtora
	 */

	public Prioridade() {

	}



	/**
	 * @return the idPrioridade
	 */
	public Integer getIdPrioridade() {
		return idPrioridade;
	}



	/**
	 * @param idPrioridade the idPrioridade to set
	 */
	public void setIdPrioridade(Integer idPrioridade) {
		this.idPrioridade = idPrioridade;
	}



	/**
	 * @return the prioridade
	 */
	public String getPrioridade() {
		return prioridade;
	}



	/**
	 * @param prioridade the prioridade to set
	 */
	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	
}
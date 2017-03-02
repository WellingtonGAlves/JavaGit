/**
 * package helpdesk.vo
 */
package helpdesk.vo;

/**
 * Classe Id.
 * Utilizada para popular a pagina de pesquisarTicket (combobox) com o parametro Id
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class Id {


	/**
	 * Variáveis de instância
	 */

	private Integer id;
	private String idString;

	

	

	/**
	 * Função construtora
	 */

	public Id() {

	}

	

	/**
	 * @return the idString
	 */
	public String getIdString() {
		return idString;
	}

	/**
	 * @param idString the idString to set
	 */
	public void setIdString(String idString) {
		this.idString = idString;
	}



	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	
}
/**
 * package helpdesk.vo
 */
package helpdesk.vo;




/**
 * Classe Status.
 * Utilizada para popular a pagina de cadastro (combobox) com o parametro status
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class Status {
	
	private String status;
	
	/**
	 *metodo construtor da classe Status
	 * 
	 */
	public Status() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status.toUpperCase();
	}
}

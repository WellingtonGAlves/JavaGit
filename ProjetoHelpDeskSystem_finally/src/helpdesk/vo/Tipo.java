/**
 * package helpdesk.vo
 */
package helpdesk.vo;

/**
 * Classe Tipo.
 * Utilizada para popular a pagina de cadastro (combobox) com o parametro tipo
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class Tipo{


	/**
	 * Variáveis de instância
	 */

	private Integer idTipo;
	private String tipo;

	

	/*
	 * Função construtora
	 */

	public Tipo() {

	}



	/**
	 * @return the idTipo
	 */
	public Integer getIdTipo() {
		return idTipo;
	}



	/**
	 * @param idTipo the idTipo to set
	 */
	public void setIdTipo(Integer idTipo) {
		this.idTipo = idTipo;
	}



	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}



	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
}
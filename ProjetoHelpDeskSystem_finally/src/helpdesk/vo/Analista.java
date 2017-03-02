/**
 * package helpdesk.vo
 * 
 */
package helpdesk.vo;

/**
 * Classe Analista.
 * Utilizada para popular a pagina de cadastro (combobox) com o parametro analista
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class Analista {

	/**
	 * Variáveis de instância
	 */

	private Integer idAnalista;

	private String nome;
	
	private Integer nivelAnalista;
	

	

	/**
	 * Metodo construtor
	 */

	public Analista() {

	}




	/**
	 * @return the idAnalista
	 */
	public Integer getIdAnalista() {
		return idAnalista;
	}




	/**
	 * @param idAnalista the idAnalista to set
	 */
	public void setIdAnalista(Integer idAnalista) {
		this.idAnalista = idAnalista;
	}




	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}




	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}




	/**
	 * @return the nivelAnalista
	 */
	public Integer getNivelAnalista() {
		return nivelAnalista;
	}




	/**
	 * @param nivelAnalista the nivelAnalista to set
	 */
	public void setNivelAnalista(Integer nivelAnalista) {
		this.nivelAnalista = nivelAnalista;
	}


	
}
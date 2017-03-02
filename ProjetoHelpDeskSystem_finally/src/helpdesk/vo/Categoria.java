/**
 * package helpdesk.vo
 */

package helpdesk.vo;



/**
 * Classe Categoria.
 * Utilizada para popular a pagina de cadastro (combobox) com o parametro categoria
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */

public class Categoria{

	/**
	 * Variáveis de instância
	 */

	private Integer idCategoria;
	private String categoria;

	

	/**
	 * @return the idCategoria
	 */
	public Integer getIdCategoria() {
		return idCategoria;
	}



	/**
	 * @param idCategoria the idCategoria to set
	 */
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}



	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}



	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}



	/**
	 * Metodo construtor
	 */

	public Categoria() {

	}

	

	
}
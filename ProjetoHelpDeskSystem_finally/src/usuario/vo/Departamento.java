/**
	 * package usuario.vo
	 */
package usuario.vo;


public class Departamento {

	


	private Integer idDepartamento;

	private String departamento;

	

	/**
	 * Função construtora da classe Departamento
	 */

	public Departamento() {

	}



	/**
	 * @return the idDepartamento
	 */
	public Integer getIdDepartamento() {
		return idDepartamento;
	}



	/**
	 * @param idDepartamento the idDepartamento to set
	 */
	public void setIdDepartamento(Integer idDepartamento) {
		this.idDepartamento = idDepartamento;
	}



	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}



	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento.toUpperCase();
	}

}
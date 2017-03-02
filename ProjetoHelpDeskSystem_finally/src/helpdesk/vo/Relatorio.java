/**
 * package helpdesk.vo
 */
package helpdesk.vo;


/**
 * Imports necessarios
 */
import java.util.Date;



/**
 * Classe Relatorio.
 * Utilizada para gerar relatório
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class Relatorio {
	
	/**
	 * Variaveis de instancia
	 */
	private Integer idTicket;
	private Integer idRelatorio;
	private String tipo;
	private String categoria;
	private String assunto;
	private String descricao;
	private Date dataInicio;
	private Date dataFim;
	private String status;
	private String solucao;
	private Integer diasAberto;
	private Integer diasFechados;
	
	
	public Relatorio() {
		
	}
	
	
	
	/**
	 * gets and sets para gerar relatorio
	 */


	/**
	 * @return the idRelatorio
	 */
	public Integer getIdRelatorio() {
		return idRelatorio;
	}


	/**
	 * @param idRelatorio the idRelatorio to set
	 */
	public void setIdRelatorio(Integer idRelatorio) {
		this.idRelatorio = idRelatorio;
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
	 * @return the assunto
	 */
	public String getAssunto() {
		return assunto;
	}


	/**
	 * @param assunto the assunto to set
	 */
	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}


	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}


	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}


	/**
	 * @param dataInicio the dataInicio to set
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}


	/**
	 * @return the dataFim
	 */
	public Date getDataFim() {
		return dataFim;
	}


	/**
	 * @param dataFim the dataFim to set
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
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
		this.status = status;
	}


	/**
	 * @return the solucao
	 */
	public String getSolucao() {
		return solucao;
	}


	/**
	 * @param solucao the solucao to set
	 */
	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}



	/**
	 * @return the diasAberto
	 */
	public Integer getDiasAberto() {
		return diasAberto;
	}



	/**
	 * @param diasAberto the diasAberto to set
	 */
	public void setDiasAberto(Integer diasAberto) {
		this.diasAberto = diasAberto;
	}



	/**
	 * @return the idTicket
	 */
	public Integer getIdTicket() {
		return idTicket;
	}



	/**
	 * @param idTicket the idTicket to set
	 */
	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}



	/**
	 * @return the diasFechados
	 */
	public Integer getDiasFechados() {
		return diasFechados;
	}



	/**
	 * @param diasFechados the diasFechados to set
	 */
	public void setDiasFechados(Integer diasFechados) {
		this.diasFechados = diasFechados;
	}
	
	
	

}

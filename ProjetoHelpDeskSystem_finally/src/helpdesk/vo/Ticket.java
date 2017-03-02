/**
 * package helpdesk.vo
 */
package helpdesk.vo;
/**
 * imports necesarios
 */
import java.util.Date;
import usuario.vo.Usuario;

/**
 * Classe Ticket. Classe Principal do objeto ticket
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class Ticket {

	/**
	 * variaveis de instancia
	 */

	private Integer idTicket;

	private String assunto;
	private Usuario usuario;
	private String descricao;
	private String solucao;
	private Integer nivelAnalista;
	private Analista analista;
	private Date dataInicio;
	private Date dataFim;

	private String statusTicket;
	private String caption;
	private Prioridade prioridade;
	private Tipo tipo;
	private Categoria categoria;
	private Id id;

	public Ticket() {
		this.prioridade = new Prioridade();
		this.setCategoria(new Categoria());
		this.setTipo(new Tipo());
		this.analista = new Analista();
		this.usuario = new Usuario();
		this.id = new Id();

	}

	/**
	 * @return the idTicket
	 */
	public Integer getIdTicket() {
		return idTicket;
	}

	/**
	 * @param idTicket
	 *            the idTicket to set
	 */
	public void setIdTicket(Integer idTicket) {
		this.idTicket = idTicket;
	}

	/**
	 * @return the assunto
	 */
	public String getAssunto() {
		return assunto;
	}

	/**
	 * @param assunto
	 *            the assunto to set
	 */
	public void setAssunto(String assunto) {
		this.assunto = assunto.toUpperCase();
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao.toUpperCase();
	}

	/**
	 * @return the nivelAnalista
	 */
	public Integer getNivelAnalista() {
		return nivelAnalista;
	}

	/**
	 * @param nivelAnalista
	 *            the nivelAnalista to set
	 */
	public void setNivelAnalista(Integer nivelAnalista) {
		this.nivelAnalista = nivelAnalista;
	}

	/**
	 * @return the analista
	 */
	public Analista getAnalista() {
		return analista;
	}

	/**
	 * @param analista
	 *            the analista to set
	 */
	public void setAnalista(Analista analista) {
		this.analista = analista;
	}

	/**
	 * @return the statusTicket
	 */
	public String getStatusTicket() {
		return statusTicket;
	}

	/**
	 * @param statusTicket
	 *            the statusTicket to set
	 */
	public void setStatusTicket(String statusTicket) {
		this.statusTicket = statusTicket.toUpperCase();
	}

	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return the solucao
	 */
	public String getSolucao() {
		return solucao;
	}

	public void setSolucao(String solucao) {
		if (solucao == null) {
			solucao = "A PREENCHER";
		}
		this.solucao = solucao.toUpperCase();
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		String data = dataFim.toString();

		if (dataFim.equals(data)) {
			dataFim = null;
		}
		this.dataFim = dataFim;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption
	 *            the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the prioridade
	 */
	public Prioridade getPrioridade() {
		return prioridade;
	}

	/**
	 * @param prioridade
	 *            the prioridade to set
	 */
	public void setPrioridade(Prioridade prioridade) {
		this.prioridade = prioridade;
	}

	/**
	 * @return the tipo
	 */
	public Tipo getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the categoria
	 */
	public Categoria getCategoria() {
		return categoria;
	}

	/**
	 * @param categoria
	 *            the categoria to set
	 */
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	/**
	 * @return the id
	 */
	public Id getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Id id) {
		this.id = id;
	}

}

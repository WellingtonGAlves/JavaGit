/**
	 * package usuario.vo
	 */
package usuario.vo;


/**
 * Classe Usuario.
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class Usuario    {



	/**
	 * Variáveis de instância do Uauario
	 */

	private Integer idUsuario;
	private String nome;
	private Departamento departamento;
	private String sexo;
	private String email;
	private String isAnalista;
	private String senha;
	private String login;
	private String permissao;
	private Integer nivelAnalista;
	private Integer idAnalista;

	/**
	 * Metodo Construtor da classe Uusario
	 */

	public Usuario() {
		departamento = new Departamento();

	}

	/**
	 * Métodos de acesso get e set
	 *
	 * @return the idUsuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario
	 *            the idUsuario to set
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome
	 *            the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}

	/**
	 * @return the departamento
	 */
	public Departamento getDepartamento() {
		return departamento;
	}

	/**
	 * @param departamento
	 *            the departamento to set
	 */
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo.toUpperCase();
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email.toUpperCase();
	}

	/**
	 * @return the isAnalista
	 */
	public String getIsAnalista() {
		return isAnalista;
	}

	/**
	 * @param isAnalista the isAnalista to set
	 */
	public void setIsAnalista(String isAnalista) {
		this.isAnalista = isAnalista.toUpperCase();
	}

	/**
	 * @return the senha
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * @param senha the senha to set
	 */
	public void setSenha(String senha) {
		this.senha = senha.toUpperCase();
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login.toUpperCase();
	}

	/**
	 * @return the permissao
	 */
	public String getPermissao() {
		return permissao;
	}

	/**
	 * @param permissao the permissao to set
	 */
	public void setPermissao(String permissao) {
		this.permissao = permissao.toUpperCase();
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

	
}

/**
 * @return the dataNascimento
 */

/**
 * package login.vo
 */
package login.vo;

public class Login {
	private Integer idLogin;
	private String permissao;
	private String login;
	private String senha;
	
	/**
	 * Função construtora da classe Login
	 */
	public Login() {
		// TODO Auto-generated constructor stub
		
	}

	/**
	 * @return the idLogin
	 */
	public Integer getIdLogin() {
		return idLogin;
	}

	/**
	 * @param idLogin the idLogin to set
	 */
	public void setIdLogin(Integer idLogin) {
		this.idLogin = idLogin;
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
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
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
		this.senha = senha;
	}

	

}

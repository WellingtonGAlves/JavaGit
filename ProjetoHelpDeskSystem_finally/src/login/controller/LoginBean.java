/**
 * package login.controller
 */
package login.controller;

/**
 * Imports
 */
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import helpdesk.vo.Ticket;
import login.model.ManutencaoLogin;
import login.vo.Login;

import java.io.Serializable;
import java.util.List;

@Named("loginBean")
@SessionScoped
/**
 * Classe LoginBean.
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 978762456732460672L;

	private Login login;
	private static String navegando;
	private Ticket ticket;
	private List<Login> loginList;
	private ManutencaoLogin manutencaoLogin;
	private String caption;

	/**
	 * Metodo construtor
	 */
	public LoginBean() {
		// TODO Auto-generated constructor stub
		login = new Login();
		setTicket(new Ticket());
		manutencaoLogin = new ManutencaoLogin();

	}
	
	/**
	 * Metodo para validar Login
	 * @param login
	 * @param senha
	 * @return
	 */

	public String validarLogin(String login, String senha) {

		//login = "admin";
		//senha = "admin";
		FacesContext context = FacesContext.getCurrentInstance();
		//retorna uma lista de logins cadastrados
		this.loginList = manutencaoLogin.buscarLogins();
		if(this.loginList == null){
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Banco de dados Indisponivel", "banco indisponivel"));
			return "/paginas/login";
		}
		if (login.equals("") && senha.equals("")) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Login  Invalido", "login e senha estao nulos"));
			return "/paginas/login";
		}

		for (Login loginCadastrado : loginList) {

			if (loginCadastrado.getLogin().equals(login.toUpperCase())
					&& (loginCadastrado.getSenha().equals(senha.toUpperCase()))) {

				if (loginCadastrado.getPermissao().equals("ADMINISTRADOR")) {
					setNavegando("ADMINISTRADOR");
					return "/paginas/homepage";
				} else {
					if (loginCadastrado.getPermissao().equals("ANALISTA")) {
						setNavegando("ANALISTA");
						return "/paginas/homepageAnalista";
					}

				}
			}
		}

		caption = loginInvalido(login.toUpperCase(), senha.toUpperCase());

		return caption;

	}
	
	/**
	 * Metodo para infomar se usuario ou senha está incorreto
	 * @param login
	 * @param senha
	 * @return
	 */

	private String loginInvalido(String login, String senha) {
		// TODO Auto-generated method stub
		FacesContext context = FacesContext.getCurrentInstance();
		Integer tamanhoDaList = loginList.size();
		int cont = 0;

		for (Login loginCadastrado : loginList) {
			if (loginCadastrado.getLogin().equals(login.toUpperCase())
					&& !loginCadastrado.getSenha().equals(senha.toUpperCase())) {

				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "SENHA INVALIDA", "senha invalida"));
			}
		}
		for (Login loginCadastrado : loginList) {
			if (!loginCadastrado.getLogin().equals(login.toUpperCase())
					&& !loginCadastrado.getSenha().equals(senha.toUpperCase())) {

				cont = cont + 1;
			}
		}
		if (cont == tamanhoDaList) {// isso caracteriza que a lista toda foi
									// percorrida e não foi encontrado nenhum
									// login igual ao digitado
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Login invalido", "senha invalida"));
		}
		return "/paginas/login";
	}

	/**
	 * @return the login
	 */
	public Login getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(Login login) {
		this.login = login;
	}

	/**
	 * @return the navegando
	 */
	public static String getNavegando() {
		return navegando;
	}

	/**
	 * @param navegando the navegando to set
	 */
	public static void setNavegando(String navegando) {
		LoginBean.navegando = navegando;
	}

	/**
	 * @return the ticket
	 */
	public Ticket getTicket() {
		return ticket;
	}

	/**
	 * @param ticket the ticket to set
	 */
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	/**
	 * @return the loginList
	 */
	public List<Login> getLoginList() {
		return loginList;
	}

	/**
	 * @param loginList the loginList to set
	 */
	public void setLoginList(List<Login> loginList) {
		this.loginList = loginList;
	}

	/**
	 * @return the manutencaoLogin
	 */
	public ManutencaoLogin getManutencaoLogin() {
		return manutencaoLogin;
	}

	/**
	 * @param manutencaoLogin the manutencaoLogin to set
	 */
	public void setManutencaoLogin(ManutencaoLogin manutencaoLogin) {
		this.manutencaoLogin = manutencaoLogin;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}

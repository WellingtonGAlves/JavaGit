/**
 * package usuario.controller
 */
package usuario.controller;
/**
 * Imports necessarios
 */
import static login.controller.LoginBean.getNavegando;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;


import usuario.model.ManutencaoUsuario;
import usuario.vo.Departamento;
import usuario.vo.Usuario;

/**
 * Backing Bean para acessar as paginas xthml jsf.
 * 
 * @author Tiago/Wellington/Fernando
 * 
 * @since 03/05/2016
 * 
 * 
 * 
 */

@Named("usuarioBean")
@SessionScoped
public class usuarioBean implements Serializable {

	
	private static final long serialVersionUID = 2607989752698035588L;

	
	/**
	 * Variáveis de instância
	 */

	private Usuario usuario;
	private Usuario auxUsuario;
	private List<SelectItem> selectItemDepartamentos = null;
	
	
	private  String caption;

	private List<Usuario> usuarios;

	private ManutencaoUsuario manutencaoUsuario;

	/**
	 * Função construtora da classe usuarioBean
	 */

	public usuarioBean() {

		usuario = new Usuario();
		
		//login = new Login();

		manutencaoUsuario = new ManutencaoUsuario();
	}

	/**
	 * Métodos de acesso para cadastrar usuario
	 */

	
	

	

	/**
	 * Operações da classe Usuario
	 */

	
	/**
	 * Metodo responsavel para salvar e atualizar um Usuario
	 * @return  para paginas onde um usuario é cadastrados ou atualizado
	 */
	public String salvar() {

		// Declaração de variáveis

		FacesContext context = FacesContext.getCurrentInstance();

		// o usuario pode estar nulo se a conexão com o banco falho e a pessoa ainda quer tentar gravar,tratamos o erro estanciando um novo usuario e o retornando para o cadastro de usuario
		if(this.usuario == null){
			this.usuario = new Usuario();
			this.usuarios = null;
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível salvar os dados.", "Preencha todos os campos"));
			//retirna para a pagina inicial
			novo();
		}
		//confirmamos se os campos estão corretamente prrenchidos e proceguimos,caso não esteja retornaremos para a Pagina de confirmação dos dados
		if ((this.usuario.getIdUsuario() == null)&&(
				(this.usuario.getNome().equals(""))||
				(this.usuario.getDepartamento()== null)||
				(this.usuario.getDepartamento().getIdDepartamento().equals(0))||
				(this.usuario.getSexo().equals(""))||
				(this.usuario.getEmail().equals(""))||
				(this.usuario.getIsAnalista().equals(""))||
				(this.usuario.getNivelAnalista()==null)||
				(this.usuario.getSenha().equals(""))||
				(this.usuario.getLogin()== null)||
				(this.usuario.getPermissao().equals("")))) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível salvar os dados. ", ""));
			
			return "/paginas/usuario/cadastro/confirmarUsuario";
		}
		
		// se o usuario passou por todas as verificações...ele entra nesse metodo para salvar, sendo passivel de um erro(conexão com o banco de dados estar inacessível)
		if (this.usuario.getIdUsuario() == null) {
			/* criamos um Objeto do tipo usuario para quando estourar um erro,
			 *  pq quando isto ocorre o usuario retorna nulo usamos o auxUsuario para preencher este usuario novamente 
			 *  para mostrar a pessoa os dados que ela tentou salvar
			 */
			auxUsuario = new Usuario();
			auxUsuario = this.usuario;
			
			this.usuario = manutencaoUsuario.salvar(this.usuario);
			
			
			
			if(this.usuario == null){
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Banco de dados indisponivel", ""));
				this.usuario = new Usuario();
				this.usuario = auxUsuario;
				return "/paginas/usuario/cadastro/cadastrarUsuario";
			}
			//this.caption = "Dados Cadastros com Sucesso";
		} else {
			if((this.usuario.getIdUsuario()!= null)&&
					(this.usuario.getNome().equals("")||
					this.usuario.getDepartamento()==null||
					this.usuario.getIsAnalista().equals("")||
					this.usuario.getSexo().equals("")||
					this.usuario.getEmail().equals("")||
					this.usuario.getLogin().equals("")||
					this.usuario.getSenha().equals(""))){
				
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível salvar os dados."+"\nNenhum Campo deve estar VAZIO*", ""));
				this.usuario = manutencaoUsuario.pesquisar(this.usuario.getIdUsuario());
				return "/paginas/usuario/cadastro/alterarUsuario";
			}
			this.auxUsuario = this.usuario;
			this.usuario = manutencaoUsuario.atualizar(this.usuario);

			this.caption = "Usuario Atualizado com Sucesso";
		}

		if (this.usuario != null) {

			this.usuarios = null;

			return "/paginas/usuario/cadastro/mostrarUsuario";

		} else {

			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível salvar os dados.", ""));
			if(auxUsuario.getIdUsuario()!=null){
				this.usuario = new Usuario();
				this.usuario = this.auxUsuario;
				return "/paginas/usuario/confirmarAtualizacaoUsuario";
			}else{
				return "/paginas/usuario/confirmarUsuario";
			}
		}
	}
	/**
	 * Metodo responsavel por buscar buscar no banco  os usuario cadastrados
	 * 
	 * @return  paginas de consulta
	 */
	public String consultar() {

		// Processamento dos dados

		this.usuarios = manutencaoUsuario.consultar();

		// Saída da informação
		if(this.usuarios==null){
			this.caption = "Falha no sistema contate o ADMINISTRADOR!";
		}
		if(getNavegando().equals("ANALISTA")){
			return "/paginas/usuario/consulta/consultarUsuarioPorAnalista";
		}
		return "/paginas/usuario/consulta/consultarUsuario";
	}

	/**
	 * Metodo responsavel por excluir um usuario
	 * passando o usuario
	 * 
	 * @return  pagina de consulta
	 */
	public String excluir() {

		FacesContext context = FacesContext.getCurrentInstance();

		Boolean flag = null;

		// Processamento dos dados

		flag = manutencaoUsuario.excluir(this.usuario);

		if (flag.equals(true)) {

			this.usuarios = manutencaoUsuario.consultar();

			return null;

		} else {

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Não foi possível excluir os dados do usuario ID # " + usuario.getIdUsuario(), ""));

			return null;
		}
	}

	
	/**
	 * Metodo responsavel por  buscar no banco  os usuario cadastrados
	 * passando como paramentro o nome dele
	 * 
	 * @return  paginas de consulta 
	 */
	public String pesquisar(String nome) {

		// Processamento dos dados

		this.usuarios = manutencaoUsuario.pesquisar(nome);
		
		if(this.usuarios==null){
			getSelectItemDepartamentos();
			this.caption = "usuario invalido ";
		}else{
			this.caption = null;
		}
		if(getNavegando().equals("ANALISTA")){
			return "/paginas/usuario/consulta/consultarUsuarioPorAnalista";
		}
		return "/paginas/usuario/consulta/consultarUsuario";
	}
	/**
	 * Metodo responsavel por reiniciar o programa instanciando
	 * todos os objetos necessario para este executar novamente
	 * e
	 * tambem verificando quem esta logado
	 * se é um Analista ou Administrador 
	 * 
	 * @return  pagina Homepage se for administrador e HomepageAnalista se for um Analista 
	 */
	public String novo() {

		if(getNavegando().equals("ANALISTA")){
			getSelectItemDepartamentos();
			this.usuario = new Usuario();
			this.usuarios = null;
			
			return "/paginas/homepageAnalista";
		}
		getSelectItemDepartamentos();
		this.usuario = new Usuario();
		this.usuarios = null;

		return "/paginas/homepage";
	}
	/**
	 * metodos resposnsavel por preencher a comboBox do Departamento das paginas que possuem comboBox
	 * de departamentos
	 * @return listas de opcões a selecionar de departamentos
	 */
	public List<SelectItem> getSelectItemDepartamentos() {

		// Declaração de variáveis
		FacesContext context = FacesContext.getCurrentInstance();
		List<Departamento> listaDepartamentos = null;

		Iterator<Departamento> iteratorLista = null;

		Departamento departamento = null;

		// Processamento dos dados

		listaDepartamentos = manutencaoUsuario.buscarDepartamentos();
		if(listaDepartamentos == null){
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha no sistema contate o ADMINISTRADOR!",
					"banco de dados indisponivel"));

		}
		if (listaDepartamentos != null && listaDepartamentos.size() > 0) {

			iteratorLista = listaDepartamentos.iterator();

			this.selectItemDepartamentos = new ArrayList<>();

			while (iteratorLista.hasNext()) {

				departamento = iteratorLista.next();

				if (departamento.getIdDepartamento() != null && !departamento.getIdDepartamento().equals("")) {

					this.selectItemDepartamentos.add(
						new SelectItem(departamento.getIdDepartamento(),departamento.getDepartamento()));
				}
			}
		}

		// Saída da informação

		return this.selectItemDepartamentos;
	}

	
	/**
	 * Metodo responsavel por validar os campos preenchidos
	 * e direcionar para a pagina ConfirmarCadastro se esta tudo certo
	 * e se não
	 * retorna para as paginas de cadastro
	 * 
	 * @return  paginas de cadastro de usuario e de confirmação de usuario
	 */
	public String confirmarCadastro(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		// abaixo verificamos se a senha e o login condizem para se cadastrar o usuario e os demais campos se não estão nulos
		if((this.usuario.getSenha().equals("NÃO POSSUE")||this.usuario.getLogin().equals("NÃO POSSUE"))&&(
				this.usuario.getNome().equals("")||
				this.usuario.getDepartamento().getDepartamento()==null||
				this.usuario.getDepartamento().getIdDepartamento().equals(0)||
				this.usuario.getPermissao().equals("")||
				this.usuario.getSexo().equals("")||
				this.usuario.getEmail().equals("")||
				this.usuario.getNivelAnalista().equals(""))){
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Preencha todos os Campos corretamente", "Preencha todos os campos"));
			return "/paginas/usuario/cadastro/cadastrarSemSenha";
		}
		//valida o usuario para cadastrar um analista ou admin verificando se a senha e login nao estão nulos e os demais campos
				if(this.usuario.getSenha().equals("")||
					this.usuario.getLogin().equals("")||
					this.usuario.getNome().equals("")||
					this.usuario.getDepartamento().getIdDepartamento().equals(0)||
					this.usuario.getPermissao().equals("")||
					this.usuario.getSexo().equals("")||
					this.usuario.getEmail().equals("")||
					this.usuario.getNivelAnalista().equals("")){
					context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Preencha todos os Campos corretamente", "Preencha todos os campos"));
					return "/paginas/usuario/cadastro/cadastrarSenha";
				}
		//valida se o departamento não está nulo, para preenchelo com o departamento selecionado na comboBox, se estiver...estancia um novo objeto Departamento()
				if(this.usuario.getDepartamento().getIdDepartamento()!= null || !this.usuario.getDepartamento().getIdDepartamento().equals("")){
					this.usuario.setDepartamento(manutencaoUsuario.pesquisarIdDepartamento(this.usuario.getDepartamento().getIdDepartamento()));
				}else{
					this.usuario.setDepartamento(new Departamento());
				}
		
		return "/paginas/usuario/cadastro/confirmarUsuario";
	}
	
	/**
	 * Metodo responsavel por validar os campos preenchidos
	 * e direcionar para a pagina confirmarAtualização se estiver tudo certo
	 * e se não
	 * para as paginas de alteração de usuario
	 * 
	 * @return  paginas de atualizar usuario e de confirmação de atualizaçaõ do  usuario
	 */
	public String confirmarAtualizacao(){
		
		
		
		FacesContext context = FacesContext.getCurrentInstance();
		if(this.usuario.getPermissao().equals("NÃO POSSUE")&&(
				this.usuario.getEmail().equals("")||
				this.usuario.getDepartamento().getDepartamento()== null)){
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Preencha Todos os Campos", "Capos não preenchidos"));
			return "paginas/usuario/usuario/atualizarUsuario";
		}
		
		if(!this.usuario.getPermissao().equals("NÃO POSSUE")&&(
				this.usuario.getEmail().equals("")||
				this.usuario.getNivelAnalista()== null||
				this.usuario.getLogin().equals("")||
				this.usuario.getSenha().equals("")||
				this.usuario.getDepartamento().getDepartamento()==null)){
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Preencha Todos os Campos", "Capos não preenchidos"));
			return "paginas/usuario/usuario/atualizarSemSenha";
		}
		
		if(this.usuario.getDepartamento().getIdDepartamento()!= null && !this.usuario.getDepartamento().getIdDepartamento().equals(0)){
			this.usuario.setDepartamento(manutencaoUsuario.pesquisarIdDepartamento(this.usuario.getDepartamento().getIdDepartamento()));
		}else{
			this.usuario.setDepartamento(manutencaoUsuario.pesquisarDepartamento(this.usuario.getDepartamento().getDepartamento()));
		}
		
		return "/paginas/usuario/cadastro/confirmarAtualizacaoUsuario";
	}
	
	/**
	 * Metodo responsavel por validar os campos preenchidos no cadastro
	 * e direcionar para as paginas de cadastro 
	 * e se não forem validos retornar 
	 * para a pagina de preencher o cadastro do usuario
	 * 
	 * @return  paginas de cadastro do usuario 
	 */
	public String continuarCadastro(){
		
		FacesContext context = FacesContext.getCurrentInstance();
		//preenche o campo departamento apartir do comboBox selecionado
		if(this.usuario.getDepartamento().getIdDepartamento()!= null && !this.usuario.getDepartamento().getIdDepartamento().equals(0)){
			this.usuario.setDepartamento(manutencaoUsuario.pesquisarIdDepartamento(this.usuario.getDepartamento().getIdDepartamento()));
		}
		
		//valida nome,email , sexo ,permissao e departamento se estão nulos
		if(this.usuario.getNome().equals("")||
				this.usuario.getDepartamento().getIdDepartamento().equals(0)||
				this.usuario.getEmail().equals("")||
				this.usuario.getSexo().equals("")||
				this.usuario.getPermissao().equals("")){
			
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Preencha Todos os Campos", "Capos não preenchidos"));
			return "/paginas/usuario/cadastro/cadastrarUsuario";
			
		}if(this.usuario.getPermissao().equals("NÃO POSSUE")){// se for usuario
			this.usuario.setLogin("NÃO POSSUE");
			this.usuario.setIsAnalista("NÃO");
			this.usuario.setSenha("NÃO POSSUE");
			this.usuario.setNivelAnalista(0);
			return "/paginas/usuario/cadastro/cadastrarSemSenha";
		}else{
			this.usuario.setNivelAnalista(2);
			this.usuario.setIsAnalista("SIM");
			this.usuario.setSenha("1234");
			this.usuario.setLogin("");
			return "/paginas/usuario/cadastro/cadastrarSenha";
		}
		}
	
	/**
	 * Metodo responsavel por validar os campos preenchidos
	 * e direcionar para as paginas de atualização do usuario e confirmação dos dados
	 * e se não forem validos
	 * para a pagina de atualizar usuario novamente
	 * 
	 * @return  paginas de atualização do usuario(atualizarComPermissao),(confirmarAtualizacaoUsuario),(atualizarComSenha)
	 */
	public String continuarAtualizacao(){
		FacesContext context = FacesContext.getCurrentInstance();
		
		if(this.usuario.getDepartamento().getDepartamento()==null||
		this.usuario.getEmail().equals("")||
		this.usuario.getPermissao().equals("")){
			
			context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Preencha Todos os Campos", "Capos não preenchidos"));
			return "/paginas/usuario/cadastro/atualizarComPermissao";
		}
		
		if(this.usuario.getPermissao().equals("NÃO POSSUE")){
			this.usuario.setIsAnalista("NÃO");
			this.usuario.setSenha("NÃO POSSUE");
			this.usuario.setLogin("NÃO POSSUE");
			return "/paginas/usuario/cadastro/confirmarAtualizacaoUsuario";
		}
		this.usuario.setIsAnalista("SIM");
		if(this.usuario.getSenha().equals("NAO POSSUE")){
			this.usuario.setSenha("1234");
		}
		if(this.usuario.getLogin().equals("NAO POSSUE")){
			this.usuario.setLogin("");
		}
		if(this.usuario.getNivelAnalista().equals(1)){
			this.usuario.setNivelAnalista(1);
		}else{
			if(this.usuario.getNivelAnalista().equals(2)){
				this.usuario.setNivelAnalista(2);
			}else{
				this.usuario.setNivelAnalista(1);
			}
		}
		return "/paginas/usuario/cadastro/atualizarComSenha";
	}
	
	/**
	 * Metodo responsavel por preencher os campos -  senha ,nivel do analista
	 * para a pagina de cadastrar usuario ja estar preenchida com estes campos 
	 * 
	 * @return  pagina cadastrarUsuario
	 */
	public String alterarPermissaoCadastro(){
		this.usuario.setSenha("1234");
		this.usuario.setIsAnalista("SIM");
		return "/paginas/usuario/cadastro/cadastrarUsuario";
	}
	/**
	 * Metodo responsavel por preencher os campos -  senha ,nivel do analista
	 * para a pagina de atualizar usuario ja estar preenchida com estes campos 
	 * 
	 * @return  pagina atualizarUsuario
	 */
	public String alterarPermissaoAtualizacao(){
		this.usuario.setSenha("1234");
		this.usuario.setIsAnalista("SIM");
		
		return "/paginas/usuario/cadastro/atualizarUsuario";
	}
	/**
	 * Metodo usado para levar quem esta navegadando a pagina de pesquisar
	 * usuario, e nele é feito um get que retorna para a pagina se o banco está indisponivel
	 * 
	 * @return pagina pesquisarUsuario
	 */
	public String pesquisarUsuario(){
			//o get é somente para mostrar se o banco de dados está indisponivel
			getSelectItemDepartamentos();
		return"/paginas/usuario/pesquisa/pesquisarUsuario";
	}
	/**
	 * Metodo responsavel verificar se a pesso é um um usuario e 
	 * direcionar para a pagina de alterar usuario
	 * e se for analista ou administrador sera direcionado para a pagina de atualização de analistas e administradores
	 * 
	 * 
	 * @return  pagina atualizarUsuario(atualiza usuario comum),atualizarComPermissao(atualiza administradores e analistas)
	 */
	public String editar(){
		this.usuario = manutencaoUsuario.pesquisar(this.usuario.getIdUsuario());
		if(this.usuario.getPermissao().equals("NAO POSSUE")||this.usuario.getPermissao().equals("NÃO POSSUE")){
			this.usuario.setPermissao("NÃO POSSUE");
			return"/paginas/usuario/cadastro/atualizarUsuario";
		}
		
		return"/paginas/usuario/cadastro/atualizarComPermissao";
		
		
	}
	/**
	 * Metodo responsavel por preencher os campos
	 * sexo , permissao da pagina cadastrarUsuario
	 * e retornar para a mesma
	 * 
	 * @return  pagina cadastrarUsuario
	 */
	public String cadastro(){
		
		this.usuario.setSexo("MASCULINO");
		this.usuario.setPermissao("NÃO POSSUE");
		return"/paginas/usuario/cadastro/cadastrarUsuario";
	}
	/**
	 * Metodo responsavel por atualizar as combobox da pagina
	 * atualizarUsuario
	 * 
	 * @return  pagina cadastrarUsuario
	 */
	public String voltar(){
		
		
		return "/paginas/usuario/cadastro/atualizarUsuario";
	}
	
	/**
	 * get de AuxUsuario
	 * @return AuxUsuario
	 */
	public Usuario getAuxUsuario() {
		return auxUsuario;
	}
	/**
	 * set de AuxUsuario
	 * @return AuxUsuario
	 */
	public void setAuxUsuario(Usuario auxUsuario) {
		this.auxUsuario = auxUsuario;
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
	public  void setCaption(String caption) {
		this.caption = caption;
	}
	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the usuario
	 */
	public List<Usuario> getUsuarioList() {

		return this.usuarios;
	}
}

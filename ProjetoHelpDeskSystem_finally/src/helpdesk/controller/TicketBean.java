/**
 * package helpdesk.controller;
 */
package helpdesk.controller;

/**
 * Imports necessarios
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;

import static login.controller.LoginBean.getNavegando;

import helpdesk.model.ManutencaoTicket;
import helpdesk.vo.Analista;
import helpdesk.vo.Categoria;
import helpdesk.vo.Id;
import helpdesk.vo.Prioridade;
import helpdesk.vo.Relatorio;
import helpdesk.vo.Status;
import helpdesk.vo.Ticket;
import helpdesk.vo.Tipo;

import usuario.model.ManutencaoUsuario;
import usuario.vo.Usuario;

import static helpdesk.util.Utilitaria.validaNumero;

@Named("ticketBean")
@SessionScoped
/**
 * Classe TicketBean.
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */
public class TicketBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2127680731465113672L;

	/**
	 * Variaveis de instancia Ticket
	 *
	 * @param ...
	 * @return .....
	 */

	private Ticket ticket;
	private Ticket auxTicket;
	private Status status;
	private List<Relatorio> relatorioDeDias;
	private List<Relatorio> relatorioListFechado;
	private List<Relatorio> relatorioListAberto;
	private List<Status> statusList;
	private Usuario usuario;
	private List<Usuario> usuarioList;
	private String caption;
	private List<Ticket> ticketList;
	private List<SelectItem> selectItemPrioridades;
	private List<SelectItem> selectItemCategorias;
	private List<SelectItem> selectItemAnalistas;
	private List<SelectItem> selectItemUsuarios;
	private List<SelectItem> selectItemTipos;
	private List<SelectItem> selectItemStatus;
	private List<SelectItem> selectItemIds;
	private ManutencaoTicket manutencaoTicket;
	private ManutencaoUsuario manutencaoUsuario;

	/**
	 * Metodo Construtor da classe TicketBean que instancia os objetos 'ticket',
	 * 'usuario', 'manutencaoTicket' , 'manutencaoUsuario' e 'Status'
	 *
	 * @param ...
	 * @return ..
	 */

	public TicketBean() {

		ticket = new Ticket();
		usuario = new Usuario();
		setStatus(new Status());
		manutencaoTicket = new ManutencaoTicket();
		manutencaoUsuario = new ManutencaoUsuario();
	}

	

	/**
	 * Metodo utilizado para Cadastrar/Atualizar o objeto ticket chamando a
	 * classe ManutencaoTicket e passando o objeto para a PersistenciaTicket e
	 * consequentemente para a TicketDao para gravar na Base de Dados
	 *
	 * @param Ticket
	 * @return Ticket
	 */

	public String salvar() {

		FacesContext context = FacesContext.getCurrentInstance();
		// para quando o der erro na gravação dos dados e o usuario insistir em
		// gravar nulo, com este (IF) inpedimos de acontecer um NULLPOINTER
		if (this.ticket == null) {

			this.ticket = new Ticket();
			this.ticketList = null;

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível salvar os dados.Banco Indisponivel ",
					"Preencha todos os campos"));

			return "/paginas/ticket/cadastro/cadastrarTicket";
		}
		if ((this.ticket.getIdTicket() == null)
				&& ((this.ticket.getUsuario() == null) || (this.ticket.getTipo() == null)
						|| (this.ticket.getCategoria() == null) || (this.ticket.getAssunto().equals(""))
						|| (this.ticket.getDescricao().equals("")) || (this.ticket.getNivelAnalista() == null)
						|| (this.ticket.getAnalista() == null) || (this.ticket.getPrioridade() == null))) {
			if (this.ticket.getUsuario() == null) {
				this.ticket.setUsuario(new Usuario());
			}
			if (this.ticket.getTipo() == null) {
				this.ticket.setTipo(new Tipo());
			}
			if (this.ticket.getCategoria() == null) {
				this.ticket.setCategoria(new Categoria());
			}
			if (this.ticket.getAnalista() == null) {
				this.ticket.setAnalista(new Analista());

			}
			if (this.ticket.getPrioridade() == null) {
				this.ticket.setPrioridade(new Prioridade());
			}
			
				
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Não foi possível salvar os dados.Preencha todos os campos", ""));
			
			return "/paginas/ticket/cadastro/cadastrarTicket";
		}

		if ((this.ticket.getIdTicket() == null)) {
			this.auxTicket = new Ticket();
			this.auxTicket = ticket;
			this.ticket = manutencaoTicket.salvarTicket(this.ticket);
			if (this.ticket != null) {
				this.caption = "Dados Cadastros com Sucesso";
			}else{
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível salvar os dados.Banco Indisponivel", ""));
				this.ticket = this.auxTicket;
				return null;
			}
		} else {
			if ((this.ticket.getIdTicket() != null) && (this.ticket.getAnalista() == null
					|| this.ticket.getStatusTicket().equals("") || this.ticket.getDescricao().equals("")
					|| this.ticket.getNivelAnalista().equals("") || this.ticket.getSolucao() == null)) {

				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Não foi possível atualizar os dados." + "\nNenhum Campo deve estar VAZIO*", ""));
				this.ticket = manutencaoTicket.pesquisarTicket(this.ticket.getIdTicket());
				return "/paginas/ticket/cadastro/alterarTicket";
			}
			this.auxTicket = new Ticket();
			this.auxTicket = this.ticket;
			this.ticket = manutencaoTicket.atualizarTicket(this.ticket);
			if (this.ticket != null) {
				this.caption = "Dados Atualizados com Sucesso";
			}
		}

		if (this.ticket != null) {

			this.ticketList = null;
			
			return "/paginas/ticket/cadastro/mostrarCadastroTicket";

		} else {

			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível salvar os dados.Banco Indisponivel", ""));
			this.ticket = this.auxTicket;
			return null;
		}
	}

	/**
	 * Metodo utilizado para Consultar o objeto ticket que retorna da Base de
	 * dados.
	 *
	 * @param ...
	 * @return Ticket
	 */

	public String consultar() {

		// Processamento dos dados

		this.ticketList = manutencaoTicket.consultarTicket();

		// Saída da informação
		if (this.ticketList == null) {
			getSelectItemTipos();
		}
		if (getNavegando().equals("ANALISTA")) {
			return "/paginas/ticket/consulta/consultaPorAnalista";
		}
		return "/paginas/ticket/consulta/consultarTicket";
	}

	/**
	 * Metodo utilizado para exluir o objeto ticket da Base de dados.
	 *
	 * @param idTicket
	 * @return boolean
	 */

	public String excluir() {

		FacesContext context = FacesContext.getCurrentInstance();

		Boolean flag = null;

		// Processamento dos dados

		flag = manutencaoTicket.excluirTicket(this.ticket);

		if (flag.equals(true)) {

			this.ticketList = manutencaoTicket.consultarTicket();

			return null;

		} else {

			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Não foi possível excluir os dados da pessoa Código # " + ticket.getIdTicket(), ""));

			return null;
		}
	}

	/**
	 * Metodo utilizado para Pesquisar o objeto ticket que retorna da Base de
	 * dados.
	 *
	 * @param usuario
	 * @return Ticket
	 */

	public String pesquisar(String usuario) {
		
		// Processamento dos dados

		this.ticketList = manutencaoTicket.pesquisarUsuarioTicket(usuario.toString().toUpperCase());

		if (this.ticketList == null) {
			this.caption = "usuario (" + usuario.toString().toUpperCase() + ") não cadastrado ";
		} else {
			this.caption = null;
		}
		if (getNavegando().equals("ANALISTA")) {
			return "/paginas/ticket/consulta/consultaPorAnalista";
		}
		return "/paginas/ticket/consulta/consultarTicket";
	}

	/**
	 * Metodo utilizado para Pesquisar o objeto ticket que retorna da Base de
	 * dados.
	 *
	 * @param analita
	 * @return Ticket
	 */

	public String pesquisarAnalista(String analista) {

		// Processamento dos dados

		this.ticketList = manutencaoTicket.pesquisarAnalistaTicket(analista.toString());

		if (this.ticketList == null) {
			this.caption = "Analista (" + analista.toString().toUpperCase() + ") não cadastrado";
		} else {
			this.caption = null;
		}
		if (getNavegando().equals("ANALISTA")) {
			return "/paginas/ticket/consulta/consultaPorAnalista";
		}
		return "/paginas/ticket/consulta/consultarTicket";
	}

	/**
	 * Metodo utilizado para Pesquisar o objeto ticket que retorna da Base de
	 * dados.
	 *
	 * @param idTicket
	 * @return Ticket
	 */

	public String pesquisarIdTicket(Integer idTicket) {

		// Processamento dos dados

		this.ticket = manutencaoTicket.pesquisarIdTicket(idTicket);
		if (this.ticket != null) {
			this.ticketList = new ArrayList<>();
			this.ticketList.add(this.ticket);
		}
		if (this.ticket == null) {
			this.caption = "Id invalido ";
			this.ticketList = null;
			this.ticket = new Ticket();
		} else {
			this.caption = null;
		}
		if (getNavegando().equals("ANALISTA")) {
			return "/paginas/ticket/consulta/consultaPorAnalista";
		}
		return "/paginas/ticket/consulta/consultarTicket";
	}

	/**
	 * Metodo utilizado para resetar o objeto ticket em caso de erro ou
	 * navegação para a pagina homepage.
	 *
	 * @param usuario
	 * @return Ticket
	 */

	public String novo() {

		if (getNavegando().equals("ANALISTA")) {
			getSelectItemTipos();
			this.ticket = new Ticket();
			this.auxTicket = new Ticket();
			
			this.ticketList = null;
			this.selectItemAnalistas = null;
			this.relatorioListAberto = null;
			this.relatorioListFechado = null;
			this.relatorioDeDias = null;

			return "/paginas/homepageAnalista";
		}
		getSelectItemTipos();
		this.ticket = new Ticket();
		this.auxTicket = new Ticket();
		this.relatorioListFechado = null;
		this.ticketList = null;
		this.selectItemAnalistas = null;
		this.setRelatorioListAberto(null);
		this.relatorioDeDias = null;

		return "/paginas/homepage";
	}

	/**
	 * Metodo utilizado para resetar o objeto ticket em caso de erro ou
	 * navegação para a pagina cadastrarTicket.
	 *
	 * @param usuario
	 * @return Ticket
	 */
	public String voltarCadastro() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (this.ticket == null) {

			this.ticket = new Ticket();

			this.ticketList = null;
		}
		if(this.ticket.getAnalista()==null &&
				this.ticket.getCategoria()==null&&
				this.ticket.getPrioridade()==null &&
				this.ticket.getTipo()==null){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Falha no sistema contate o ADMINISTRADOR!", "banco de dados indisponivel"));
			
				this.ticket.setUsuario(new Usuario());
				this.ticket.setTipo(new Tipo());
				this.ticket.setCategoria(new Categoria());
				this.ticket.setAnalista(new Analista());
				this.ticket.setPrioridade(new Prioridade());
			
			
		}

		return "/paginas/ticket/cadastro/cadastrarTicket";
	}

	/**
	 * Metodo utilizado para buscar na base de dados o campo prioridade.
	 *
	 * @param
	 * @return listaPrioridades
	 */
	public List<SelectItem> getSelectItemPrioridades() {

		// Declaração de variáveis

		List<Prioridade> listaPrioridades = null;

		Iterator<Prioridade> iteratorLista = null;

		Prioridade prioridade = null;

		// Processamento dos dados

		listaPrioridades = manutencaoTicket.buscarPrioridades();
		
		if (listaPrioridades != null && listaPrioridades.size() > 0) {

			iteratorLista = listaPrioridades.iterator();

			this.selectItemPrioridades = new ArrayList<>();

			while (iteratorLista.hasNext()) {

				prioridade = iteratorLista.next();

				if (prioridade.getIdPrioridade() != null && !prioridade.getIdPrioridade().equals("")) {

					this.selectItemPrioridades
							.add(new SelectItem(prioridade.getIdPrioridade(), prioridade.getPrioridade()));
				}
			}
		}

		// Saída da informação

		return this.selectItemPrioridades;
	}

	/**
	 * Metodo utilizado para buscar na base de dados o campo categoria.
	 *
	 * @param
	 * @return listaCategorias
	 */

	public List<SelectItem> getSelectItemCategorias() {

		// Declaração de variáveis

		List<Categoria> listaCategorias = null;

		Iterator<Categoria> iteratorLista = null;

		Categoria categoria = null;

		// Processamento dos dados

		listaCategorias = manutencaoTicket.buscarCategorias();

		if (listaCategorias != null && listaCategorias.size() > 0) {

			iteratorLista = listaCategorias.iterator();

			this.selectItemCategorias = new ArrayList<>();

			while (iteratorLista.hasNext()) {

				categoria = iteratorLista.next();

				if (categoria.getIdCategoria() != null && !categoria.getIdCategoria().equals("")) {

					this.selectItemCategorias.add(new SelectItem(categoria.getIdCategoria(), categoria.getCategoria()));
				}
			}
		}

		// Saída da informação

		return this.selectItemCategorias;
	}

	/**
	 * Metodo utilizado para buscar na base de dados o campo analista.
	 *
	 * @param
	 * @return listaAnalistas
	 */

	public List<SelectItem> getSelectItemAnalistas() {

		// Declaração de variáveis

		List<Analista> listaAnalistas = null;

		Iterator<Analista> iteratorLista = null;

		Analista analista = null;
		
		
		selectItemAnalistas = null;
		
		if(this.ticket.getNivelAnalista()!=null){
			listaAnalistas = manutencaoTicket.buscarAnalistas(ticket.getNivelAnalista());
		}
		if (listaAnalistas != null && listaAnalistas.size() > 0) {

			iteratorLista = listaAnalistas.iterator();

			this.selectItemAnalistas = new ArrayList<>();

			while (iteratorLista.hasNext()) {

				analista = iteratorLista.next();

				if (analista.getIdAnalista() != null && !analista.getIdAnalista().equals("")) {

					this.selectItemAnalistas.add(new SelectItem(analista.getIdAnalista(), analista.getNome()));
				}
			}
		}

		// Saída da informação

		return this.selectItemAnalistas;
	}

	/**
	 * Metodo utilizado para buscar na base de dados o campo usuario.
	 *
	 * @param
	 * @return listaUsuarios
	 */

	public List<SelectItem> getSelectItemUsuarios() {

		// Declaração de variáveis

		List<Usuario> listaUsuarios = null;

		Iterator<Usuario> iteratorLista = null;

		Usuario usuario = null;

		// Processamento dos dados

		listaUsuarios = this.usuarioList;

		if (listaUsuarios != null && listaUsuarios.size() > 0) {

			iteratorLista = listaUsuarios.iterator();

			this.selectItemUsuarios = new ArrayList<>();

			while (iteratorLista.hasNext()) {

				usuario = iteratorLista.next();

				if (usuario.getIdUsuario() != null && !usuario.getIdUsuario().equals("")) {

					this.selectItemUsuarios.add(new SelectItem(usuario.getIdUsuario(), usuario.getNome()));
				}
			}
		}

		// Saída da informação

		return this.selectItemUsuarios;
	}

	/**
	 * Metodo utilizado para buscar na base de dados o campo tipo.
	 *
	 * @param
	 * @return listaTipos
	 */
	public List<SelectItem> getSelectItemTipos() {

		// Declaração de variáveis
		FacesContext context = FacesContext.getCurrentInstance();
		
		List<Tipo> listaTipos = null;

		Iterator<Tipo> iteratorLista = null;

		Tipo tipo = null;

		// Processamento dos dados

		listaTipos = manutencaoTicket.buscarTipos();
		if(listaTipos ==null){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha no sistema contate o ADMINISTRADOR!",
					"Banco indisponivel"));
			}

		if (listaTipos != null && listaTipos.size() > 0) {

			iteratorLista = listaTipos.iterator();

			this.selectItemTipos = new ArrayList<>();

			while (iteratorLista.hasNext()) {

				tipo = iteratorLista.next();

				if (tipo.getIdTipo() != null && !tipo.getIdTipo().equals("")) {

					this.selectItemTipos.add(new SelectItem(tipo.getIdTipo(), tipo.getTipo()));
				}
			}
		}

		// Saída da informação

		return this.selectItemTipos;
	}

	/**
	 * Metodo utilizado para buscar na base de dados o campo tipo.
	 *
	 * @param
	 * @return listaStatus
	 */
	public List<SelectItem> getSelectItemStatus() {

		// Declaração de variáveis

		List<Status> listaStatus = null;

		Iterator<Status> iteratorLista = null;

		Status status = null;
		Status aberto = new Status();
		aberto.setStatus("ABERTO");
		this.statusList = new ArrayList<>();
		this.statusList.add(aberto);
		Status fechado = new Status();
		fechado.setStatus("FECHADO");
		this.statusList.add(fechado);

		listaStatus = this.statusList;

		if (listaStatus != null && listaStatus.size() > 0) {

			iteratorLista = listaStatus.iterator();

			this.selectItemStatus = new ArrayList<>();

			while (iteratorLista.hasNext()) {

				status = iteratorLista.next();

				if (status.getStatus() != null) {

					this.selectItemStatus.add(new SelectItem(status.getStatus()));
				}
			}
		}

		// Saída da informação

		return this.selectItemStatus;
	}

	/**
	 * Metodo utilizado para buscar na base de dados o campo idTicket.
	 *
	 * @param
	 * @return listaIds
	 */

	public List<SelectItem> getSelectItemIds() {

		// Declaração de variáveis

		List<Id> listaIds = null;

		Iterator<Id> iteratorLista = null;

		Id id = null;

		// Processamento dos dados

		listaIds = manutencaoTicket.buscarIds();

		if (listaIds != null && listaIds.size() > 0) {

			iteratorLista = listaIds.iterator();

			this.selectItemIds = new ArrayList<>();

			while (iteratorLista.hasNext()) {

				id = iteratorLista.next();

				if (id.getId() != null) {

					this.selectItemIds.add(new SelectItem(id.getId()));
				}
			}
		}

		// Saída da informação

		return this.selectItemIds;
	}

	/**
	 * Metodo utilizado para verificar na base de dados se o usuario já está
	 * cadastrado e devolve para a pagina cadastrarTicket/pesquisarTicket.
	 *
	 * @param
	 * @return usuarioList
	 */

	public String pesquisarUsuarioCadastro(String usuarioCadastro) {

		// Processamento dos dados
		usuarioList = new ArrayList<>();

		this.usuarioList = manutencaoUsuario.pesquisar(usuarioCadastro.toString());

		if (this.usuarioList == null) {
			this.caption = "usuario invalido ou o banco de dados está inoperante ";
		} else {
			this.caption = null;
		}
		return "/paginas/ticket/cadastro/cadastrarTicket";
	}
	
	/**
	 * Metodo utilizado para carregar a pagina (pesquisarTicket) para preencher as comboBox
	 *
	 * 
	 * @return pagina (pesquisarTicket)
	 */
	public String pesquisarTicket() {
		getSelectItemTipos();
		return "/paginas/ticket/pesquisa/pesquisarTicket";
	}

	/**
	 * Metodo utilizado para carregar a pagina (pesquisarTicket) para preencher as comboBox
	 *
	 * 
	 * @return pagina (pesquisarTicket)
	 */
	public String confirmarCadastro() {
		
		FacesContext context = FacesContext.getCurrentInstance();
		Boolean flag = false;
		
		/*verifica se todas as comboBox estão preenchidas e faz uma pesquisar com o que foi selecionado e
		 * se não estiverem é instaciado um novo objeto para as classe que compoem as comboBox
		 */
		if ((this.ticket.getPrioridade() != null && this.ticket.getPrioridade().getIdPrioridade() != null)
				|| (this.ticket.getCategoria() != null && this.ticket.getCategoria().getIdCategoria() != null)
				|| (this.ticket.getAnalista() != null && this.ticket.getAnalista().getIdAnalista() != null)
				|| (this.usuario != null && this.usuario.getIdUsuario() != null)
				|| (this.ticket.getTipo() != null && this.ticket.getTipo().getIdTipo() != null)) {

			this.ticket.setUsuario(manutencaoUsuario.pesquisar(this.ticket.getUsuario().getIdUsuario()));
			this.ticket.setPrioridade(
					manutencaoTicket.pesquisarIdPrioridade(this.ticket.getPrioridade().getIdPrioridade()));
			this.ticket.setAnalista(manutencaoTicket.pesquisarIdAnalista(this.ticket.getAnalista().getIdAnalista()));
			this.ticket
					.setCategoria(manutencaoTicket.pesquisarIdCategoria(this.ticket.getCategoria().getIdCategoria()));
			this.ticket.setTipo(manutencaoTicket.pesquisarIdTipo(this.ticket.getTipo().getIdTipo()));

		} else {
			this.ticket.setUsuario(new Usuario());
			this.ticket.setCategoria(new Categoria());
			this.ticket.setPrioridade(new Prioridade());
			this.ticket.setAnalista(new Analista());
			this.ticket.setTipo(new Tipo());
		}
		if(this.ticket.getNivelAnalista()!= null && this.ticket.getAnalista()!= null){
			flag = manutencaoTicket.validaAnalistaEnivel(this.ticket);
			if(flag==false){
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O Analista e o Nível selecionado  não condizem, Selecione o nivel e faça uma pesquisa do analista",
						"analista e nivel não condizem"));
				return "/paginas/ticket/cadastro/cadastrarTicket";
			}
		}

		return "/paginas/ticket/cadastro/confirmarCadastroTicket";
	}

	/**
	 * Metodo utilizado para setar o objeto Ticket com o ID/Nome do Analista e
	 * devolve para a pagina confirmarAtualizacaoTicket. Utilizado no momento em
	 * que se é alterado o Analista responsavel pelo Ticket
	 * e tambem valida se o nivel do analista condiz com o analista selecionado, se isto não for verdade,retorna para a pagina (alterarTicket)
	 *
	 * @param
	 * @return IdAnalista , nome.Analista
	 */

	public String confirmarAtualizacaoNoTicket() {
		
		FacesContext context = FacesContext.getCurrentInstance();
	
		Boolean flag = false;
		Analista analista = new Analista();
		
		if (!this.ticket.getAnalista().getIdAnalista().equals(0)) {
			analista = ticket.getAnalista();
			this.ticket.setAnalista(manutencaoTicket.pesquisarIdAnalista(this.ticket.getAnalista().getIdAnalista()));;
		}else{
			analista = ticket.getAnalista();
		}
		flag = manutencaoTicket.validaAnalistaEnivel(this.ticket);
		if(flag==false){
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "O Analista e o Nível selecionado  não condizem, Selecione o nivel e faça uma pesquisa do analista",
					"analista e nivel não condizem"));
			this.ticket.setAnalista(analista);
			return "/paginas/ticket/cadastro/alterarTicket";
		}
		return "/paginas/ticket/cadastro/confirmarAtualizacaoTicket";
	}

	/**
	 * Metodo utilizado para setar o objeto Ticket o nivel do analisata e o
	 * campo Descrição. Utilizado no momento em que se é criado um novo Ticket
	 *
	 */
	public String cadastrarTicket() {
		this.ticket.setNivelAnalista(1);
		this.ticket.setDescricao("");
		return "/paginas/ticket/cadastro/cadastrarTicket";
	}

	/**
	 * Metodo utilizado para gerar relatorio.
	 * 
	 * @param status
	 * @return relatorioListFechado , relatorioListAberto
	 */

	public String relatorios(String status) {
		if (status.equals("0")) {
			this.relatorioListFechado = null;
			this.relatorioListAberto = null;
			this.relatorioDeDias = null;
		}
		if (status.equals("FECHADO")) {
			this.relatorioListAberto = null;
			this.relatorioDeDias = null;
			this.setRelatorioListFechado(manutencaoTicket.relatorioDeFechados(status));
		}
		if (status.equals("ABERTO")) {
			this.relatorioListFechado = null;
			this.relatorioDeDias = null;
			this.setRelatorioListAberto(manutencaoTicket.relatorioDeAbertos(status));
		}
		getSelectItemTipos();

		return "/paginas/ticket/cadastrarTicket";

	}
		
	/**
	 * Metodo utilizado para preencher a comboBox dos analista
	 * 
	 * 
	 * @return  pagina cadastrarTicket
	 */
	
	public String pesquisarAnalistaCadastro(){
		
		return"/paginas/ticket/cadastrarTicket";
	}
	
	/**
	 * Metodo utilizado gerear relatorios para a pagina Relatorios
	 * 
	 * @param numeroDeDias
	 * @return  relatorio de tickets
	 */
	
	public String relatoriosDatas(String numeroDeDias){
		FacesContext context = FacesContext.getCurrentInstance();
		this.relatorioListFechado = null;
		this.relatorioListAberto = null;
		
		if(validaNumero(numeroDeDias)==true){
			this.setRelatorioDeDias(manutencaoTicket.relatoriosData(new Integer (numeroDeDias))) ;
		}else{
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Não digite caracters ou espaços para gerar relatorio", "caracter em vez de numero"));
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Digite Somente números", "caracter em vez de numero"));
			getSelectItemTipos();
			this.relatorioDeDias = null;
			return"/paginas/ticket/consulta/relatorios";
		}
		getSelectItemTipos();
		return"/paginas/ticket/consulta/relatorios";
	}
	
	public String relatoriosDatasFechados(String numeroDeDiasFechados){
		FacesContext context = FacesContext.getCurrentInstance();
		this.relatorioListFechado = null;
		this.relatorioListAberto = null;
		
		if(validaNumero(numeroDeDiasFechados)==true){
			this.setRelatorioDeDias(manutencaoTicket.relatoriosDataFechados(new Integer (numeroDeDiasFechados))) ;
		}else{
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Não digite caracters ou espaços para gerar relatorio", "caracter em vez de numero"));
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Digite Somente números", "caracter em vez de numero"));
			getSelectItemTipos();
			this.relatorioDeDias = null;
			return"/paginas/ticket/consulta/relatorios";
		}
		getSelectItemTipos();
		return"/paginas/ticket/consulta/relatorios";
	}
	
	
	/**
	 * Metodo que retorna a lista de ticket 
	 * atual
	 * 
	 * @return lista de tickets
	 */
	public List<Ticket> getTicketList() {

		return this.ticketList;
	}

	/**
	 * Metodo que pega o status atual da classe Status utilizado para a pagina
	 * pesquisar (combox)
	 * 
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Metodo que seta o status atual da classe Status utilizado para a pagina
	 * pesquisar (combox)
	 * 
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Metodo utilizado para gerar relatorio.
	 * 
	 * @param status
	 * @return relatorioListAberto
	 */

	public List<Relatorio> getRelatorioListFechado() {
		return relatorioListFechado;
	}

	/**
	 * @return the relatorioListAberto
	 */
	public List<Relatorio> getRelatorioListAberto() {
		return relatorioListAberto;
	}

	/**
	 * @param relatorioListAberto
	 *            the relatorioListAberto to set
	 */
	public void setRelatorioListAberto(List<Relatorio> relatorioListAberto) {
		this.relatorioListAberto = relatorioListAberto;
	}

	/**
	 * @param relatorioListFechado
	 *            the relatorioListFechado to set
	 */
	public void setRelatorioListFechado(List<Relatorio> relatorioListFechado) {
		this.relatorioListFechado = relatorioListFechado;
	}

	/**
	 * Get e Set da classe Usuário
	 *
	 * @param 'usuario'
	 * @return 'usuario'
	 */
	public Usuario getUsuario() {
		return this.usuario;
	}

	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * Metodo para pegar o objeto ticket da classe Ticket
	 * 
	 * @return the ticket
	 */
	public Ticket getTicket() {
		return ticket;
	}

	/**
	 * Metodo para setar o objeto ticket da classe Ticket
	 * 
	 * @param ticket
	 *            the ticket to set
	 */
	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
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
	 * @return the relatorioDeDias
	 */
	public List<Relatorio> getRelatorioDeDias() {
		return relatorioDeDias;
	}

	/**
	 * @param relatorioDeDias the relatorioDeDias to set
	 */
	public void setRelatorioDeDias(List<Relatorio> relatorioDeDias) {
		this.relatorioDeDias = relatorioDeDias;
	}

	

}

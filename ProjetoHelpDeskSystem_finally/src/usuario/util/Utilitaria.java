/**
 * usuario.util
 */
package usuario.util;


/**
 * Classe utilitária para implementar validação da entrada de dados.
 * 
 * @author Tiago/Wellington/Fernando
 * 
 * @since 03/05/2016
 * 
 * @version 1.0
 * 
 * 
 */

public class Utilitaria {

	
	

	public static Boolean validarSIMouNAO(String opcao){
		
		boolean flag = false;
	
		if(opcao.charAt(0)=='s'||opcao.charAt(0) == 'S'){
			flag = true;
		}
		else{
			flag = false;
		}
		
		return flag;
		}
	
	public static Boolean validarSexo(String sexo){
		
		boolean flag = false;
		
		if(sexo.charAt(0)=='m'||sexo.charAt(0) == 'M'){
			flag = true;
		}
		else{
			flag = false;
		}
	
		return flag;
	}
	public static Boolean validarPermissao(String permissao){
		
		boolean flag = false;
		
		if(permissao.charAt(1)=='d'||permissao.charAt(1) == 'D'){
			flag = true;
		}
		else{
			flag = false;
		}
	
		return flag;
	}
}




	

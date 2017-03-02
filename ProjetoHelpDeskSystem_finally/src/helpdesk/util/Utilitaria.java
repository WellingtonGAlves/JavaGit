/**
 * package helpdesk.util
 */
package helpdesk.util;



/**
 * Classe Utilitaria.
 * Classe utilitária para implementar validação da entrada de dados.
 * 
 * @author Wellington alves/Tiago/Fernando
 * 
 * @since 14/05/2016
 * 
 * @version 1.0
 * 
 */

public class Utilitaria {
	
	
	/**
	 * 
	 * @param status
	 * @return
	 */

	public static Boolean validarStatus(String status){
		
		boolean flag = false;
		
		if(status.charAt(0)=='a'||status.charAt(0) == 'A'){
			flag = true;
		}
		else{
			flag = false;
		}
		
		return flag;
		}
	
	public static Boolean validaNumero(String numeroDeDias){
		Integer tamanho = numeroDeDias.length();
		boolean flag = false;
		for(int i = 0 ;i<tamanho;i++){
		if(numeroDeDias.charAt(i)!='0'&&
		numeroDeDias.charAt(i) != '1' &&
		numeroDeDias.charAt(i)!='2'&&
		numeroDeDias.charAt(i)!='3'&&
		numeroDeDias.charAt(i)!='4'&&
		numeroDeDias.charAt(i)!='5'&&
		numeroDeDias.charAt(i)!='6'&&
		numeroDeDias.charAt(i)!='7'&&
		numeroDeDias.charAt(i)!='8'&&
		numeroDeDias.charAt(i)!='9'){
			flag = false;
			return flag;
		}
		else{
			flag = true;
		}
		}
		return flag;
		}
	
	

	
}

package br.com.cs.desafio.validators;

import java.util.Arrays;
import java.util.List;

public class Validator {
	
	/**
	 * Id de serializaçao do objeto
	 */
	
	
	/**
	 * Mensagem de erro ou warning referente o preenchimento do campo 
	 **/
	private String message;
	
	
	public Validator () {
		
	}
	
	public Validator (String message ){
		this.message = message;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}


	
	public static List< Validator > newList (String message ) {		
		return Arrays.asList( new Validator(message));
	}	


	@Override
	public String toString() {
		return this.message;
	}


	

}

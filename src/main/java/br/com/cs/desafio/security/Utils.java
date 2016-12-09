package br.com.cs.desafio.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import br.com.cs.desafio.model.Customer;

public class Utils {

	public static String GerarHashMd5(String input) throws NoSuchAlgorithmException {
		String string = input;
		MessageDigest m;

		try {
			m = MessageDigest.getInstance("MD5");
			m.update(string.getBytes(), 0, string.length());
			BigInteger i = new BigInteger(1, m.digest());

			// Formatando o resuldado em uma cadeia de 32 caracteres,
			// completando com 0 caso falte
			string = String.format("%1$032X", i);

			System.out.println("MD5: " + string);
		}

		catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return string;
	}
	
	public static void hideManyPasswords(List<Customer> list){
		
//		imperformatico, mas foi o unico modo que encontrei no momento, para anular 
		for(Customer customer : list){
			customer.setPassword(null);
		}
	}
	public static void hidePassword(Customer customer){
			customer.setPassword(null);
	}
	
}

//package br.com.cs.desafio.configuration;
//
//public abstract class GenerateHashPasswordUtil {
//
//	private static Object salt;
//
//public static String generateHash(String password) {
//
// MessageDigestPasswordEncoder digestPasswordEncoder = getInstanceMessageDisterPassword();
//
//String encodePassword = digestPasswordEncoder.encodePassword(password, salt);
//
//return encodePassword;
//
//������� }
//
//	// informo tipo de enconding que desejo
//
//	MessageDigestPasswordEncoder�digestPasswordEncoder=new MessageDigestPasswordEncoder("MD5");
//
//	return digestPasswordEncoder;
//
//}
//
//	// m�todo que faz a valida��o� como n�o usamos salt deixei em null
//
//	public static boolean isPasswordValid(String password, String hashPassword) {
//
//MessageDigestPasswordEncoder digestPasswordEncoder = getInstanceMessageDisterPassword();
//
// return digestPasswordEncoder.isPasswordValid(hashPassword, password, salt);
//
//������� }
//
//}
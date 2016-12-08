//package br.com.cs.desafio.configuration;
//
//import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
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
// }
//
//	// informo tipo de enconding que desejo
//
//	MessageDigestPasswordEncoderdigestPasswordEncoder=new MessageDigestPasswordEncoder("MD5");
//
//	return digestPasswordEncoder;
//
//}
//
//	// mtodo que faz a validao como no usamos salt deixei em null
//
//	public static boolean isPasswordValid(String password, String hashPassword) {
//
//MessageDigestPasswordEncoder digestPasswordEncoder = getInstanceMessageDisterPassword();
//
// return digestPasswordEncoder.isPasswordValid(hashPassword, password, salt);
//
// }
//
//}
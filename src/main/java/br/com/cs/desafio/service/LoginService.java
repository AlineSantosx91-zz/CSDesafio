package br.com.cs.desafio.service;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cs.desafio.dao.CustomerRepository;
import br.com.cs.desafio.dao.LoginRepository;
import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.security.Utils;
import br.com.cs.desafio.validators.Result;
import br.com.cs.desafio.validators.Validator;

@Service
public class LoginService  implements ILoginService{

	@Autowired
	@Qualifier("loginRepository")
	LoginRepository loginRepository;
	
	@Autowired
	@Qualifier("customerRepository")
	CustomerRepository customerRepository;

	protected final Logger logger = Logger.getLogger(getClass());
	@Override
	public ResponseEntity<Result<Customer>> login(String email, String password) {
		Result<Customer> result = null;
		
		
		try {
			if (email != null && !email.trim().isEmpty()) {
				password = Utils.GerarHashMd5(password);

				/*Caso o e-mail n�o exista, retornar erro com status apropriado mais a
				mensagem "Usu�rio e/ou senha inv�lidos"*/
			
				result = new Result<Customer>( this.customerRepository.findUnique(email));
				if(result == null || result.getResult() == null){
					result = new Result<Customer>(new Validator("Usu�rio e/ou senha inv�lidos"));
					return new ResponseEntity<Result<Customer>>(result, HttpStatus.NOT_FOUND);

				}
				
				/*Caso o e-mail exista mas a senha n�o bata, retornar o status apropriado
				 * 401 mais a mensagem "Usu�rio e/ou senha inv�lidos"*/
				
				if(result.getResult() != null && !result.getResult().getPassword().equals(password)){
					result = new Result<Customer>(new Validator("Usu�rio e/ou senha inv�lidos"));
					return new ResponseEntity<Result<Customer>>(result, HttpStatus.UNAUTHORIZED);
				}
				
			}
			/*Caso o e-mail e a senha correspondam a um usu�rio existente, retornar igual 
			 ao endpoint de Cria��o.*/
			result = new Result<Customer>(this.loginRepository.login(email, password));
			if(result != null && result.getStatus() == 1){
				result = updateUserInfo(result.getResult());
			}
//			result.getResult().setPassword(null);
			Utils.hidePassword(result.getResult());
			return new ResponseEntity<Result<Customer>>(result, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(e.getMessage());
			result = new Result<Customer>(new Validator("Houve um erro interno, tente novamente"));
			return new ResponseEntity<Result<Customer>>(result, HttpStatus.INTERNAL_SERVER_ERROR);

		}
				
	}
	
	private Result<Customer> updateUserInfo( Customer customer){
		customer.setLastLogin(new Date());
		try {
			customer.setToken( Utils.GerarHashMd5(UUID.randomUUID().toString()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return new Result<Customer>(customerRepository.save(customer));
	}

}

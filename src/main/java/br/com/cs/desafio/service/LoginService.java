package br.com.cs.desafio.service;

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
				/*Caso o e-mail não exista, retornar erro com status apropriado mais a
				mensagem "Usuário e/ou senha inválidos"*/
			
				result = new Result<Customer>( this.customerRepository.findUnique(email));
				if(result == null || result.getResult() == null){
					result = new Result<Customer>(new Validator("Usuário e/ou senha inválidos"));
					return new ResponseEntity<Result<Customer>>(result, HttpStatus.NOT_FOUND);

				}
				
				/*Caso o e-mail exista mas a senha não bata, retornar o status apropriado
				 * 401 mais a mensagem "Usuário e/ou senha inválidos"*/
				
				if(result.getResult() != null && !result.getResult().getPassword().equals(password)){
					result = new Result<Customer>(new Validator("Usuário e/ou senha inválidos"));
					return new ResponseEntity<Result<Customer>>(result, HttpStatus.UNAUTHORIZED);
				}
				
			}
			/*Caso o e-mail e a senha correspondam a um usuário existente, retornar igual 
			 ao endpoint de Criação.*/
			result = new Result<Customer>(this.loginRepository.login(email, password));
			if(result != null && result.getStatus() == 1){
				result = updateUserInfo(result.getResult());
			}
			result.getResult().setToken( UUID.randomUUID().toString());
			result.getResult().setPassword(null);
			return new ResponseEntity<Result<Customer>>(result, HttpStatus.OK);

		} catch (Exception e) {
			logger.error(e.getMessage());
			result = new Result<Customer>(new Validator("Houve um erro interno, tente novamente"));
			return new ResponseEntity<Result<Customer>>(result, HttpStatus.OK);

		}
				
	}
	
	private Result<Customer> updateUserInfo( Customer customer){
		customer.setLastLogin(new Date());
		return new Result<Customer>(customerRepository.save(customer));
	}

}

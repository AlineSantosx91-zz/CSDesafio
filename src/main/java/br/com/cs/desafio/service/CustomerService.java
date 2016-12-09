
package br.com.cs.desafio.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cs.desafio.dao.CustomerRepository;
import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.security.Utils;
import br.com.cs.desafio.validators.Result;
import br.com.cs.desafio.validators.Validator;

@Service
public class CustomerService implements ICustomerService {

	@Autowired
	@Qualifier("customerRepository")
	CustomerRepository customerRepository;

	protected final Logger logger = Logger.getLogger(getClass());

	@Override
	public ResponseEntity<Result<Customer>> findById(long id) {
		Result<Customer> result = null;

		try {
			result = new Result<Customer>(this.customerRepository.findOne(id));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		if (result == null) {
			result = new Result<Customer>(new Validator("Houve um erro interno, tente novamente"));
		}

		if (result.getResult() == null) {
			return new ResponseEntity<Result<Customer>>(new Result<>(new Validator("Usuário não localizado")),
					HttpStatus.NOT_FOUND);
		}
		
		Utils.hidePassword(result.getResult());

		return new ResponseEntity<Result<Customer>>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Result<Customer>> findByEmail(String email) {
		Result<Customer> result = null;

		if (email == null || email.trim().isEmpty()) {
			result = new Result<Customer>(new Validator("Email não pode ser vazio"));
		} else {
			try {
				result = new Result<Customer>(this.customerRepository.findUnique(email));
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		if (result == null) {
			result = new Result<Customer>(new Validator("Houve um erro interno, tente novamente"));
			return new ResponseEntity<Result<Customer>>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (result.getResult() == null) {
			result = new Result<Customer>(new Validator("O email " + email + " não foi encontrado"));
			return new ResponseEntity<Result<Customer>>(result, HttpStatus.NOT_FOUND);
		}
		
		Utils.hidePassword(result.getResult());
		return new ResponseEntity<Result<Customer>>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Result<Customer>> saveCustomer(Customer customer) {

		Result<Customer> result = null;
		List<Validator> validators = new ArrayList<>();

		if (customer.getName() == null || customer.getName().trim().isEmpty()) {
			validators.add(new Validator("Nome é obrigatório"));
		}

		if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
			validators.add(new Validator("Email é obrigatório"));
		}

		if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
			validators.add(new Validator("Senha é obrigatório"));
		}

		if (validators.size() > 0) {
			return new ResponseEntity<Result<Customer>>(new Result<Customer>(0, validators), HttpStatus.BAD_REQUEST);
		}
		try {
			Customer findUnique = this.customerRepository.findUnique(customer.email);

			if (findUnique != null) {
				result = new Result<Customer>(new Validator("E-mail já existente"));
				return new ResponseEntity<Result<Customer>>(result, HttpStatus.UNPROCESSABLE_ENTITY);
			}

			customer.setModified(new Date());
			String hashPassword = Utils.GerarHashMd5(customer.getPassword());
			customer.setPassword(hashPassword);
			result = new Result<Customer>(this.customerRepository.save(customer));
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		Utils.hidePassword(result.getResult());
		return new ResponseEntity<Result<Customer>>(result, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Result<Customer>> verifyResult(Result<Customer> result, Boolean isPOST) {
		if (result == null) {
			if (isPOST) {
				return new ResponseEntity<Result<Customer>>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return new ResponseEntity<Result<Customer>>(new Result<>(new Validator("Houve um erro interno")),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (result.getStatus() == 0) {
			errorResponse(result.getValidators());
		}
		return null;
	}

	@Override
	public List<Validator> errorResponse(List<Validator> validators) {
		return validators;
	}

	@Override
	public ResponseEntity<Result<Customer>> getPerfilCustomer(String tokenP, Long id) {
		Customer customer = this.customerRepository.findOne(id);

		// a principio, se o usuario estiver null ou nao tiver token, nao esta
		// autorizado
		if (customer == null || customer.getToken() == null) {
			return new ResponseEntity<Result<Customer>>(new Result<>(new Validator("Não autorizado")),
					HttpStatus.UNAUTHORIZED);
		}

		String token = this.customerRepository.findToken(tokenP);

		/*
		 * Caso o token não exista, retornar erro com status apropriado com a
		 * mensagem "Não autorizado".
		 */
		if (token == null) {
			return new ResponseEntity<Result<Customer>>(new Result<>(new Validator("Não autorizado")),
					HttpStatus.UNAUTHORIZED);
		}

		/*
		 * Caso o token exista, buscar o usuário pelo id passado no path e
		 * comparar se o token no modelo é igual ao token passado no header.
		 */

		/*
		 * Caso não seja o mesmo token, retornar erro com status apropriado e
		 * mensagem "Não autorizado"
		 */

		if (token != null && !token.trim().isEmpty()) {
			if (!customer.getToken().equals(token)) {
				return new ResponseEntity<Result<Customer>>(new Result<>(new Validator("Não autorizado")),
						HttpStatus.UNAUTHORIZED);
			}

			/*
			 * Caso seja o mesmo token, verificar se o último login foi a MENOS
			 * que 30 minutos atrás. Caso não seja a MENOS que 30 minutos atrás,
			 * retornar erro com status apropriado com mensagem
			 * "Sessão inválida".
			 */

			if (customer.getToken().equals(token)) {

				Date lastLogin = verifyLastLogin(customer);
				Long pastMinutes = calcDiferenceBetweenDates(lastLogin);
				if (pastMinutes > 30) {

					return new ResponseEntity<Result<Customer>>(new Result<>(new Validator("Sessão inválida")),
							HttpStatus.UNAUTHORIZED);
				}
			}
		}
		/*
		 * Caso tudo esteja ok, retornar o usuário no mesmo formato do retorno
		 * do Login.
		 */
		Utils.hidePassword(customer);
		return new ResponseEntity<Result<Customer>>(new Result<Customer>(customer), HttpStatus.OK);
	}

	@Override
	public Date verifyLastLogin(Customer customer) {
		return this.customerRepository.verifyLastLogin(customer.id);
	}

	public Long calcDiferenceBetweenDates(Date lastLogin) {
		Duration dur = new Duration(new DateTime(lastLogin), DateTime.now());

		System.out.println(dur.getStandardMinutes());

		return dur.getStandardMinutes();

	}

}
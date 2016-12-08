package br.com.cs.desafio.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.cs.desafio.dao.CustomerRepository;
import br.com.cs.desafio.model.Customer;
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
		
		return new ResponseEntity<Result<Customer>>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Result<Customer>> findByEmail(String email) {
		Result<Customer> result = null;

		if (email != null && !email.trim().isEmpty()) {
			try {
				result = new Result<Customer>(this.customerRepository.findUnique(email));
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else {
			result = new Result<Customer>(new Validator("Email não pode ser vazio"));
		}

		if (result == null) {
			result = new Result<Customer>(new Validator("Houve um erro interno, tente novamente"));
		}
		return new ResponseEntity<Result<Customer>>(result, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Result<Customer>> saveCustomer(Customer customer) {

		Result<Customer> result = null;
		List<Validator> validators = new ArrayList<>(); 
		
		if(customer.getName() == null || customer.getName().trim().isEmpty()){
			validators.add(new Validator("Nome é obrigatorio"));
		}
		
		if(customer.getEmail() == null || customer.getEmail().trim().isEmpty()){
			validators.add(new Validator("Email é obrigatorio"));
		}
				
		if(customer.getPassword() == null || customer.getPassword().trim().isEmpty()){
			validators.add(new Validator("Senha é obrigatorio"));
		}
		

		if(validators.size() > 0){
			return new ResponseEntity<Result<Customer>>(new Result<Customer>(0, validators), HttpStatus.OK);
		}
		
		Customer findUnique = this.customerRepository.findUnique(customer.email);
		if(findUnique != null){
			result = new Result<Customer>(new Validator("E-mail já existente"));
			return new ResponseEntity<Result<Customer>>(result, HttpStatus.OK);
		}

		try {
			customer.setModified(new Date());
			result = new Result<Customer>(this.customerRepository.save(customer));
			verifyResult(result, true);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		return new ResponseEntity<Result<Customer>>(result, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<Result<Customer>> verifyResult(Result<Customer> result, Boolean isPOST) {
		if (result == null) {
			if (isPOST) {
				return new ResponseEntity<Result<Customer>>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return new ResponseEntity<Result<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
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

}

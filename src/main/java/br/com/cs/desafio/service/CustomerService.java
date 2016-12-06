package br.com.cs.desafio.service;

import java.util.List;

import javax.transaction.Transactional;

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
@Transactional
public class CustomerService implements ICustomerService {
	
	@Autowired
    @Qualifier("customerRepository")
	CustomerRepository customerRepository;
	
	protected final Logger logger = Logger.getLogger(getClass());


	@Override
	public ResponseEntity<Customer> findById(long id) {
		Result<Customer> result = null;
		
		try{
			result = new Result<Customer>(customerRepository.findOne(id));
			verifyResult(result, false);
		}catch (Exception e) {
			logger.error(e.getMessage());
			result = new Result<Customer>(new Validator("Houve um erro interno, tente novamente"));
		}
		
		return new ResponseEntity<Customer>(result.getResult(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Customer> findByEmail(String email) {
		Result<Customer> result = null;
		try{
			result = new Result<Customer>(customerRepository.findUnique(email));
			verifyResult(result, false);
		}catch (Exception e) {
			logger.error(e.getMessage());
			result = new Result<Customer>(new Validator("Houve um erro interno, tente novamente"));
		}
		return new ResponseEntity<Customer>(result.getResult(), HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Customer> saveCustomer(Customer customer) {
		
		Result<Customer> result = null;

		try {
			result = new Result<Customer>(customerRepository.save(customer));
			verifyResult(result, true);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		return new ResponseEntity<Customer>(result.getResult(), HttpStatus.OK);
		
	}


	@Override
	public ResponseEntity<Customer> verifyResult(Result<Customer> result, Boolean isPOST) {
		if (result == null) {
			if(isPOST){
				return new ResponseEntity<Customer>(HttpStatus.UNPROCESSABLE_ENTITY);
			}
			return new ResponseEntity<Customer>(HttpStatus.INTERNAL_SERVER_ERROR);
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

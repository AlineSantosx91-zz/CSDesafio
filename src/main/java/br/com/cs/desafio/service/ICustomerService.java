package br.com.cs.desafio.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.validators.Result;
import br.com.cs.desafio.validators.Validator;


public interface ICustomerService {
	
		ResponseEntity<Customer> findById(long id);
     
	 	ResponseEntity<Customer> findByEmail(String email);
	     
	 	ResponseEntity<Customer> saveCustomer(Customer custumer);
	 	
	 	ResponseEntity<Customer> verifyResult(Result<Customer>  result, Boolean isPOST);
	 	
	 	List<Validator> errorResponse(List<Validator> validators);
	 	
	   
}

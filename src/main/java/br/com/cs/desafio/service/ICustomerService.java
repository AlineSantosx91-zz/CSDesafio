package br.com.cs.desafio.service;

import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.validators.Result;


public interface ICustomerService {
	
	 	Result<Customer> findById(long id);
     
	 	Result<Customer> findByEmail(String email);
	     
	 	Result<Customer> saveCustomer(Customer custumer);
	   
}

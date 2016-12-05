package br.com.cs.desafio.service;

import java.util.List;

import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.validators.Result;


public interface ICustomerService {
	
	 	Result<Customer> findById(long id);
     
	 	Result<Customer> findByEmail(String email);
	     
	 	Result<Customer> saveCustomer(Customer custumer);
	     
	 	Result<Customer> updateCustomer(Customer custumer);
	     
	    void deleteCustomerById(long id);
	 
	    Result<List<Customer>> findAllCustomers(); 
	     
	    void deleteAllCustomers();
	     
	    public boolean isCustomerExist(Customer custumer);
}

package br.com.cs.desafio.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cs.desafio.dao.CustomerRepository;
import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.service.CustomerService;
import br.com.cs.desafio.validators.Result;

@RestController
@RequestMapping(value = "/api")
public class CustomerController {

	@Autowired
	@Qualifier("customerService")
	CustomerService customerService;

	@Autowired
	@Qualifier("customerRepository")
	CustomerRepository customerRepository;

	protected final Logger logger = Logger.getLogger(getClass());

	@GetMapping("/hello")
	public String getHello() {
		return "helo";
	}

	@GetMapping("/customers")
	private ResponseEntity<List<Customer>> getAllCustomers(@PathVariable(value = "email", required = false) String email) {
		List<Customer> findAll = new ArrayList<>();
		
		try{
			findAll = customerRepository.findAll();
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	
		return new ResponseEntity<List<Customer>>(findAll, HttpStatus.OK);
	}
	
	@GetMapping("/customers/{email}")
	private ResponseEntity<Result<Customer>> getUniqueCustomer(@PathVariable("email") String email) {
		ResponseEntity<Result<Customer>> findByEmail = customerService.findByEmail(email);
		return findByEmail;
	}

	@PostMapping(value = "/customers")
	public 	ResponseEntity<Result<Customer>> post(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}


}

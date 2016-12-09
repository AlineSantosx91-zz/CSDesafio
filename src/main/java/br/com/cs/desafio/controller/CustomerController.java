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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cs.desafio.dao.CustomerRepository;
import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.security.Utils;
import br.com.cs.desafio.service.CustomerService;
import br.com.cs.desafio.validators.Result;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class CustomerController {

	@Autowired
	@Qualifier("customerService")
	CustomerService customerService;

	@Autowired
	@Qualifier("customerRepository")
	CustomerRepository customerRepository;

	protected final Logger logger = Logger.getLogger(getClass());

	@GetMapping("/customers")
	private ResponseEntity<List<Customer>> getAllCustomers(@PathVariable(value = "email", required = false) String email) {
		List<Customer> findAll = new ArrayList<>();
		
		try{
			findAll = customerRepository.findAll();
			Utils.hideManyPasswords(findAll);
		}catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<List<Customer>>(findAll, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Customer>>(findAll, HttpStatus.OK);
	}
	
	@GetMapping("/customers/{id}")
	private ResponseEntity<Result<Customer>> getUniqueCustomer(@PathVariable("id") Long id) {
		ResponseEntity<Result<Customer>> findById = customerService.findById(id);
		return findById;
	}
	
	@GetMapping("/customers/{id}/profile")
	private ResponseEntity<Result<Customer>> getPerfilCustomer(@RequestHeader(value="token") String token,
			@PathVariable("id") Long id) {
		ResponseEntity<Result<Customer>> findById = customerService.getPerfilCustomer(token, id);
		return findById;
	}

	@PostMapping(value = "/customers")
	public 	ResponseEntity<Result<Customer>> post(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}
}

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.cs.desafio.dao.CustomerRepository;
import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.service.CustomerService;

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
	public void getCustomers(@RequestParam(value = "email", required = false) String email) {
		if (email != null && !email.trim().isEmpty()) {
			getUniqueCustomer(email);
		} else {
			getAllCustomers();
		}
	}

	private ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> findAll = new ArrayList<>();
		
		try{
			findAll = customerRepository.findAll();
		}catch (Exception e) {
			logger.error(e.getMessage());
		}
	
		return new ResponseEntity<List<Customer>>(findAll, HttpStatus.OK);
	}

	private ResponseEntity<Customer> getUniqueCustomer(String email) {
		return customerService.findByEmail(email);
	}


	@GetMapping("/customers/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
		return customerService.findById(id);
	}


	@PostMapping(value = "/customers")
	public ResponseEntity<Customer> post(@RequestBody Customer customer) {
		return customerService.saveCustomer(customer);
	}


}

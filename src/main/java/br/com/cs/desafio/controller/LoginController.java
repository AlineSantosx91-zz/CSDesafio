package br.com.cs.desafio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.service.LoginService;
import br.com.cs.desafio.validators.Result;

@RestController
@RequestMapping(value = "/auth", produces = "application/json")
public class LoginController {
	
	@Autowired
	@Qualifier("loginService")
	LoginService loginService;
	 
	 @PostMapping(value = "/login")
		public ResponseEntity<Result<Customer>> post(@RequestBody Customer customer) {
		     ResponseEntity<Result<Customer>> user = loginService.login(customer.getEmail(), customer.getPassword());
		     return user;
	 	}
}

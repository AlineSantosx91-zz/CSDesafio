package br.com.cs.desafio.service;

import org.springframework.http.ResponseEntity;

import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.validators.Result;

public interface ILoginService {
	
	ResponseEntity<Result<Customer>> login(String email, String password);

}

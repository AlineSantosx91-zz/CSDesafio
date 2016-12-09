package br.com.cs.desafio.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.model.Phone;
import br.com.cs.desafio.validators.Result;

/**
 * 
 * Teste de Integração Teste Unitário
 * 
 * @author Aline Santos
 *
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CustomerTeste {

	public static final String REST_SERVICE_URI = "http://localhost:8080/api";

	/* POST */
	public void createUser() {
		System.out.println("Testing create User API----------");
		RestTemplate restTemplate = new RestTemplate();
		Customer customer = new Customer();
		customer.setName("João da Silva");
		customer.setEmail("joao@silva.org");
		customer.setPassword("hunter2");

		Phone phone1 = new Phone();
		phone1.setDdd("11");
		phone1.setNumber("123456987");

		Phone phone2 = new Phone();
		phone2.setDdd("21");
		phone2.setNumber("123654789");

		List<Phone> phones = new ArrayList<>();
		phones.add(phone1);
		phones.add(phone2);

		customer.setPhones(phones);

		URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/customers/", customer, Customer.class);
		System.out.println(uri.toASCIIString());
	}

	/* GET Customers */
	public void getCostumers() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object[]> result = restTemplate.getForEntity(REST_SERVICE_URI + "/customers/", Object[].class);
		System.out.println(result);

		assertNotNull(result);
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	/* GET Customer by Id */
	public void getCostumerById() {

	}

}

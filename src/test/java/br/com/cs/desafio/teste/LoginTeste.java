package br.com.cs.desafio.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import br.com.cs.desafio.model.Customer;


@RunWith(SpringRunner.class)
@WebAppConfiguration
public class LoginTeste {
	
	public static final String REST_SERVICE_URI = "http://localhost:8080";

	
	/*Login*/
	public void login(){
		RestTemplate restTemplate = new RestTemplate();
	
		Customer customer = new Customer();
		customer.setEmail("joana@silva.org");
		customer.setPassword("teste123");
		
		ResponseEntity<Customer> result = restTemplate.postForEntity(REST_SERVICE_URI + "/auth/login", customer, Customer.class);
		assertNotNull(result);
		assertEquals(result.getBody().getEmail(), "joana@silva.org");		
	}

}

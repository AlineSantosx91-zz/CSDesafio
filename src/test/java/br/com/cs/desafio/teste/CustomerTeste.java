package br.com.cs.desafio.teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.isNotNull;

import java.util.ArrayList;
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

@RunWith(SpringRunner.class)
@WebAppConfiguration
public class CustomerTeste {

	
	public static final String REST_SERVICE_URI = "http://localhost:8080/api";
	
	@Test
	/* POST */
	public void createUser() {
		System.out.println("Testing create User API----------");
		RestTemplate restTemplate = new RestTemplate();
		Customer customer = new Customer();
		customer.setName("Joana Mello");
		customer.setEmail("joana@silva.org");
		customer.setPassword("rsrs");

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
			
		ResponseEntity<Customer> result = restTemplate.postForEntity(REST_SERVICE_URI + "/customers/", customer, Customer.class);
		assertNotNull(result);
		assertEquals(result.getBody().getId(),isNotNull());
	}

	/* GET Customers */
	public void getCostumers() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object[]> result = restTemplate.getForEntity(REST_SERVICE_URI + "/customers/", Object[].class);
		System.out.println(result);

		assertNotNull(result);
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	
	
	/* GET Customer by Id */
	public void getCostumerById() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Customer> result = restTemplate.getForEntity(REST_SERVICE_URI + "/customers/1", Customer.class);
		System.out.println(result);

		assertNotNull(result);
		assertEquals(result.getBody().getId().toString(), "1");
	}


}

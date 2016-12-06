package br.com.cs.desafio.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.service.CustomerService;
import br.com.cs.desafio.validators.Result;



@RestController
@RequestMapping(value="/api")
public class CustomerController {
	
	@Autowired
    @Qualifier("customerService")
    CustomerService customerService;  //Service which will do all data retrieval/manipulation work
	
    protected final Logger logger = Logger.getLogger(getClass());

 
    @GetMapping("/hello")
    public String handleRequest(Model model) {
        return "hello";
    }

 
    //-------------------Retrieve Single Customer by Email--------------------------------------------------------
    
    @GetMapping("/customers/{email}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("email") String email) {
         Result<Customer> result = customerService.findByEmail(email);
        if (result.getResult() == null) {
            return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Customer>(result.getResult(), HttpStatus.OK);
    }
     
    //-------------------Create a Customer--------------------------------------------------------
     
	@PostMapping(value = "/customers")
	   public ResponseEntity<Customer> post (@RequestBody Customer customer) {
		Result<Customer> result  = null;
		   
		try {
			
			 result = customerService.saveCustomer(customer);
				if (result.getResult() == null) {
					return new  ResponseEntity<Customer>(result.getResult(), HttpStatus.UNPROCESSABLE_ENTITY);
				} else {
					return new  ResponseEntity<Customer>(result.getResult(), HttpStatus.OK);
				}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);

		}
				
	   }
 
}

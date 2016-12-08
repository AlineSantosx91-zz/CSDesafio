package br.com.cs.desafio.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cs.desafio.dao.CustomerRepository;
import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.model.User;
import br.com.cs.desafio.service.LoginService;
import br.com.cs.desafio.validators.Result;

@RestController
@RequestMapping(value = "/auth", produces = "application/json")
public class LoginController {
	
	@Autowired
	@Qualifier("customerRepository")
	CustomerRepository customerRepository;
	
	@Autowired
	@Qualifier("loginService")
	LoginService loginService;
	
	 @RequestMapping("loginForm")
	  public String loginForm() {
	    return "formulario-login";
	  }
	 
	 @RequestMapping("efetuaLogin")
	 public String efetuaLogin(Customer usuario, HttpSession session) {
		 User findUnique = customerRepository.findUserUnique(usuario.getEmail());
	   if(findUnique != null) {
	     session.setAttribute("usuarioLogado", findUnique);
	     return "menu";
	   }
	   return "redirect:loginForm";
	 }
	 
//	 @RequestHeader(value="token", required=true )String appKey
	 @GetMapping("/")
		public String welcome(Map<String, Object> model) {
			model.put("time", new Date());
			model.put("message", "WELCOME");
			return "welcome";
		}
	 
	 @PostMapping(value = "/login")
		public ResponseEntity<Result<Customer>> post(@RequestBody Customer customer) {
		     ResponseEntity<Result<Customer>> user = loginService.login(customer.getEmail(), customer.getPassword());
		     return user;
	 	}
}

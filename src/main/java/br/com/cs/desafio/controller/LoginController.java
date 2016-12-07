package br.com.cs.desafio.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.cs.desafio.dao.CustomerRepository;
import br.com.cs.desafio.model.User;

@Controller
public class LoginController {
	
	@Autowired
	@Qualifier("customerRepository")
	CustomerRepository customerRepository;
	
	 @RequestMapping("loginForm")
	  public String loginForm() {
	    return "formulario-login";
	  }
	 
	 @RequestMapping("efetuaLogin")
	 public String efetuaLogin(User usuario, HttpSession session) {
		 User findUnique = customerRepository.findUserUnique(usuario.getEmail());
	   if(findUnique != null) {
	     session.setAttribute("usuarioLogado", findUnique);
	     return "menu";
	   }
	   return "redirect:loginForm";
	 }
	

}

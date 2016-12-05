package br.com.cs.desafio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.cs.desafio.model.Customer;

public interface  CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query(value="SELECT c FROM Customer c WHERE c.email=:email")
	public Customer findUnique(@Param("email") String email);
	

}

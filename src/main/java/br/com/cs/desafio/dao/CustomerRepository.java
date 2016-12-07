package br.com.cs.desafio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.model.User;

@Repository
public interface  CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query(value="SELECT c FROM Customer c WHERE c.email=:email")
	public Customer findUnique(@Param("email") String email);
	
	@Query(value="SELECT u FROM User u WHERE u.email=:email")
	public User findUserUnique(@Param("email") String email);

	
}

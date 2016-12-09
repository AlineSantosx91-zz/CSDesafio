package br.com.cs.desafio.dao;

import java.util.Date;

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

	@Query(value="SELECT u.token FROM User u WHERE u.token=:token")
	public String findToken(@Param("token") String token);
	
	
	@Query(value="SELECT c.lastLogin FROM Customer c WHERE c.id=:id")
	public Date verifyLastLogin(@Param("id") Long id);

	
}

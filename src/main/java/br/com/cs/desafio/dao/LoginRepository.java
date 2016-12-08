package br.com.cs.desafio.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.model.User;


@Repository
public interface LoginRepository extends JpaRepository<User, Long>{
	
	@Query(value="SELECT c FROM Customer c WHERE c.email=:email AND c.password=:password")
	public Customer login(@Param("email") String email, @Param("password") String password );

}

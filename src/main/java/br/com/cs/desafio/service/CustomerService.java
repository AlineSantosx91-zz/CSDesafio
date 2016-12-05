package br.com.cs.desafio.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.cs.desafio.dao.CustomerRepository;
import br.com.cs.desafio.model.Customer;
import br.com.cs.desafio.validators.Result;


@Service
@Transactional
public class CustomerService implements ICustomerService {
	
	@Autowired
    @Qualifier("customerRepository")
	CustomerRepository customerRepository;

	@Override
	public Result<Customer> findById(long id) {
		return new Result<Customer>(customerRepository.findOne(id));
	}

	@Override
	public Result<Customer> findByEmail(String email) {
		return new Result<Customer>(customerRepository.findUnique(email));
	}

	@Override
	public Result<Customer> saveCustomer(Customer custumer) {
		 return new Result<Customer>(customerRepository.save(custumer));
		
	}

	@Override
	public Result<Customer> updateCustomer(Customer custumer) {
		return new Result<Customer>(customerRepository.save(custumer));
		
	}

	@Override
	public void deleteCustomerById(long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public  Result<List<Customer>> findAllCustomers() {
		return new Result<List<Customer>>(customerRepository.findAll());
	}

	@Override
	public void deleteAllCustomers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCustomerExist(Customer custumer) {
		// TODO Auto-generated method stub
		return false;
	}

	

}

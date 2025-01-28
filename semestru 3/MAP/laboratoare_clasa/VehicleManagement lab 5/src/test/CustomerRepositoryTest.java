package test;

import domain.Customer;
import org.junit.Before;
import org.junit.Test;

import repository.CustomerRepository;
import repository.CustomerRepositoryImpl;

public class CustomerRepositoryTest {
	
	private CustomerRepository customerRepository;
	@Before
	public void setUp(){
		this.customerRepository = new CustomerRepositoryImpl();
	}
	
	@Test
	public void testAddCustomer(){
		this.customerRepository.addCustomer(new Customer("Jane", "Doe",25));
		assert(this.customerRepository.getAllCustomers("customerInitialLoadFile").size() == 1);
	}
}

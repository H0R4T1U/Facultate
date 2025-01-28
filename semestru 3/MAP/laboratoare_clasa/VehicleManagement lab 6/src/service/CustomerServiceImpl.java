package service;

import java.util.List;

import domain.Customer;
import repository.CustomerRepository;

public class CustomerServiceImpl implements CustomerService{
	
	private CustomerRepository customerRepository;
	

	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}


	@Override
	public List<Customer> sortCustomersByNameAscending() {
		// TODO implement sorting by using java 8 features
		return getAll("customerInitialLoadFile").stream()
				.sorted()
				.toList();
	}



	@Override
	public void addCustomer(Customer customer) {
		customerRepository.addCustomer(customer);		
	}



	@Override
	public List<Customer> getAll(String property) {
		return customerRepository.getAllCustomers(property);
	}

}

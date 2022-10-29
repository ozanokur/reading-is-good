package com.getir.readingisgood.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	@Autowired
	CustomerRepository customerRepository;
    
    public Customer save(Customer customer) {
    	validateSaveRequest(customer);
    	
    	return customerRepository.save(customer);
    }
    
    private void validateSaveRequest(Customer customer) {
		if (customerRepository.existsByEmail(customer.getEmail())) {
			throw new RuntimeException("This email is already used.");
		}
	}

	public Customer findByCustomerId(Long id) {
    	return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("No customer with id:" + id + " found."));
    }
	
	
	
	
}

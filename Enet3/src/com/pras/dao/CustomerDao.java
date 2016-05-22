package com.pras.dao;

import java.util.Collection;

import com.pras.dto.CustomerDto;
import com.pras.model.Customer;

public interface CustomerDao {

	public void addCustomer(CustomerDto customer);
	public void removeCustomer(long customer);
	public void editCustomer(long id, CustomerDto cust);
	public Collection<? extends CustomerDto> getCustomers();
	public CustomerDto getCustomer(long id);
	
}

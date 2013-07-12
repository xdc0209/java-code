package com.springinaction.pizza.service;
import com.springinaction.pizza.domain.Customer;

public interface CustomerService {
  public Customer lookupCustomer(String phoneNumber) 
      throws CustomerNotFoundException;
}
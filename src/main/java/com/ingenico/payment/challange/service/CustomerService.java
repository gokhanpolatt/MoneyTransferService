package com.ingenico.payment.challange.service;

import com.ingenico.payment.challange.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAllCustomers();

    Customer findCustomerById(String id);

}

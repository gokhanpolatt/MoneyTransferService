package com.ingenico.payment.challange.repository;

import com.ingenico.payment.challange.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByCustomerId(String customerId);

    List<Customer> findByCustomerNameAndCustomerSurname(String customerName, String customerSurname);

}

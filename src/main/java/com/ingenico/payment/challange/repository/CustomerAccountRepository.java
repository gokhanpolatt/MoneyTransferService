package com.ingenico.payment.challange.repository;

import com.ingenico.payment.challange.entity.CustomerAccount;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, Long> {

    CustomerAccount findByAccountId(String accountId);

    List<CustomerAccount> findByCustomerId(String customerId);

}

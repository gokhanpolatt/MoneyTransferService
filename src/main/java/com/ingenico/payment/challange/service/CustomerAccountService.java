package com.ingenico.payment.challange.service;

import com.ingenico.payment.challange.entity.CustomerAccount;
import com.ingenico.payment.challange.model.MoneyTransferModel;

import java.util.List;

public interface CustomerAccountService {

    CustomerAccount checkBalance(String accountId) throws Exception;

    CustomerAccount addMoney(double amount, String accountId) throws Exception;

    CustomerAccount withdraw(double amount, String accountId) throws Exception;

    CustomerAccount transfer(MoneyTransferModel transferModel) throws Exception;

    List<CustomerAccount> getCustomerAccounts(String customerId) throws Exception;
}

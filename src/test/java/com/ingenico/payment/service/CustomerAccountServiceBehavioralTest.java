package com.ingenico.payment.service;

import com.ingenico.payment.challange.model.MoneyTransferModel;
import com.ingenico.payment.challange.service.CustomerAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CustomerAccountServiceBehavioralTest {

    @Mock
    CustomerAccountService customerAccountService;

    @Test
    public void checkBalance_test() throws Exception {
        //given
        String accountId = anyString();
        //when
        customerAccountService.checkBalance(accountId);
        //then
        verify(customerAccountService).checkBalance(accountId);
    }

    @Test
    public void addMoney_test() throws Exception {
        //given
        String accountId = anyString();
        double amount = anyDouble();
        //when
        customerAccountService.addMoney(amount, accountId);
        //then
        verify(customerAccountService).addMoney(amount, accountId);
    }

    @Test
    public void withdraw_test() throws Exception {
        //given
        String accountId = anyString();
        double amount = anyDouble();
        //when
        customerAccountService.withdraw(amount, accountId);
        //then
        verify(customerAccountService).withdraw(amount, accountId);
    }

    @Test
    public void transfer_test() throws Exception {
        //given
        MoneyTransferModel transferModel = new MoneyTransferModel("anyAccount", 0d, "anyAccount");
        //when
        customerAccountService.transfer(transferModel);
        //then
        verify(customerAccountService).transfer(transferModel);
    }
}

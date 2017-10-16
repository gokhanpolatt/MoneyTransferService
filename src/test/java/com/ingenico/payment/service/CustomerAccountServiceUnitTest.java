package com.ingenico.payment.service;

import com.ingenico.payment.challange.MoneyTransferServiceApplication;
import com.ingenico.payment.challange.entity.CustomerAccount;
import com.ingenico.payment.challange.exception.InvalidAccountException;
import com.ingenico.payment.challange.exception.InvalidAmountException;
import com.ingenico.payment.challange.model.MoneyTransferModel;
import com.ingenico.payment.challange.service.CustomerAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = MoneyTransferServiceApplication.class)
@ComponentScan
public class CustomerAccountServiceUnitTest {

    @Autowired
    CustomerAccountService customerAccountService;

    @Test
    public void addMoney_test() throws Exception {
        //given
        Double amount = 50d;
        String accountId = "123456789";
        //when
        // assumption : account has no money before adding
        CustomerAccount customerAccount = customerAccountService.addMoney(amount, accountId);
        //then
        Double accountBalance = customerAccount.getAccountBalance().doubleValue();
        assertEquals(amount, accountBalance);
    }

    @Test(expected = InvalidAccountException.class)
    public void shouldGive_invalidAccountException_when_accountNotFound_addMoneyTest() throws Exception {
        //given
        Double amount = 50d;
        String accountId = "wrong Id";
        //when
        CustomerAccount customerAccount = customerAccountService.addMoney(amount, accountId);
    }

    @Test(expected = InvalidAmountException.class)
    public void shouldGive_invalidAmountException_when_amountIsNegative_addMoneyTest() throws Exception {
        //given
        Double amount = -50d;
        String accountId = "123456789";
        //when
        CustomerAccount customerAccount = customerAccountService.addMoney(amount, accountId);
    }

    @Test
    public void withdraw_test() throws Exception {
        //given
        Double amount = 20d;
        String accountId = "1122334455";
        //when
        CustomerAccount customerAccount = customerAccountService.withdraw(amount, accountId);
        //then
        Double accountBalance = customerAccount.getAccountBalance().doubleValue();
        assertEquals((Double) 80d, accountBalance);
    }

    @Test(expected = InvalidAccountException.class)
    public void shouldGive_invalidAccountException_when_accountNotFound_withDrawTest() throws Exception {
        //given
        Double amount = 50d;
        String accountId = "wrong Id";
        //when
        CustomerAccount customerAccount = customerAccountService.withdraw(amount, accountId);
    }

    @Test(expected = InvalidAmountException.class)
    public void shouldGive_invalidAmountException_when_amountIsNegative_withdrawTest() throws Exception {
        //given
        Double amount = -50d;
        String accountId = "123456789";
        //when
        CustomerAccount customerAccount = customerAccountService.withdraw(amount, accountId);
    }

    @Test(expected = InvalidAmountException.class)
    public void shouldGive_invalidAmountException_when_amountIsGreaterThanBalance_withdrawTest() throws Exception {
        //given
        Double amount = 150d;
        String accountId = "123456789";
        //when
        CustomerAccount customerAccount = customerAccountService.withdraw(amount, accountId);
    }

    @Test
    public void transfer_test() throws Exception {
        //given
        Double amount = 30d;
        String fromAccountId = "5544332211";
        String toAccountId = "987654321";
        MoneyTransferModel moneyTransferModel = new MoneyTransferModel(fromAccountId, amount, toAccountId);
        //when
        CustomerAccount customerAccount = customerAccountService.transfer(moneyTransferModel);
        //then
        Double accountBalance = customerAccount.getAccountBalance().doubleValue();
        assertEquals((Double) 70d, accountBalance);
    }

    @Test(expected = InvalidAccountException.class)
    public void shouldGive_invalidAccountException_when_accountNotFound_transferTest() throws Exception {
        //given
        Double amount = 30d;
        String fromAccountId = "wrong Id";
        String toAccountId = "987654321";
        MoneyTransferModel moneyTransferModel = new MoneyTransferModel(fromAccountId, amount, toAccountId);
        //when
        CustomerAccount customerAccount = customerAccountService.transfer(moneyTransferModel);
    }

    @Test(expected = InvalidAmountException.class)
    public void shouldGive_invalidAmountException_when_amountIsNegative_transferTest() throws Exception {
        //given
        Double amount = -30d;
        String fromAccountId = "5544332211";
        String toAccountId = "987654321";
        MoneyTransferModel moneyTransferModel = new MoneyTransferModel(fromAccountId, amount, toAccountId);
        //when
        CustomerAccount customerAccount = customerAccountService.transfer(moneyTransferModel);
    }

    @Test(expected = InvalidAmountException.class)
    public void shouldGive_invalidAmountException_when_amountIsGreaterThanBalance_transferTest() throws Exception {
        //given
        Double amount = 130d;
        String fromAccountId = "5544332211";
        String toAccountId = "987654321";
        MoneyTransferModel moneyTransferModel = new MoneyTransferModel(fromAccountId, amount, toAccountId);
        //when
        CustomerAccount customerAccount = customerAccountService.transfer(moneyTransferModel);
    }

}

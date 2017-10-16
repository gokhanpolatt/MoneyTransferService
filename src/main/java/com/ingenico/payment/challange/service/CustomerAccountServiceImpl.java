package com.ingenico.payment.challange.service;

import com.ingenico.payment.challange.entity.CustomerAccount;
import com.ingenico.payment.challange.exception.InvalidAccountException;
import com.ingenico.payment.challange.exception.InvalidAmountException;
import com.ingenico.payment.challange.model.MoneyTransferModel;
import com.ingenico.payment.challange.repository.CustomerAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Transactional
    @Override
    public synchronized CustomerAccount checkBalance(String accountId) throws Exception {

        CustomerAccount foundedAccount = customerAccountRepository.findByAccountId(accountId);

        try {
            if (foundedAccount != null) {
                return foundedAccount;
            } else
                throw new InvalidAccountException("Could not found any account with given id!");

        } catch (Exception e) {

            logger.error("Error on checkBalance service", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    @Override
    public synchronized CustomerAccount addMoney(double amount, String accountId) throws Exception {

        CustomerAccount foundedAccount = customerAccountRepository.findByAccountId(accountId);

        try {

            if (foundedAccount != null) {

                if (amount > 0) {

                    foundedAccount.setAccountBalance(foundedAccount.getAccountBalance().add(new BigDecimal(amount)));
                    CustomerAccount newBalancedAccount = customerAccountRepository.save(foundedAccount);

                    logger.info("Money added :" + amount);

                    return newBalancedAccount;
                } else
                    throw new InvalidAmountException("Amount can not be 0 or negative!");
            } else
                throw new InvalidAccountException("Could not found any account with given id!");

        } catch (Exception e) {

            logger.error("Error on addMoney service", e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    @Override
    public synchronized CustomerAccount withdraw(double amount, String accountId) throws Exception {
        CustomerAccount foundedAccount = customerAccountRepository.findByAccountId(accountId);

        try {

            if (foundedAccount != null) {

                if (amount > 0) {

                    BigDecimal accountBalance = foundedAccount.getAccountBalance();
                    BigDecimal moneyToDraw = new BigDecimal(amount);

                    if (accountBalance.compareTo(moneyToDraw) >= 0) {

                        // Get money from account
                        foundedAccount.setAccountBalance(accountBalance.subtract(moneyToDraw));
                        foundedAccount = customerAccountRepository.save(foundedAccount);

                        logger.info("Money withdrawn :" + amount);

                    } else
                        throw new InvalidAmountException("Account does not have enough money to draw!");
                } else
                    throw new InvalidAmountException("Amount can not be 0 or negative!");
            } else
                throw new InvalidAccountException("Could not found any account with given id!");
        } catch (Exception e) {

            logger.error("Error on withdraw service", e.getMessage(), e);
            throw e;
        }

        return foundedAccount;
    }

    @Transactional
    @Override
    public synchronized CustomerAccount transfer(MoneyTransferModel transferModel) throws Exception {

        CustomerAccount foundedFromAccount = customerAccountRepository.findByAccountId(transferModel.getAccountFrom());

        CustomerAccount foundedToAccount = customerAccountRepository.findByAccountId(transferModel.getAccountTo());

        try {

            if (transferModel.getAmount() > 0) {

                if (foundedFromAccount != null) {

                    if (foundedToAccount != null) {

                        BigDecimal accountFromBalance = foundedFromAccount.getAccountBalance();

                        BigDecimal accountToBalance = foundedToAccount.getAccountBalance();

                        BigDecimal moneyToDraw = new BigDecimal(transferModel.getAmount());

                        if (accountFromBalance.compareTo(moneyToDraw) >= 0) {

                            // Get Money fromAccount;
                            foundedFromAccount.setAccountBalance(accountFromBalance.subtract(moneyToDraw));
                            foundedFromAccount = customerAccountRepository.save(foundedFromAccount);

                            logger.info("Money sended :" + transferModel.getAmount());

                            // Add Money toNewAccount
                            foundedToAccount.setAccountBalance(accountToBalance.add(moneyToDraw));
                            foundedToAccount = customerAccountRepository.save(foundedToAccount);

                            logger.info("Money received :" + transferModel.getAmount());

                        } else
                            throw new InvalidAmountException("Account does not have enough money to transfer!");
                    } else
                        throw new InvalidAccountException("Could not found outgoing account with given id!");
                } else
                    throw new InvalidAccountException("Could not found incoming account with given id!");
            } else
                throw new InvalidAmountException("Amount can not be 0 or negative!");
        } catch (Exception e) {

            logger.error("Error on transfer service", e.getMessage(), e);
            throw e;
        }
        return foundedFromAccount;

    }

    @Override
    public List<CustomerAccount> getCustomerAccounts(String customerId) {

        return customerAccountRepository.findByCustomerId(customerId);
    }
}
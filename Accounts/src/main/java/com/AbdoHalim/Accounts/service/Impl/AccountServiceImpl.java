package com.AbdoHalim.Accounts.service.Impl;

import com.AbdoHalim.Accounts.Constants.AccountConstants;
import com.AbdoHalim.Accounts.CustomException.CustomerAlreadyExistsException;
import com.AbdoHalim.Accounts.CustomException.ResourceNotExists;
import com.AbdoHalim.Accounts.Entity.Account;
import com.AbdoHalim.Accounts.Entity.Customer;
import com.AbdoHalim.Accounts.Mapper.AccountMapper;
import com.AbdoHalim.Accounts.Mapper.CustomerMapper;
import com.AbdoHalim.Accounts.Repository.AccountRepository;
import com.AbdoHalim.Accounts.Repository.CustomerRepository;
import com.AbdoHalim.Accounts.dto.AccountDto;
import com.AbdoHalim.Accounts.dto.CustomerDto;
import com.AbdoHalim.Accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer Already Exists with given mobile number "
                    + customerDto.getMobileNumber());
        }

        customerRepository.save(customer);
        accountRepository.save(createNewAccount(customer));

    }


    private Account createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountConstants.SAVINGS);
        newAccount.setBranchAddress(AccountConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotExists("Customer", "mobileNumber", mobileNumber));
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotExists("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        AccountDto accountDto = AccountMapper.mapToAccountsDto(account, new AccountDto());
        customerDto.setAccountDto(accountDto);


        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto != null) {
            Account account = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotExists("Account", "account number", accountDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccounts(accountDto, account);

            Customer customer = customerRepository.findById(account.getCustomerId()).orElseThrow(
                    () -> new ResourceNotExists("Customer", "Id", account.getCustomerId().toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            accountRepository.save(account);

            customerRepository.save(customer);

            return true;
        }
        return false;


    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        if (mobileNumber!=null){
            Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                    () -> new ResourceNotExists("Customer", "mobileNumber", mobileNumber)
            );
            customerRepository.delete(customer);

            return true;
        }
        return false;

    }
}

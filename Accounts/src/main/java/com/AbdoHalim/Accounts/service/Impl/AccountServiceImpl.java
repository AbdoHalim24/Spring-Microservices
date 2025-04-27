package com.AbdoHalim.Accounts.service.Impl;

import com.AbdoHalim.Accounts.Constants.AccountConstants;
import com.AbdoHalim.Accounts.CustomException.CustomerAlreadyExistsException;
import com.AbdoHalim.Accounts.Entity.Account;
import com.AbdoHalim.Accounts.Entity.Customer;
import com.AbdoHalim.Accounts.Mapper.CustomerMapper;
import com.AbdoHalim.Accounts.Repository.AccountRepository;
import com.AbdoHalim.Accounts.Repository.CustomerRepository;
import com.AbdoHalim.Accounts.dto.CustomerDto;
import com.AbdoHalim.Accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {

        Customer customer= CustomerMapper.mapToCustomer(customerDto,new Customer());
        Optional<Customer> optionalCustomer=customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()){
            throw new CustomerAlreadyExistsException("Customer Already Exists with given mobile number "
                    +customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("aref");
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
}

package com.AbdoHalim.Accounts.Repository;

import com.AbdoHalim.Accounts.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

     Optional<Customer> findByMobileNumber(String mobileNumber);

}

package com.bank.mybank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.mybank.entity.Customer;
import com.bank.mybank.entity.CustomerAccount;

@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {

	CustomerAccount findByCustomerId(Customer customer);

	Optional<CustomerAccount> findByCustomerAccountNumber(Long beneficiaryAccountNumber);

}

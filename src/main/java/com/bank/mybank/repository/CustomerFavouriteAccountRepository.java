package com.bank.mybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.mybank.entity.CustomerFavouriteAccount;

@Repository
public interface CustomerFavouriteAccountRepository extends JpaRepository<CustomerFavouriteAccount, Long> {

}

package com.bank.mybank.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.RequestDto;
import com.bank.mybank.dto.ResponseDto;
import com.bank.mybank.entity.Customer;
import com.bank.mybank.entity.CustomerAccount;
import com.bank.mybank.entity.CustomerFavouriteAccount;
import com.bank.mybank.exception.CustomerAccountNotFoundException;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.exception.NoAccountListException;
import com.bank.mybank.repository.CustomerAccountRepository;
import com.bank.mybank.repository.CustomerFavouriteAccountRepository;
import com.bank.mybank.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerFavouriteAccountRepository customerFavouriteAccountRepository;
	
	@Autowired
	CustomerAccountRepository customerAccountRepository;
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Optional<ResponseDto> addFavourite(RequestDto addFavouriteRequestDto) throws NoAccountListException,CustomerAccountNotFoundException, GeneralException {
		Customer customer=customerRepository.findByCustomerId(addFavouriteRequestDto.getCustomerId());
		CustomerAccount customerAccount=customerAccountRepository.findByCustomerId(customer);
		Optional<CustomerAccount> customerBeneficiaryAccount=customerAccountRepository.findByCustomerAccountNumber(addFavouriteRequestDto.getBeneficiaryAccountNumber());
		if(!customerBeneficiaryAccount.isPresent()) {
			throw new CustomerAccountNotFoundException("No customer account found");
		}
		
		Optional<CustomerFavouriteAccount> customerFavouriteAccountDetail=customerFavouriteAccountRepository.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount,customerBeneficiaryAccount.get());
		if(customerFavouriteAccountDetail.isPresent()) {
			throw new GeneralException("Beneficiary account already exists in favourite list");
		}
		List<CustomerFavouriteAccount> listOffavouriteAccounts=customerFavouriteAccountRepository.findAllByCustomerAccountNumber(customerAccount);
		if(listOffavouriteAccounts.size()<10) {
			CustomerFavouriteAccount customerFavouriteAccount=new CustomerFavouriteAccount();
			customerFavouriteAccount.setCustomerAccountNumber(customerAccount);
			customerFavouriteAccount.setBeneficiaryAccountName(addFavouriteRequestDto.getBeneficiaryAccountName());
			customerFavouriteAccount.setBeneficiaryAccountNumber(customerBeneficiaryAccount.get());
			customerFavouriteAccount.setCustomerFavouriteAccountStatus(ApplicationConstants.STATUS_OF_ACTIVE_ACCOUNT);
			customerFavouriteAccount.setIfscCode(addFavouriteRequestDto.getIfscCode());
			customerFavouriteAccount.setAccountAddedOn(LocalDateTime.now());
			customerFavouriteAccountRepository.save(customerFavouriteAccount);
			ResponseDto addFavouriteResponseDto=new ResponseDto();
			return Optional.of(addFavouriteResponseDto);
		}
		throw new NoAccountListException ("Please delete one of your favourite accounts to add a new beneficiary accounts in your list");
	}

	@Override
	public Optional<ResponseDto> deleteFavourite(RequestDto deleteRequestDto) {
		Customer customer=customerRepository.findByCustomerId(deleteRequestDto.getCustomerId());
		CustomerAccount customerAccount=customerAccountRepository.findByCustomerId(customer);
		Optional<CustomerAccount> customerBeneficiaryAccount=customerAccountRepository.findByCustomerAccountNumber(deleteRequestDto.getBeneficiaryAccountNumber());
		if(customerBeneficiaryAccount.isPresent()) {
		Optional<CustomerFavouriteAccount> customerFavouriteAccountDetail=customerFavouriteAccountRepository.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount,customerBeneficiaryAccount.get());
		if(customerFavouriteAccountDetail.isPresent()) {
		customerFavouriteAccountDetail.get().setCustomerFavouriteAccountStatus(ApplicationConstants.STATUS_OF_INACTIVE_CODE);
		customerFavouriteAccountRepository.save(customerFavouriteAccountDetail.get());
		ResponseDto addFavouriteResponseDto=new ResponseDto();
		return Optional.of(addFavouriteResponseDto);
		}
		}
		return Optional.ofNullable(null);
	}
	

}

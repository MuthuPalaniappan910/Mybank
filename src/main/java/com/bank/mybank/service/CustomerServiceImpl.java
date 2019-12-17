package com.bank.mybank.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.AddFavouriteRequestDto;
import com.bank.mybank.dto.AddFavouriteResponseDto;
import com.bank.mybank.dto.CustomerFavouriteAccountResponse;
import com.bank.mybank.dto.FavouriteBeneficiariesResponseDto;
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
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerFavouriteAccountRepository customerFavouriteAccountRepository;

	@Autowired
	CustomerAccountRepository customerAccountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Optional<AddFavouriteResponseDto> addFavourite(AddFavouriteRequestDto addFavouriteRequestDto)
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Customer customer = customerRepository.findByCustomerId(addFavouriteRequestDto.getCustomerId());
		CustomerAccount customerAccount = customerAccountRepository.findByCustomerId(customer);
		Optional<CustomerAccount> customerBeneficiaryAccount = customerAccountRepository
				.findByCustomerAccountNumber(addFavouriteRequestDto.getBeneficiaryAccountNumber());
		if (!customerBeneficiaryAccount.isPresent()) {
			throw new CustomerAccountNotFoundException("No customer account found");
		}

		Optional<CustomerFavouriteAccount> customerFavouriteAccountDetail = customerFavouriteAccountRepository
				.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount,
						customerBeneficiaryAccount.get());
		if (customerFavouriteAccountDetail.isPresent() && addFavouriteRequestDto.getActionType().equalsIgnoreCase("edit")) {

			customerFavouriteAccountDetail.get()
					.setBeneficiaryAccountName(addFavouriteRequestDto.getBeneficiaryAccountName());
			customerFavouriteAccountDetail.get().setIfscCode(addFavouriteRequestDto.getIfscCode());
			customerFavouriteAccountDetail.get().setAccountAddedOn(LocalDateTime.now());
			customerFavouriteAccountRepository.save(customerFavouriteAccountDetail.get());
		} else {
			throw new GeneralException("Beneficiary account already exists in favourite list");
		}

		if (!addFavouriteRequestDto.getActionType().equalsIgnoreCase("edit")) {
			List<CustomerFavouriteAccount> listOffavouriteAccounts = customerFavouriteAccountRepository
					.findAllByCustomerAccountNumber(customerAccount);
			if (listOffavouriteAccounts.size() < 10) {
				CustomerFavouriteAccount customerFavouriteAccount = new CustomerFavouriteAccount();
				customerFavouriteAccount.setCustomerAccountNumber(customerAccount);
				customerFavouriteAccount.setBeneficiaryAccountName(addFavouriteRequestDto.getBeneficiaryAccountName());
				customerFavouriteAccount.setBeneficiaryAccountNumber(customerBeneficiaryAccount.get());
				customerFavouriteAccount
						.setCustomerFavouriteAccountStatus(ApplicationConstants.STATUS_OF_ACTIVE_ACCOUNT);
				customerFavouriteAccount.setIfscCode(addFavouriteRequestDto.getIfscCode());
				customerFavouriteAccount.setAccountAddedOn(LocalDateTime.now());
				customerFavouriteAccountRepository.save(customerFavouriteAccount);

			} else {
				throw new NoAccountListException(
						"Please delete one of your favourite accounts to add a new beneficiary accounts in your list");
			}
		}
		AddFavouriteResponseDto addFavouriteResponseDto = new AddFavouriteResponseDto();
		return Optional.of(addFavouriteResponseDto);
	}

	@Override
	public Optional<FavouriteBeneficiariesResponseDto> viewFavouriteAccounts(Long customerId) throws GeneralException {
		FavouriteBeneficiariesResponseDto favouriteBeneficiariesResponseDto = new FavouriteBeneficiariesResponseDto();

		List<CustomerFavouriteAccountResponse> customerFavouriteAccountResponseList = new ArrayList<>();

		Optional<Customer> customer = customerRepository.findById(customerId);

		if (!customer.isPresent()) {
			throw new GeneralException(ApplicationConstants.INVALID_CUSTOMER);
		}

		Optional<List<CustomerAccount>> customerAccountList = customerAccountRepository.findByCustomerId(customer);
		if (!customerAccountList.isPresent()) {
			throw new GeneralException(ApplicationConstants.INVALID_ACCOUNT_NUMBER);
		}
		for (CustomerAccount finalListCustomer : customerAccountList.get()) {

			Optional<List<CustomerFavouriteAccount>> customerFavouriteAccountOptional = customerFavouriteAccountRepository
					.findByCustomerAccountNumberAndCustomerFavouriteAccountStatusOrderByAccountAddedOnDesc(
							finalListCustomer, "active");
			if (!customerFavouriteAccountOptional.isPresent()) {
				throw new GeneralException("Error in CustomerFavouriteAccount");
			}
			customerFavouriteAccountOptional.get().forEach(customerFavouriteAccountIndex -> {
				CustomerFavouriteAccountResponse customerFavouriteAccountResponse = new CustomerFavouriteAccountResponse();

				customerFavouriteAccountResponse
						.setBeneficiaryAccountName(customerFavouriteAccountIndex.getBeneficiaryAccountName());
				customerFavouriteAccountResponse.setIfscCode(customerFavouriteAccountIndex.getIfscCode());
				customerFavouriteAccountResponse.setBeneficiaryAccountNumber(
						customerFavouriteAccountIndex.getBeneficiaryAccountNumber().getCustomerAccountNumber());
				customerFavouriteAccountResponseList.add(customerFavouriteAccountResponse);

			});
		}

		favouriteBeneficiariesResponseDto.setFavouritesList(customerFavouriteAccountResponseList);
		return Optional.of(favouriteBeneficiariesResponseDto);
	}

}

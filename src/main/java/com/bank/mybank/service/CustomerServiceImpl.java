package com.bank.mybank.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.AddFavouriteRequestDto;
import com.bank.mybank.dto.AddFavouriteResponseDto;
import com.bank.mybank.dto.BeneficiaryResponseDto;
import com.bank.mybank.entity.Customer;
import com.bank.mybank.entity.CustomerAccount;
import com.bank.mybank.entity.CustomerFavouriteAccount;
import com.bank.mybank.exception.BeneficiaryNotFoundException;
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
		if (customerFavouriteAccountDetail.isPresent()) {
			throw new GeneralException("Beneficiary account already exists in favourite list");
		}
		List<CustomerFavouriteAccount> listOffavouriteAccounts = customerFavouriteAccountRepository
				.findAllByCustomerAccountNumber(customerAccount);
		if (listOffavouriteAccounts.size() < 10) {
			CustomerFavouriteAccount customerFavouriteAccount = new CustomerFavouriteAccount();
			customerFavouriteAccount.setCustomerAccountNumber(customerAccount);
			customerFavouriteAccount.setBeneficiaryAccountName(addFavouriteRequestDto.getBeneficiaryAccountName());
			customerFavouriteAccount.setBeneficiaryAccountNumber(customerBeneficiaryAccount.get());
			customerFavouriteAccount.setCustomerFavouriteAccountStatus(ApplicationConstants.STATUS_OF_ACTIVE_ACCOUNT);
			customerFavouriteAccount.setIfscCode(addFavouriteRequestDto.getIfscCode());
			customerFavouriteAccount.setAccountAddedOn(LocalDateTime.now());
			customerFavouriteAccountRepository.save(customerFavouriteAccount);
			AddFavouriteResponseDto addFavouriteResponseDto = new AddFavouriteResponseDto();
			return Optional.of(addFavouriteResponseDto);
		}
		throw new NoAccountListException(
				"Please delete one of your favourite accounts to add a new beneficiary accounts in your list");
	}

	/**
	 * 
	 * Method displays the details of the particular beneficiary that includes
	 * account number,name and ifsc code
	 * 
	 * @author Muthu
	 * 
	 * @param customerId
	 * @param beneficiaryAccountNumber
	 * @return
	 * @throws BeneficiaryNotFoundException
	 * @throws CustomerAccountNotFoundException
	 */

	@Override
	public Optional<BeneficiaryResponseDto> getBeneficiaryDetails(Long customerId, Long beneficiaryAccountNumber)
			throws BeneficiaryNotFoundException, CustomerAccountNotFoundException {
		Customer customer = customerRepository.findByCustomerId(customerId);
		if (customer != null) {
			BeneficiaryResponseDto beneficiaryResponseDto = new BeneficiaryResponseDto();
			CustomerAccount customerAccountDetails = customerAccountRepository.findByCustomerId(customer);
			Optional<CustomerAccount> beneficiaryDetails = customerAccountRepository
					.findByCustomerAccountNumber(beneficiaryAccountNumber);
			if (beneficiaryDetails.isPresent()) {
				Optional<CustomerFavouriteAccount> favouriteAccount = customerFavouriteAccountRepository
						.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccountDetails,
								beneficiaryDetails.get());
				if (favouriteAccount.isPresent()) {
					beneficiaryResponseDto
							.setBeneficiaryAccountName(favouriteAccount.get().getBeneficiaryAccountName());
					beneficiaryResponseDto.setBeneficiaryAccountNumber(beneficiaryAccountNumber);
					beneficiaryResponseDto.setIfscCode(favouriteAccount.get().getIfscCode());
					beneficiaryResponseDto.setStatusCode(ApplicationConstants.SUCCESS_CODE);
					beneficiaryResponseDto.setMessage(ApplicationConstants.BENEFICIARY_VALID);
					return Optional.of(beneficiaryResponseDto);
				}
				throw new BeneficiaryNotFoundException(ApplicationConstants.ACCOUNT_BENEFICIARY_MISMATCH);
			}
			throw new BeneficiaryNotFoundException(ApplicationConstants.BENEFICIARY_INVALID);
		}
		throw new CustomerAccountNotFoundException("No customer account found");
	}

}

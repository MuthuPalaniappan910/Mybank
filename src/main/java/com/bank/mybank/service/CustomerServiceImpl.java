package com.bank.mybank.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.BeneficiaryResponseDto;
import com.bank.mybank.dto.CustomerFavouriteAccountResponse;
import com.bank.mybank.dto.FavouriteBeneficiariesResponseDto;
import com.bank.mybank.dto.RequestDto;
import com.bank.mybank.dto.ResponseDto;
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

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * This class is used to provide implementations for all the end user related operations
 *
 */
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerFavouriteAccountRepository customerFavouriteAccountRepository;

	@Autowired
	CustomerAccountRepository customerAccountRepository;

	@Autowired
	CustomerRepository customerRepository;


	@Override
	/**
	 * This method is used to edit the details of the added favourite account
	 * 
	 * @author Chethana
	 * @param editFavouriteRequestDto
	 * @return ResponseDto returns success/failure operation
	 * @throws NoAccountListException
	 * @throws CustomerAccountNotFoundException
	 * @throws GeneralException
	 */

	public Optional<ResponseDto> editFavourite(RequestDto editFavouriteRequestDto)
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		log.info("entering into edit favourite payee");
		Customer customer = customerRepository.findByCustomerId(editFavouriteRequestDto.getCustomerId());
		CustomerAccount customerAccount = customerAccountRepository.findByCustomerId(customer);
		Optional<CustomerAccount> customerBeneficiaryAccount = customerAccountRepository
				.findByCustomerAccountNumber(editFavouriteRequestDto.getBeneficiaryAccountNumber());
		if (!customerBeneficiaryAccount.isPresent()) {
			log.error("Exception occured in editFavourite");
			throw new GeneralException(ApplicationConstants.INVALID_ACCOUNT_NUMBER);
		}

		Optional<CustomerFavouriteAccount> customerFavouriteAccountDetail = customerFavouriteAccountRepository
				.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount,
						customerBeneficiaryAccount.get());
		if (customerFavouriteAccountDetail.isPresent()) {

			customerFavouriteAccountDetail.get()
					.setBeneficiaryAccountName(editFavouriteRequestDto.getBeneficiaryAccountName());
			customerFavouriteAccountDetail.get().setIfscCode(editFavouriteRequestDto.getIfscCode());
			customerFavouriteAccountDetail.get().setAccountAddedOn(LocalDateTime.now());
			customerFavouriteAccountRepository.save(customerFavouriteAccountDetail.get());
		}
		ResponseDto addFavouriteResponseDto = new ResponseDto();
		return Optional.of(addFavouriteResponseDto);
	}

	/**
	 * This method is used to add the details of favourite account
	 * 
	 * @author Bindu
	 * @param addFavouriteRequestDto
	 * @return ResponseDto
	 * @throws NoAccountListException
	 * @throws CustomerAccountNotFoundException
	 * @throws GeneralException
	 */
	@Override
	public Optional<ResponseDto> addFavourite(RequestDto addFavouriteRequestDto)
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		log.info("entering into add favourite payee");
		Customer customer = customerRepository.findByCustomerId(addFavouriteRequestDto.getCustomerId());
		CustomerAccount customerAccount = customerAccountRepository.findByCustomerId(customer);

		Optional<CustomerAccount> customerBeneficiaryAccount = customerAccountRepository
				.findByCustomerAccountNumber(addFavouriteRequestDto.getBeneficiaryAccountNumber());
		if (!customerBeneficiaryAccount.isPresent()) {
			log.error("Exception occured in addFavourite");
			throw new GeneralException(ApplicationConstants.BENEFICIARY_ALREADY_EXISTS);
		}

		Optional<CustomerFavouriteAccount> customerFavouriteAccountDetail = customerFavouriteAccountRepository
				.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount,
						customerBeneficiaryAccount.get());
		Long existingAccount = customerAccount.getCustomerAccountNumber();
		Long givenAccount = addFavouriteRequestDto.getBeneficiaryAccountNumber();
		if (existingAccount.equals(givenAccount)) {
			log.error("Exception occured in addFavourite:Cannot add your account as a beneficiary account");
			throw new GeneralException(ApplicationConstants.BENEFECIARY_NOT_FOUND);
		}
		if (customerFavouriteAccountDetail.isPresent()) {
			log.error("Exception occured in addFavourite:"+ApplicationConstants.BENEFICIARY_ALREADY_EXISTS);
			throw new GeneralException(ApplicationConstants.BENEFICIARY_ALREADY_EXISTS);
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
			ResponseDto addFavouriteResponseDto = new ResponseDto();
			return Optional.of(addFavouriteResponseDto);
		}
		log.error("Exception occured in addFavourite:"+ApplicationConstants.BENEFICIARY_LIST_EXCEEDS);
		throw new NoAccountListException(ApplicationConstants.BENEFICIARY_LIST_EXCEEDS);
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
	 * @return BeneficiaryResponseDto
	 * @throws BeneficiaryNotFoundException
	 * @throws CustomerAccountNotFoundException
	 */

	@Override
	public Optional<BeneficiaryResponseDto> getBeneficiaryDetails(Long customerId, Long beneficiaryAccountNumber)
			throws BeneficiaryNotFoundException, CustomerAccountNotFoundException {
		log.info("entering into get beneficiaryDetails");
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
				log.error("Exception occured in getBeneficiaryDetails:"+ApplicationConstants.ACCOUNT_BENEFICIARY_MISMATCH);
				throw new BeneficiaryNotFoundException(ApplicationConstants.ACCOUNT_BENEFICIARY_MISMATCH);
			}
			log.error("Exception occured in getBeneficiaryDetails:"+ApplicationConstants.BENEFICIARY_INVALID);
			throw new BeneficiaryNotFoundException(ApplicationConstants.BENEFICIARY_INVALID);
		}
		log.error("Exception occured in getBeneficiaryDetails:"+ApplicationConstants.CUSTOMER_NOT_FOUND);
		throw new CustomerAccountNotFoundException(ApplicationConstants.CUSTOMER_NOT_FOUND);
	}

	/**
	 * This method is used to remove an account from the favourite list and account status is made inactive. 
	 * 
	 * @author Bindu
	 * @param deleteRequestDto
	 * @return ResponseDto
	 */
	@Override
	public Optional<ResponseDto> deleteFavourite(RequestDto deleteRequestDto) {
		log.info("entering into delete favourite ");
		Customer customer = customerRepository.findByCustomerId(deleteRequestDto.getCustomerId());
		CustomerAccount customerAccount = customerAccountRepository.findByCustomerId(customer);
		Optional<CustomerAccount> customerBeneficiaryAccount = customerAccountRepository
				.findByCustomerAccountNumber(deleteRequestDto.getBeneficiaryAccountNumber());
		if (customerBeneficiaryAccount.isPresent()) {
			Optional<CustomerFavouriteAccount> customerFavouriteAccountDetail = customerFavouriteAccountRepository
					.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount,
							customerBeneficiaryAccount.get());
			if (customerFavouriteAccountDetail.isPresent()) {
				customerFavouriteAccountDetail.get()
						.setCustomerFavouriteAccountStatus(ApplicationConstants.STATUS_OF_INACTIVE_CODE);
				customerFavouriteAccountRepository.save(customerFavouriteAccountDetail.get());

			}
		}
		ResponseDto addDeleteResponseDto = new ResponseDto();
		return Optional.of(addDeleteResponseDto);
	}

	/**
	 * This method is used to view favourite active accounts
	 * @author Mahesh
	 * @param customerId
	 * @return FavouriteBeneficiariesResponseDto
	 * @throws GeneralException
	 */
	public Optional<FavouriteBeneficiariesResponseDto> viewFavouriteAccounts(Long customerId) throws GeneralException {
		log.info("entering into view favourite account");
		FavouriteBeneficiariesResponseDto favouriteBeneficiariesResponseDto = new FavouriteBeneficiariesResponseDto();

		List<CustomerFavouriteAccountResponse> customerFavouriteAccountResponseList = new ArrayList<>();

		Optional<Customer> customer = customerRepository.findById(customerId);

		if (!customer.isPresent()) {
			log.error("Exception occured in getBeneficiaryDetails:"+ApplicationConstants.INVALID_CUSTOMER);
			throw new GeneralException(ApplicationConstants.INVALID_CUSTOMER);
		}

		Optional<List<CustomerAccount>> customerAccountList = customerAccountRepository.findByCustomerId(customer);
		if (!customerAccountList.isPresent()) {
			log.error("Exception occured in getBeneficiaryDetails:"+ApplicationConstants.INVALID_ACCOUNT_NUMBER);
			throw new GeneralException(ApplicationConstants.INVALID_ACCOUNT_NUMBER);
		}
		if (customerAccountList.get().isEmpty()) {
			log.error("Exception occured in getBeneficiaryDetails:"+ApplicationConstants.NO_FAVOURITES);
			throw new GeneralException(ApplicationConstants.NO_FAVOURITES);
		}
		for (CustomerAccount finalListCustomer : customerAccountList.get()) {

			Optional<List<CustomerFavouriteAccount>> customerFavouriteAccountOptional = customerFavouriteAccountRepository
					.findByCustomerAccountNumberAndCustomerFavouriteAccountStatusOrderByAccountAddedOnDesc(
							finalListCustomer, "active");
			if(!customerFavouriteAccountOptional.isPresent()) {
				favouriteBeneficiariesResponseDto.setFavouritesList(customerFavouriteAccountResponseList);
				return Optional.of(favouriteBeneficiariesResponseDto);
			
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

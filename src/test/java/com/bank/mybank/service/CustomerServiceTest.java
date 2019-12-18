package com.bank.mybank.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.BeneficiaryResponseDto;
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

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest {

	@InjectMocks
	CustomerServiceImpl customerServiceImpl;

	@Mock
	CustomerFavouriteAccountRepository customerFavouriteAccountRepository;

	@Mock
	CustomerAccountRepository customerAccountRepository;

	@Mock
	CustomerRepository customerRepository;

	RequestDto addFavouriteRequestDto = null;
	ResponseDto addFavouriteResponseDto = null;
	Customer customer = null;
	Customer customer1 = null;
	CustomerAccount customerAccount = null;
	CustomerAccount customerAccountBeneficiary = null;
	CustomerFavouriteAccount customerFavouriteAccount = null;
	List<CustomerFavouriteAccount> listOffavouriteAccounts = null;

	@Before
	public void before() {
		addFavouriteRequestDto = new RequestDto();
		addFavouriteRequestDto.setBeneficiaryAccountName("bindu");
		addFavouriteRequestDto.setBeneficiaryAccountNumber(54L);
		addFavouriteRequestDto.setCustomerId(1L);
		addFavouriteRequestDto.setIfscCode("hdfc100");

		addFavouriteResponseDto = new ResponseDto();

		customer = new Customer();
		customer.setCustomerId(1L);
		customer.setCustomerName("bindu");
		customer.setEmail("bindu@gmail.com");
		customer.setPassword("5146");
		customer.setPhoneNumber(726387L);

		customer1 = new Customer();
		customer1.setCustomerId(2L);
		customer1.setCustomerName("bindu");
		customer1.setEmail("bindu@gmail.com");
		customer1.setPassword("5146");
		customer1.setPhoneNumber(726387L);

		customerAccount = new CustomerAccount();
		customerAccount.setAccountStatus("active");
		customerAccount.setAccoutnType("savings");
		customerAccount.setCustomerAccountNumber(11L);
		customerAccount.setCustomerId(customer);

		customerAccountBeneficiary = new CustomerAccount();
		customerAccountBeneficiary.setAccountStatus("active");
		customerAccountBeneficiary.setAccoutnType("savings");
		customerAccountBeneficiary.setCustomerAccountNumber(54L);
		customerAccountBeneficiary.setCustomerId(customer1);

		customerFavouriteAccount = new CustomerFavouriteAccount();
		customerFavouriteAccount.setCustomerAccountNumber(customerAccount);
		customerFavouriteAccount.setBeneficiaryAccountName(addFavouriteRequestDto.getBeneficiaryAccountName());
		customerFavouriteAccount.setBeneficiaryAccountNumber(customerAccountBeneficiary);
		customerFavouriteAccount.setCustomerFavouriteAccountStatus(ApplicationConstants.STATUS_OF_ACTIVE_ACCOUNT);
		customerFavouriteAccount.setIfscCode(addFavouriteRequestDto.getIfscCode());
		customerFavouriteAccount.setAccountAddedOn(LocalDateTime.now());

		listOffavouriteAccounts = new ArrayList<>();
		listOffavouriteAccounts.add(customerFavouriteAccount);

	}

	@Test(expected = CustomerAccountNotFoundException.class)
	public void testGetBeneficiaryDetailsCustomerNegative()
			throws BeneficiaryNotFoundException, CustomerAccountNotFoundException {
		Mockito.when(customerRepository.findByCustomerId(1L)).thenReturn(customer);
		Optional<BeneficiaryResponseDto> expected = customerServiceImpl.getBeneficiaryDetails(2L, 100L);
		String response = ApplicationConstants.CUSTOMER_NOT_FOUND;
		assertEquals(response, expected);
	}

	@Test(expected = BeneficiaryNotFoundException.class)
	public void testGetBeneficiaryDetailsAccountNegative()
			throws BeneficiaryNotFoundException, CustomerAccountNotFoundException {
		Mockito.when(customerRepository.findByCustomerId(1L)).thenReturn(customer);
		Mockito.when(customerAccountRepository.findByCustomerAccountNumber(12L)).thenReturn(Optional.ofNullable(null));
		Optional<BeneficiaryResponseDto> expected = customerServiceImpl.getBeneficiaryDetails(1L, 100L);
		String response = ApplicationConstants.BENEFICIARY_INVALID;
		assertEquals(response, expected);
	}

	@Test(expected = BeneficiaryNotFoundException.class)
	public void testGetBeneficiaryDetailsBeneficiaryAccountNegative()
			throws BeneficiaryNotFoundException, CustomerAccountNotFoundException {
		Mockito.when(customerRepository.findByCustomerId(1L)).thenReturn(customer);
		Mockito.when(customerAccountRepository.findByCustomerId(customer)).thenReturn(customerAccount);
		Mockito.when(customerAccountRepository.findByCustomerAccountNumber(12L))
				.thenReturn(Optional.of(customerAccount));
		Mockito.when(customerFavouriteAccountRepository
				.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount, customerAccount))
				.thenReturn(Optional.ofNullable(null));
		Optional<BeneficiaryResponseDto> expected = customerServiceImpl.getBeneficiaryDetails(1L, 12L);
		String response = ApplicationConstants.ACCOUNT_BENEFICIARY_MISMATCH;
		assertEquals(response, expected);
	}

	@Test
	public void testGetBeneficiaryDetailsSuccess()
			throws BeneficiaryNotFoundException, CustomerAccountNotFoundException {
		Mockito.when(customerRepository.findByCustomerId(1L)).thenReturn(customer);
		Mockito.when(customerAccountRepository.findByCustomerId(customer)).thenReturn(customerAccount);
		Mockito.when(customerAccountRepository.findByCustomerAccountNumber(12L))
				.thenReturn(Optional.of(customerAccount));
		Mockito.when(customerFavouriteAccountRepository
				.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount, customerAccount))
				.thenReturn(Optional.of(customerFavouriteAccount));
		Optional<BeneficiaryResponseDto> expected = customerServiceImpl.getBeneficiaryDetails(1L, 12L);
		assertEquals(true, expected.isPresent());
	}
	
	@Test
	public void testAddFavouriteForPositive()
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Mockito.when(customerRepository.findByCustomerId(addFavouriteRequestDto.getCustomerId())).thenReturn(customer);
		Mockito.when(customerAccountRepository.findByCustomerId(customer)).thenReturn(customerAccount);
		Mockito.when(customerAccountRepository
				.findByCustomerAccountNumber(addFavouriteRequestDto.getBeneficiaryAccountNumber()))
				.thenReturn(Optional.of(customerAccountBeneficiary));
		Optional<CustomerFavouriteAccount> customerFavouriteAccountDetails = Optional.ofNullable(null);
		Mockito.when(customerFavouriteAccountRepository
				.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount, customerAccountBeneficiary))
				.thenReturn(customerFavouriteAccountDetails);
		Mockito.when(customerFavouriteAccountRepository.findAllByCustomerAccountNumber(customerAccount))
				.thenReturn(listOffavouriteAccounts);
		Optional<ResponseDto> addFavouriteResponseDto = customerServiceImpl
				.addFavourite(addFavouriteRequestDto);
		assertNotNull(addFavouriteResponseDto);
	}

	@Test(expected = GeneralException.class)
	public void testAddFavouriteForNegative1()
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Mockito.when(customerRepository.findByCustomerId(addFavouriteRequestDto.getCustomerId())).thenReturn(customer);
		Mockito.when(customerAccountRepository.findByCustomerId(customer)).thenReturn(customerAccount);
		Optional<CustomerAccount> customerDetails = Optional.ofNullable(null);
		Mockito.when(customerAccountRepository
				.findByCustomerAccountNumber(addFavouriteRequestDto.getBeneficiaryAccountNumber()))
				.thenReturn(customerDetails);
		customerServiceImpl.addFavourite(addFavouriteRequestDto);
	}

	@Test(expected = GeneralException.class)
	public void testAddFavouriteForNegative2()
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Mockito.when(customerRepository.findByCustomerId(addFavouriteRequestDto.getCustomerId())).thenReturn(customer);
		Mockito.when(customerAccountRepository.findByCustomerId(customer)).thenReturn(customerAccount);
		Mockito.when(customerAccountRepository
				.findByCustomerAccountNumber(addFavouriteRequestDto.getBeneficiaryAccountNumber()))
				.thenReturn(Optional.of(customerAccountBeneficiary));
		Mockito.when(customerFavouriteAccountRepository
				.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount, customerAccountBeneficiary))
				.thenReturn(Optional.of(customerFavouriteAccount));
		customerServiceImpl.addFavourite(addFavouriteRequestDto);
	}

	@Test(expected = NoAccountListException.class)
	public void testAddFavouriteForNegative3()
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Mockito.when(customerRepository.findByCustomerId(addFavouriteRequestDto.getCustomerId())).thenReturn(customer);
		Mockito.when(customerAccountRepository.findByCustomerId(customer)).thenReturn(customerAccount);
		Mockito.when(customerAccountRepository
				.findByCustomerAccountNumber(addFavouriteRequestDto.getBeneficiaryAccountNumber()))
				.thenReturn(Optional.of(customerAccountBeneficiary));
		Optional<CustomerFavouriteAccount> customerFavouriteAccountDetails = Optional.ofNullable(null);
		Mockito.when(customerFavouriteAccountRepository
				.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount, customerAccountBeneficiary))
				.thenReturn(customerFavouriteAccountDetails);
		listOffavouriteAccounts.add(customerFavouriteAccount);
		listOffavouriteAccounts.add(customerFavouriteAccount);
		listOffavouriteAccounts.add(customerFavouriteAccount);
		listOffavouriteAccounts.add(customerFavouriteAccount);
		listOffavouriteAccounts.add(customerFavouriteAccount);
		listOffavouriteAccounts.add(customerFavouriteAccount);
		listOffavouriteAccounts.add(customerFavouriteAccount);
		listOffavouriteAccounts.add(customerFavouriteAccount);
		listOffavouriteAccounts.add(customerFavouriteAccount);
		listOffavouriteAccounts.add(customerFavouriteAccount);
		listOffavouriteAccounts.add(customerFavouriteAccount);
		listOffavouriteAccounts.add(customerFavouriteAccount);

		Mockito.when(customerFavouriteAccountRepository.findAllByCustomerAccountNumber(customerAccount))
				.thenReturn(listOffavouriteAccounts);
		customerServiceImpl.addFavourite(addFavouriteRequestDto);
	}
	
	@Test(expected = GeneralException.class)
	public void testAddFavouriteForNegative4()
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Mockito.when(customerRepository.findByCustomerId(addFavouriteRequestDto.getCustomerId())).thenReturn(customer);
		Mockito.when(customerAccountRepository.findByCustomerId(customer)).thenReturn(customerAccount);
		Mockito.when(customerAccountRepository
				.findByCustomerAccountNumber(addFavouriteRequestDto.getBeneficiaryAccountNumber()))
				.thenReturn(Optional.of(customerAccountBeneficiary));
		Optional<CustomerFavouriteAccount> customerFavouriteAccountDetails = Optional.ofNullable(null);
		Mockito.when(customerFavouriteAccountRepository
				.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount, customerAccountBeneficiary))
				.thenReturn(customerFavouriteAccountDetails);
		addFavouriteRequestDto.setBeneficiaryAccountNumber(11L);
		customerServiceImpl.addFavourite(addFavouriteRequestDto);
		
	}
	
	@Test
	public void testDeleteFavouriteForPositive() {
		Mockito.when(customerRepository.findByCustomerId(addFavouriteRequestDto.getCustomerId())).thenReturn(customer);
		Mockito.when(customerAccountRepository.findByCustomerId(customer)).thenReturn(customerAccount);
		Mockito.when(customerAccountRepository
				.findByCustomerAccountNumber(addFavouriteRequestDto.getBeneficiaryAccountNumber()))
				.thenReturn(Optional.of(customerAccountBeneficiary));
		Mockito.when(customerFavouriteAccountRepository
				.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount, customerAccountBeneficiary))
				.thenReturn(Optional.of(customerFavouriteAccount));
		Optional<ResponseDto> addDeleteResponseDto = customerServiceImpl
				.deleteFavourite(addFavouriteRequestDto);
		assertNotNull(addDeleteResponseDto);
	}
	
	@Test
	public void testDeleteFavouriteForNegativeCustomer(){
		Mockito.when(customerRepository.findByCustomerId(addFavouriteRequestDto.getCustomerId())).thenReturn(customer);
		Mockito.when(customerAccountRepository.findByCustomerId(customer)).thenReturn(customerAccount);
		Optional<CustomerAccount> customerDetails = Optional.ofNullable(null);
		Mockito.when(customerAccountRepository
				.findByCustomerAccountNumber(addFavouriteRequestDto.getBeneficiaryAccountNumber()))
				.thenReturn(customerDetails);
		Optional<ResponseDto> addDeleteResponseDto = customerServiceImpl
				.deleteFavourite(addFavouriteRequestDto);
		assertNotNull(addDeleteResponseDto);
	}
	
	@Test
	public void testDeleteFavouriteForNegativeCustomerAccount(){
		Mockito.when(customerRepository.findByCustomerId(addFavouriteRequestDto.getCustomerId())).thenReturn(customer);
		Mockito.when(customerAccountRepository.findByCustomerId(customer)).thenReturn(customerAccount);
		Mockito.when(customerAccountRepository
				.findByCustomerAccountNumber(addFavouriteRequestDto.getBeneficiaryAccountNumber()))
				.thenReturn(Optional.of(customerAccountBeneficiary));
		Optional<CustomerFavouriteAccount> customerDetails = Optional.ofNullable(null);
		Mockito.when(customerFavouriteAccountRepository
				.findByCustomerAccountNumberAndBeneficiaryAccountNumber(customerAccount, customerAccountBeneficiary))
				.thenReturn(customerDetails);
		Optional<ResponseDto> addDeleteResponseDto = customerServiceImpl
				.deleteFavourite(addFavouriteRequestDto);
		assertNotNull(addDeleteResponseDto);
	}


}

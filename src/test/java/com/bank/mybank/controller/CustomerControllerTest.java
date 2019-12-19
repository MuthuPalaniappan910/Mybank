package com.bank.mybank.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.mybank.dto.BeneficiaryResponseDto;
import com.bank.mybank.dto.FavouriteBeneficiariesResponseDto;
import com.bank.mybank.dto.RequestDto;
import com.bank.mybank.dto.ResponseDto;
import com.bank.mybank.exception.BeneficiaryNotFoundException;
import com.bank.mybank.exception.CustomerAccountNotFoundException;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.exception.NoAccountListException;
import com.bank.mybank.service.CustomerService;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerControllerTest {

	@InjectMocks
	CustomerController customerController;

	@Mock
	CustomerService customerService;

	RequestDto addFavouriteRequestDto = null;
	ResponseDto addFavouriteResponseDto = null;
	FavouriteBeneficiariesResponseDto favouriteBeneficiariesResponseDto = null;

	@Before
	public void before() {
		addFavouriteRequestDto = new RequestDto();
		addFavouriteRequestDto.setBeneficiaryAccountName("bindu");
		addFavouriteRequestDto.setBeneficiaryAccountNumber(11L);
		addFavouriteRequestDto.setCustomerId(1L);
		addFavouriteRequestDto.setIfscCode("hdfc100");

		addFavouriteResponseDto = new ResponseDto();

		favouriteBeneficiariesResponseDto = new FavouriteBeneficiariesResponseDto();
		favouriteBeneficiariesResponseDto.setMessage("success");
		favouriteBeneficiariesResponseDto.setStatusCode(200);
	}

	@Test
	public void testDeleteFavouritePayeeForPositive() throws CustomerAccountNotFoundException {
		Mockito.when(customerService.deleteFavourite(addFavouriteRequestDto))
				.thenReturn(Optional.of(addFavouriteResponseDto));
		Integer response = customerController.deleteFavouritePayee(addFavouriteRequestDto).getStatusCodeValue();
		assertEquals(200, response);
	}

	@Test
	public void deleteFavouriteAccountsForPositive() throws GeneralException {
		Mockito.when(customerService.deleteFavourite(addFavouriteRequestDto))
				.thenReturn(Optional.of(addFavouriteResponseDto));
		ResponseEntity<Optional<ResponseDto>> result = customerController.deleteFavouritePayee(addFavouriteRequestDto);
		assertNotNull(result);
	}

	@Test
	public void testAddFavouritePayeeForPositive()
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Mockito.when(customerService.addFavourite(addFavouriteRequestDto))
				.thenReturn(Optional.of(addFavouriteResponseDto));
		Integer response = customerController.addFavouritePayee(addFavouriteRequestDto).getStatusCodeValue();
		assertEquals(200, response);
	}

	@Test(expected = GeneralException.class)
	public void testAddFavouritePayeeForNegative()
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Optional<ResponseDto> responseFavourite = Optional.ofNullable(null);
		Mockito.when(customerService.addFavourite(addFavouriteRequestDto)).thenReturn(responseFavourite);
		customerController.addFavouritePayee(addFavouriteRequestDto);
	}

	@Test
	public void testGetBeneficiaryDetailsPositive()
			throws BeneficiaryNotFoundException, CustomerAccountNotFoundException {
		Long customerId = 2L;
		Long beneficiaryAccountNumber = 100L;
		BeneficiaryResponseDto response = new BeneficiaryResponseDto();
		Mockito.when(customerService.getBeneficiaryDetails(customerId, beneficiaryAccountNumber))
				.thenReturn(Optional.of(response));
		Integer expected = customerController.getBeneficiaryDetails(customerId, beneficiaryAccountNumber)
				.getStatusCodeValue();
		assertEquals(200, expected);
	}

	@Test
	public void testGetBeneficiaryDetailsNegative()
			throws BeneficiaryNotFoundException, CustomerAccountNotFoundException {
		Long customerId = 2L;
		Long beneficiaryAccountNumber = 100L;
		Mockito.when(customerService.getBeneficiaryDetails(customerId, beneficiaryAccountNumber))
				.thenReturn(Optional.ofNullable(null));
		Integer expected = customerController.getBeneficiaryDetails(customerId, beneficiaryAccountNumber)
				.getStatusCodeValue();
		assertEquals(404, expected);
	}

	@Test
	public void viewFavouriteAccountsFor() throws GeneralException {
		Mockito.when(customerService.viewFavouriteAccounts(1L))
				.thenReturn(Optional.of(favouriteBeneficiariesResponseDto));
		ResponseEntity<Optional<FavouriteBeneficiariesResponseDto>> response = customerController
				.viewFavouriteAccounts(101L);
		assertNotNull(response);
	}

	@Test
	public void testEditFavouritePayeeForPositive()
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Mockito.when(customerService.editFavourite(addFavouriteRequestDto))
				.thenReturn(Optional.of(addFavouriteResponseDto));
		Integer response = customerController.editFavouritePayee(addFavouriteRequestDto).getStatusCodeValue();
		assertEquals(200, response);
	}

	@Test(expected = GeneralException.class)
	public void testEditFavouritePayeeForNegative()
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Optional<ResponseDto> responseFavourite = Optional.ofNullable(null);
		Mockito.when(customerService.editFavourite(addFavouriteRequestDto)).thenReturn(responseFavourite);
		customerController.editFavouritePayee(addFavouriteRequestDto);
	}

	@Test
	public void viewFavouriteAccountsForPositive() throws GeneralException {
		Mockito.when(customerService.viewFavouriteAccounts(1L))
				.thenReturn(Optional.of(favouriteBeneficiariesResponseDto));
		ResponseEntity<Optional<FavouriteBeneficiariesResponseDto>> result = customerController
				.viewFavouriteAccounts(1L);
		assertNotNull(result);
	}

}

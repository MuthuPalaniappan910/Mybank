package com.bank.mybank.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.mybank.dto.RequestDto;
import com.bank.mybank.dto.ResponseDto;
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
	
	RequestDto addFavouriteRequestDto=null;
	ResponseDto addFavouriteResponseDto=null;
	
	@Before
	public void before() {
		addFavouriteRequestDto=new RequestDto();
		addFavouriteRequestDto.setBeneficiaryAccountName("bindu");
		addFavouriteRequestDto.setBeneficiaryAccountNumber(11L);
		addFavouriteRequestDto.setCustomerId(1L);
		addFavouriteRequestDto.setIfscCode("hdfc100");
		
		addFavouriteResponseDto=new ResponseDto();
	}
	
	@Test
	public void testAddFavouritePayeeForPositive() throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Mockito.when(customerService.addFavourite(addFavouriteRequestDto)).thenReturn(Optional.of(addFavouriteResponseDto));
		Integer response=customerController.addFavouritePayee(addFavouriteRequestDto).getStatusCodeValue();
		assertEquals(200, response);
	}
	
	@Test(expected = GeneralException.class)
	public void testAddFavouritePayeeForNegative() throws NoAccountListException, CustomerAccountNotFoundException, GeneralException {
		Optional<ResponseDto> responseFavourite=Optional.ofNullable(null);
		Mockito.when(customerService.addFavourite(addFavouriteRequestDto)).thenReturn(responseFavourite);
		customerController.addFavouritePayee(addFavouriteRequestDto);
	}
	
	@Test
	public void testDeleteFavouritePayeeForPositive() throws  CustomerAccountNotFoundException {
		Mockito.when(customerService.deleteFavourite(addFavouriteRequestDto)).thenReturn(Optional.of(addFavouriteResponseDto));
		Integer response=customerController.deleteFavouritePayee(addFavouriteRequestDto).getStatusCodeValue();
		assertEquals(200, response);
	}
	
	
	
}

package com.bank.mybank.service;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.mybank.dto.LoginRequestDto;
import com.bank.mybank.dto.LoginResponseDto;
import com.bank.mybank.entity.Customer;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.repository.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoginServiceTest {

	@InjectMocks
	LoginServiceImpl loginServiceImplementation;

	@Mock
	CustomerRepository customerRepository;

	LoginRequestDto loginRequestDto = new LoginRequestDto();
	LoginResponseDto loginResponsedto = new LoginResponseDto();
	Customer customer = new Customer();

	@Before
	public void setUp() {
		loginRequestDto.setCustomerId(1L);
		loginRequestDto.setPassword("c");
		loginResponsedto.setCustomerID(1L);

		customer.setCustomerId(1L);
	}

	@Test
	public void testUserLogin() throws GeneralException {
		Mockito.when(customerRepository.findByCustomerIdAndPassword(1L, "c")).thenReturn(Optional.of(customer));
		Optional<LoginResponseDto> loginResponsedto = loginServiceImplementation.login(loginRequestDto);
		Assert.assertNotNull(loginResponsedto);
	}

	@Test(expected = GeneralException.class)
	public void testUserLoginNegative() throws GeneralException {
		Mockito.when(customerRepository.findByCustomerIdAndPassword(2L, "c")).thenReturn(Optional.of(customer));
		loginServiceImplementation.login(loginRequestDto);
	}
}

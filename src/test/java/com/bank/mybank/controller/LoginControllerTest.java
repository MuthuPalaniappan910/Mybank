package com.bank.mybank.controller;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.mybank.dto.LoginRequestDto;
import com.bank.mybank.dto.LoginResponseDto;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.service.LoginServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoginControllerTest {
	@InjectMocks
	LoginController loginController;

	@Mock
	LoginServiceImpl loginServiceImplementation;

	LoginRequestDto loginRequestDto = new LoginRequestDto();
	LoginResponseDto loginResponsedto = new LoginResponseDto();

	@Before
	public void setUp() {

		loginRequestDto.setCustomerId(1L);
		loginRequestDto.setPassword("c");	
		loginResponsedto.setStatusCode(200);
	}
	
	@Test
	public void testUserLoginPositive() throws GeneralException {
		Mockito.when(loginServiceImplementation.login(loginRequestDto)).thenReturn(Optional.of(loginResponsedto));
		ResponseEntity<Optional<LoginResponseDto>> loginResponsedto=loginController.login(loginRequestDto);
		Assert.assertNotNull(loginResponsedto);
	}
	
	@Test
	public void testUserLoginNegative() throws GeneralException {
		Mockito.when(loginServiceImplementation.login(loginRequestDto)).thenReturn(Optional.ofNullable(null));
		ResponseEntity<Optional<LoginResponseDto>> loginResponsedto=loginController.login(loginRequestDto);
		Assert.assertNotNull(loginResponsedto);
	}
}

package com.bank.mybank.controller;


import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.BranchResponseDto;
import com.bank.mybank.exception.IFSCNotFoundException;
import com.bank.mybank.service.BranchService;

@RunWith(SpringJUnit4ClassRunner.class)
public class BranchControllerTest {
	@Mock
	BranchService branchService;

	@InjectMocks
	BranchController branchController;

	BranchResponseDto branchResponseDto = null;

	@Before
	public void before() {
		branchResponseDto = new BranchResponseDto();
		branchResponseDto.setBankName("abc");
	}

	@Test
	public void testGetBankDetailsPositive() throws IFSCNotFoundException {
		Mockito.when(branchService.getBankDetails("qwe123")).thenReturn(Optional.of(branchResponseDto));
		Integer expected = branchController.getBankDetails("qwe123").getStatusCodeValue();
		assertEquals(ApplicationConstants.SUCCESSCODE, expected);
	}

	@Test
	public void testGetBankDetailsNegative() throws IFSCNotFoundException {
		Mockito.when(branchService.getBankDetails("qwe123")).thenReturn(Optional.ofNullable(null));
		Integer expected = branchController.getBankDetails("qwe123").getStatusCodeValue();
		assertEquals(ApplicationConstants.FAILURECODE, expected);
	}
}

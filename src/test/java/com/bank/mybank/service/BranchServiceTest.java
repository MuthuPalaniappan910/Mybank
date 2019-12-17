package com.bank.mybank.service;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.BranchResponseDto;
import com.bank.mybank.entity.IfscDetail;
import com.bank.mybank.exception.IFSCNotFoundException;
import com.bank.mybank.repository.IfscDetailRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BranchServiceTest {
	@Mock
	IfscDetailRepository ifscDetailRepository;

	@InjectMocks
	BranchServiceImpl branchServiceImpl;

	BranchResponseDto branchResponseDto = null;
	IfscDetail ifscDetail = null;

	@Before
	public void before() {
		branchResponseDto = new BranchResponseDto();
		ifscDetail = new IfscDetail();
		ifscDetail.setIfscCode("qwe123");
	}

	@Test
	public void testGetBankDetailsPositive() throws IFSCNotFoundException {
		Mockito.when(ifscDetailRepository.findByIfscCode("qwe123")).thenReturn(ifscDetail);
		Optional<BranchResponseDto> expected = branchServiceImpl.getBankDetails("qwe123");
		assertEquals(true, expected.isPresent());
	}

	@Test(expected = IFSCNotFoundException.class)
	public void testGetBankDetailsNegative() throws IFSCNotFoundException {
		Mockito.when(ifscDetailRepository.findByIfscCode("qwe123")).thenReturn(ifscDetail);
		Optional<BranchResponseDto> expected = branchServiceImpl.getBankDetails("qwe124");
		String response = ApplicationConstants.IFSC_FAILUREMESSAGE;
		assertEquals(response, expected);
	}
}

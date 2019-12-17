package com.bank.mybank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mybank.dto.BeneficiaryResponseDto;
import com.bank.mybank.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/customers")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class CustomerController {
	@Autowired
	CustomerService customerService;

	@GetMapping("/beneficiary")
	public ResponseEntity<BeneficiaryResponseDto> getBeneficiaryAccountDetails(@RequestParam Long customerId,
			@RequestParam Long beneficiaryAccountNumber) {
		log.info("For getting beneficiary details");
		return null;
	}
}

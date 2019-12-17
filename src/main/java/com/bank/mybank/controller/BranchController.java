package com.bank.mybank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.BranchResponseDto;
import com.bank.mybank.exception.IFSCNotFoundException;
import com.bank.mybank.service.BranchService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/branches")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class BranchController {
	@Autowired
	BranchService branchService;

	@GetMapping
	public ResponseEntity<BranchResponseDto> getBankDetails(@RequestParam String ifscCode) throws IFSCNotFoundException {
		log.info("For displaying bank details");
		BranchResponseDto branchResponseDto = new BranchResponseDto();
		Optional<BranchResponseDto> response = branchService.getBankDetails(ifscCode);
		if (response.isPresent()) {
			branchResponseDto.setBankName(response.get().getBankName());
			branchResponseDto.setBranchName(response.get().getBranchName());
			branchResponseDto.setIfscCode(response.get().getIfscCode());
			branchResponseDto.setPincode(response.get().getPincode());
			branchResponseDto.setStatusCode(response.get().getStatusCode());
			branchResponseDto.setMessage(ApplicationConstants.IFSC_SUCCESSMESSAGE);
			branchResponseDto.setStatusCode(ApplicationConstants.SUCCESSCODE);
			return new ResponseEntity<>(branchResponseDto, HttpStatus.OK);
		}
		branchResponseDto.setMessage(ApplicationConstants.IFSC_FAILUREMESSAGE);
		branchResponseDto.setStatusCode(ApplicationConstants.FAILURECODE);
		return new ResponseEntity<>(branchResponseDto, HttpStatus.NOT_FOUND);
	}
}

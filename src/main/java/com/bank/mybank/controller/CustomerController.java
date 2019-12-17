package com.bank.mybank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.AddFavouriteRequestDto;
import com.bank.mybank.dto.AddFavouriteResponseDto;
import com.bank.mybank.dto.BeneficiaryResponseDto;
import com.bank.mybank.exception.BeneficiaryNotFoundException;
import com.bank.mybank.exception.CustomerAccountNotFoundException;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.exception.NoAccountListException;
import com.bank.mybank.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/customers")
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
@RestController
public class CustomerController {
	@Autowired
	CustomerService customerService;

	@PostMapping("/beneficiary")
	public ResponseEntity<Optional<AddFavouriteResponseDto>> addFavouritePayee(
			@RequestBody AddFavouriteRequestDto addFavouriteRequestDto)
			throws GeneralException, NoAccountListException, CustomerAccountNotFoundException {
		Optional<AddFavouriteResponseDto> favouriteListResponse = customerService.addFavourite(addFavouriteRequestDto);
		if (!favouriteListResponse.isPresent()) {
			throw new GeneralException("Unable to add favourite payee");
		}
		favouriteListResponse.get().setStatusCode(ApplicationConstants.SUCCESS_CODE);
		favouriteListResponse.get().setMessage(ApplicationConstants.BENEFICIARY_ADDED_SUCCESSFULLY);
		return new ResponseEntity<>(favouriteListResponse, HttpStatus.OK);
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
	 * @return
	 * @throws BeneficiaryNotFoundException
	 * @throws CustomerAccountNotFoundException
	 */
	@GetMapping("/beneficiary")
	public ResponseEntity<BeneficiaryResponseDto> getBeneficiaryDetails(@RequestParam Long customerId,
			@RequestParam Long beneficiaryAccountNumber)
			throws BeneficiaryNotFoundException, CustomerAccountNotFoundException {
		log.info("For getting beneficiary details");
		Optional<BeneficiaryResponseDto> response = customerService.getBeneficiaryDetails(customerId,
				beneficiaryAccountNumber);
		if (response.isPresent()) {
			log.info("Displaying results");
			return new ResponseEntity<>(response.get(), HttpStatus.OK);
		}
		log.info("No beneficiary found");
		BeneficiaryResponseDto beneficaryResponseDto = new BeneficiaryResponseDto();
		beneficaryResponseDto.setMessage(ApplicationConstants.BENEFICIARY_INVALID);
		beneficaryResponseDto.setStatusCode(ApplicationConstants.FAILURECODE);
		return new ResponseEntity<>(beneficaryResponseDto, HttpStatus.NOT_FOUND);
	}
}

package com.bank.mybank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.AddFavouriteRequestDto;
import com.bank.mybank.dto.AddFavouriteResponseDto;
import com.bank.mybank.dto.FavouriteBeneficiariesResponseDto;
import com.bank.mybank.exception.CustomerAccountNotFoundException;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.exception.NoAccountListException;
import com.bank.mybank.service.CustomerService;
@RequestMapping("/customers")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/beneficiary")
	public ResponseEntity<Optional<AddFavouriteResponseDto>> addFavouritePayee(@RequestBody AddFavouriteRequestDto addFavouriteRequestDto)
			throws GeneralException,NoAccountListException,CustomerAccountNotFoundException {
		Optional<AddFavouriteResponseDto> favouriteListResponse = customerService.addFavourite(addFavouriteRequestDto);
		if (!favouriteListResponse.isPresent()) {
			throw new GeneralException("Unable to add favourite payee");
		}
		favouriteListResponse.get().setStatusCode(ApplicationConstants.SUCCESS_CODE);
		favouriteListResponse.get().setMessage(ApplicationConstants.BENEFICIARY_ADDED_SUCCESSFULLY);
		return new ResponseEntity<>(favouriteListResponse, HttpStatus.OK);
	}
	
	@GetMapping("{customerId}/benefeciary")
	public ResponseEntity<Optional<FavouriteBeneficiariesResponseDto>> viewFavouriteAccounts(
			@PathVariable Long customerId) throws GeneralException {
		Optional<FavouriteBeneficiariesResponseDto> favouriteBeneficiariesResponseDto = customerService
				.viewFavouriteAccounts(customerId);
		if (favouriteBeneficiariesResponseDto.isPresent()) {
			favouriteBeneficiariesResponseDto.get().setStatusCode(ApplicationConstants.FAVOURITE_ACCOUNT_SUCCESS_CODE);
			favouriteBeneficiariesResponseDto.get().setMessage(ApplicationConstants.FAVOURITE_ACCOUNT_SUCCESS_MESSAGE);
			return new ResponseEntity<>(favouriteBeneficiariesResponseDto, HttpStatus.OK);
		}
		FavouriteBeneficiariesResponseDto favouriteBeneficiariesResponse = new FavouriteBeneficiariesResponseDto();
		favouriteBeneficiariesResponse.setStatusCode(ApplicationConstants.FAVOURITE_ACCOUNT_FAILURE_CODE);
		favouriteBeneficiariesResponse.setMessage(ApplicationConstants.FAVOURITE_ACCOUNT_FAILURE_MESSAGE);
		return new ResponseEntity<>(Optional.of(favouriteBeneficiariesResponse), HttpStatus.OK);
	}
	
	@PutMapping("/beneficiary")
	public ResponseEntity<Optional<AddFavouriteResponseDto>> editFavouritePayee(@RequestBody AddFavouriteRequestDto addFavouriteRequestDto)
			throws GeneralException,NoAccountListException,CustomerAccountNotFoundException {
		Optional<AddFavouriteResponseDto> favouriteListResponse = customerService.addFavourite(addFavouriteRequestDto);
		if (!favouriteListResponse.isPresent()) {
			throw new GeneralException("Unable to edit favourite payee");
		}
		favouriteListResponse.get().setStatusCode(ApplicationConstants.SUCCESS_CODE);
		favouriteListResponse.get().setMessage(ApplicationConstants.BENEFICIARY_ADDED_SUCCESSFULLY);
		return new ResponseEntity<>(favouriteListResponse, HttpStatus.OK);
	}
}

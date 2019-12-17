package com.bank.mybank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.RequestDto;
import com.bank.mybank.dto.ResponseDto;
import com.bank.mybank.dto.FavouriteBeneficiariesResponseDto;
import com.bank.mybank.exception.CustomerAccountNotFoundException;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.exception.NoAccountListException;
import com.bank.mybank.service.CustomerService;
@RequestMapping("/customers/beneficiary")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class CustomerController {
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("")
	public ResponseEntity<Optional<ResponseDto>> addFavouritePayee(@RequestBody RequestDto addFavouriteRequestDto)
			throws GeneralException,NoAccountListException,CustomerAccountNotFoundException {
		Optional<ResponseDto> favouriteResponse = customerService.addFavourite(addFavouriteRequestDto);
		if (!favouriteResponse.isPresent()) {
			throw new GeneralException("Unable to add favourite payee");
		}
		favouriteResponse.get().setStatusCode(ApplicationConstants.SUCCESS_CODE);
		favouriteResponse.get().setMessage(ApplicationConstants.BENEFICIARY_ADDED_SUCCESSFULLY);
		return new ResponseEntity<>(favouriteResponse, HttpStatus.OK);
	}
	
	@PostMapping("/action")
	public ResponseEntity<Optional<ResponseDto>> deleteFavouritePayee(@RequestBody RequestDto deleteFavouriteRequestDto){
		Optional<ResponseDto> deleteResponse = customerService.deleteFavourite(deleteFavouriteRequestDto);
		if(deleteResponse.isPresent()) {
		deleteResponse.get().setStatusCode(ApplicationConstants.SUCCESS_CODE);
		deleteResponse.get().setMessage(ApplicationConstants.BENEFICIARY_DELETED_SUCCESSFULLY);
		}
		return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
	}
	
	@GetMapping("{customerId}")
	public ResponseEntity<Optional<FavouriteBeneficiariesResponseDto>> viewFavouriteAccounts(
			@PathVariable Long customerId) throws GeneralException {
		Optional<FavouriteBeneficiariesResponseDto> favouriteBeneficiariesResponseDto = customerService
				.viewFavouriteAccounts(customerId);
		if (!favouriteBeneficiariesResponseDto.isPresent()) {
			FavouriteBeneficiariesResponseDto favouriteBeneficiariesResponse = new FavouriteBeneficiariesResponseDto();
			favouriteBeneficiariesResponse.setStatusCode(ApplicationConstants.FAVOURITE_ACCOUNT_FAILURE_CODE);
			favouriteBeneficiariesResponse.setMessage(ApplicationConstants.FAVOURITE_ACCOUNT_FAILURE_MESSAGE);
		}
		favouriteBeneficiariesResponseDto.get().setStatusCode(ApplicationConstants.FAVOURITE_ACCOUNT_SUCCESS_CODE);
		favouriteBeneficiariesResponseDto.get().setMessage(ApplicationConstants.FAVOURITE_ACCOUNT_SUCCESS_MESSAGE);
		return new ResponseEntity<>(favouriteBeneficiariesResponseDto, HttpStatus.OK);
	}
}

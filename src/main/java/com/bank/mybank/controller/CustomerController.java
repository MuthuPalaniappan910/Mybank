package com.bank.mybank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> eb2feb50324d7713749d0e7af89fa04b35b74d42
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mybank.constants.ApplicationConstants;
<<<<<<< HEAD
import com.bank.mybank.dto.AddFavouriteRequestDto;
import com.bank.mybank.dto.AddFavouriteResponseDto;
import com.bank.mybank.dto.BeneficiaryResponseDto;
import com.bank.mybank.exception.BeneficiaryNotFoundException;
=======
import com.bank.mybank.dto.RequestDto;
import com.bank.mybank.dto.ResponseDto;
import com.bank.mybank.dto.FavouriteBeneficiariesResponseDto;
>>>>>>> eb2feb50324d7713749d0e7af89fa04b35b74d42
import com.bank.mybank.exception.CustomerAccountNotFoundException;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.exception.NoAccountListException;
import com.bank.mybank.service.CustomerService;
<<<<<<< HEAD

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/customers")
=======
@RequestMapping("/customers/beneficiary")
@RestController
>>>>>>> eb2feb50324d7713749d0e7af89fa04b35b74d42
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
@RestController
public class CustomerController {
	@Autowired
	CustomerService customerService;
<<<<<<< HEAD

	@PostMapping("/beneficiary")
	public ResponseEntity<Optional<AddFavouriteResponseDto>> addFavouritePayee(
			@RequestBody AddFavouriteRequestDto addFavouriteRequestDto)
			throws GeneralException, NoAccountListException, CustomerAccountNotFoundException {
		Optional<AddFavouriteResponseDto> favouriteListResponse = customerService.addFavourite(addFavouriteRequestDto);
		if (!favouriteListResponse.isPresent()) {
=======
	
	@PostMapping("")
	public ResponseEntity<Optional<ResponseDto>> addFavouritePayee(@RequestBody RequestDto addFavouriteRequestDto)
			throws GeneralException,NoAccountListException,CustomerAccountNotFoundException {
		Optional<ResponseDto> favouriteResponse = customerService.addFavourite(addFavouriteRequestDto);
		if (!favouriteResponse.isPresent()) {
>>>>>>> eb2feb50324d7713749d0e7af89fa04b35b74d42
			throw new GeneralException("Unable to add favourite payee");
		}
		favouriteResponse.get().setStatusCode(ApplicationConstants.SUCCESS_CODE);
		favouriteResponse.get().setMessage(ApplicationConstants.BENEFICIARY_ADDED_SUCCESSFULLY);
		return new ResponseEntity<>(favouriteResponse, HttpStatus.OK);
	}
	
	@PostMapping("/action")
	public ResponseEntity<Optional<ResponseDto>> deleteFavouritePayee(@RequestBody RequestDto deleteFavouriteRequestDto) throws CustomerAccountNotFoundException{
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

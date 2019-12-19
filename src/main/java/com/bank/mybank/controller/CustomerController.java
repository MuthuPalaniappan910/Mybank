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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.BeneficiaryResponseDto;
import com.bank.mybank.dto.FavouriteBeneficiariesResponseDto;
import com.bank.mybank.dto.RequestDto;
import com.bank.mybank.dto.ResponseDto;
import com.bank.mybank.exception.BeneficiaryNotFoundException;
import com.bank.mybank.exception.CustomerAccountNotFoundException;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.exception.NoAccountListException;
import com.bank.mybank.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/customers")
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@Slf4j
/**
 * 
 * This class is used to perform all the end user related operations
 *
 */
public class CustomerController {
	@Autowired
	CustomerService customerService;

	/**
	 * The method adds an account to favourite list of the customer
	 * 
	 * 
	 * @author Bindu
	 * @param addFavouriteRequestDto
	 * @return
	 * @throws GeneralException
	 * @throws NoAccountListException
	 * @throws CustomerAccountNotFoundException
	 */
	@PostMapping("/beneficiary")
	public ResponseEntity<Optional<ResponseDto>> addFavouritePayee(@RequestBody RequestDto addFavouriteRequestDto)
			throws GeneralException, NoAccountListException, CustomerAccountNotFoundException {
		log.info("Entering into add  favourite payee controller");
		Optional<ResponseDto> favouriteResponse = customerService.addFavourite(addFavouriteRequestDto);
		
		if (!favouriteResponse.isPresent()) {
			throw new GeneralException("Unable to add favourite payee");
		}
		favouriteResponse.get().setStatusCode(ApplicationConstants.SUCCESS_CODE);
		favouriteResponse.get().setMessage(ApplicationConstants.BENEFICIARY_ADDED_SUCCESSFULLY);
		return new ResponseEntity<>(favouriteResponse, HttpStatus.OK);
	}

	/**
	 * @author Bindu
	 * @param deleteFavouriteRequestDto
	 * @return
	 */

	@PutMapping("/beneficiary/action")
	public ResponseEntity<Optional<ResponseDto>> deleteFavouritePayee(
			@RequestBody RequestDto deleteFavouriteRequestDto) {
		Optional<ResponseDto> deleteResponse = customerService.deleteFavourite(deleteFavouriteRequestDto);
		log.info("deleting favourite payee");
		if (deleteResponse.isPresent()) {
			deleteResponse.get().setStatusCode(ApplicationConstants.SUCCESS_CODE);
			deleteResponse.get().setMessage(ApplicationConstants.BENEFICIARY_DELETED_SUCCESSFULLY);
		}
		return new ResponseEntity<>(deleteResponse, HttpStatus.OK);
	}

	/**
	 * This method is used to view favourite active accounts
	 * @author Mahesh
	 * @param customerId
	 * @return
	 * @throws GeneralException
	 */

	@GetMapping("/{customerId}/beneficiary")
	public ResponseEntity<Optional<FavouriteBeneficiariesResponseDto>> viewFavouriteAccounts(
			@PathVariable Long customerId) throws GeneralException {
		Optional<FavouriteBeneficiariesResponseDto> favouriteBeneficiariesResponseDto = customerService
				.viewFavouriteAccounts(customerId);
		log.info("Viewing favourite payee");
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

	/**
	 * This method is used to edit the details of the added favourite account
	 * @param editFavouriteRequestDto
	 * @return ResponseDto success/failure of edit response
	 * @throws GeneralException
	 * @throws NoAccountListException
	 * @throws CustomerAccountNotFoundException
	 */

	@PutMapping("/beneficiary")
	public ResponseEntity<Optional<ResponseDto>> editFavouritePayee(@RequestBody RequestDto editFavouriteRequestDto)
			throws GeneralException, NoAccountListException, CustomerAccountNotFoundException {
		Optional<ResponseDto> favouriteListResponse = customerService.editFavourite(editFavouriteRequestDto);
		log.info("Editing favourite payee");
		if (!favouriteListResponse.isPresent()) {
			ResponseDto responseDto= new ResponseDto();
			responseDto.setStatusCode(ApplicationConstants.FAILURE_CODE);
			responseDto.setMessage(ApplicationConstants.FAVOURITE_ACCOUNT_FAILURE_MESSAGE);
			return new ResponseEntity<>(Optional.of(responseDto), HttpStatus.OK);
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
		beneficaryResponseDto.setStatusCode(ApplicationConstants.FAILURE_CODE);
		return new ResponseEntity<>(beneficaryResponseDto, HttpStatus.NOT_FOUND);
	}
}

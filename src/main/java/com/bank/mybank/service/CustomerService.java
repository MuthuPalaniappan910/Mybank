package com.bank.mybank.service;

import java.util.Optional;

import com.bank.mybank.dto.BeneficiaryResponseDto;
import com.bank.mybank.dto.FavouriteBeneficiariesResponseDto;
import com.bank.mybank.dto.RequestDto;
import com.bank.mybank.dto.ResponseDto;
import com.bank.mybank.exception.BeneficiaryNotFoundException;
import com.bank.mybank.exception.CustomerAccountNotFoundException;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.exception.NoAccountListException;

public interface CustomerService {

	Optional<ResponseDto> addFavourite(RequestDto addFavouriteRequestDto)
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException;

	Optional<ResponseDto> deleteFavourite(RequestDto deleteRequestDto);

	Optional<FavouriteBeneficiariesResponseDto> viewFavouriteAccounts(Long customerId) throws GeneralException;

	Optional<BeneficiaryResponseDto> getBeneficiaryDetails(Long customerId, Long beneficiaryAccountNumber)
			throws BeneficiaryNotFoundException, CustomerAccountNotFoundException;

	Optional<ResponseDto> editFavourite(RequestDto addFavouriteRequestDto)
			throws NoAccountListException, CustomerAccountNotFoundException, GeneralException;

}

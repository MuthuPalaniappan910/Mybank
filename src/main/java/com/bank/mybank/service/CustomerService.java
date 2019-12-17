package com.bank.mybank.service;

import java.util.Optional;

import com.bank.mybank.dto.RequestDto;
import com.bank.mybank.dto.ResponseDto;
import com.bank.mybank.dto.FavouriteBeneficiariesResponseDto;
import com.bank.mybank.exception.CustomerAccountNotFoundException;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.exception.NoAccountListException;

public interface CustomerService {
	
	Optional<ResponseDto> addFavourite(RequestDto addFavouriteRequestDto) throws NoAccountListException, CustomerAccountNotFoundException, GeneralException;
	
	Optional<ResponseDto> deleteFavourite(RequestDto deleteRequestDto) throws CustomerAccountNotFoundException ;

	Optional<FavouriteBeneficiariesResponseDto> viewFavouriteAccounts(Long customerId) throws GeneralException; 
	
}

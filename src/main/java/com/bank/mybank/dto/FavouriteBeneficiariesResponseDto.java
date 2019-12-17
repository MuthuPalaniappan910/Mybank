package com.bank.mybank.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavouriteBeneficiariesResponseDto implements Serializable{
	
	private static final long serialVersionUID = 4758208273289187861L;
	private Integer statusCode;
	private String message;
	private List<CustomerFavouriteAccountResponse> favouritesList;
}
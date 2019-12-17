package com.bank.mybank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerFavouriteAccountResponse {

	private String beneficiaryAccountName;
	private Long beneficiaryAccountNumber;
	private String ifscCode;
}

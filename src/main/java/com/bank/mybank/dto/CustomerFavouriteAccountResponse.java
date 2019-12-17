package com.bank.mybank.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerFavouriteAccountResponse implements Serializable {

	private static final long serialVersionUID = 4758208273289187861L;
	private String beneficiaryAccountName;
	private Long beneficiaryAccountNumber;
	private String ifscCode;
}

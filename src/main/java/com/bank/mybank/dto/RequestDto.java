package com.bank.mybank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestDto {
	
	private Long customerId;
	private String beneficiaryAccountName;
	private Long beneficiaryAccountNumber;
	private String ifscCode;
	private String actionType;
}



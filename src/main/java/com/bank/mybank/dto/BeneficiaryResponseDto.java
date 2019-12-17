package com.bank.mybank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BeneficiaryResponseDto {
	private Integer statusCode;
	private String message;
	private String beneficiaryAccountName;
	private Long beneficiaryAccountNumber;
	private String ifscCode;
}

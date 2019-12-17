package com.bank.mybank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchResponseDto {
	private Integer statusCode;
	private String message;
	private String ifscCode;
	private String branchName;
	private String bankName;
	private Long pincode;
}

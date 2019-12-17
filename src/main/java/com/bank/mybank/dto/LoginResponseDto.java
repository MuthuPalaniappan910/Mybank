package com.bank.mybank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
	private Integer statusCode;
	private String message;
	private Long customerID;
}

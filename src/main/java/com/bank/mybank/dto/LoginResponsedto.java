package com.bank.mybank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponsedto {
	public Integer statusCode;
	public String message;
	public Long customerID;
}

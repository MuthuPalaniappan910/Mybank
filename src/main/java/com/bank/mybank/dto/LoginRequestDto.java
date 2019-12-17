package com.bank.mybank.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestdto {

	private Long customerId;
	private String password;
}

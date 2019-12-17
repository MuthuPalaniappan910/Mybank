package com.bank.mybank.service;

import java.util.Optional;

import com.bank.mybank.dto.LoginRequestDto;

import com.bank.mybank.dto.LoginResponseDto;
import com.bank.mybank.exception.GeneralException;




public interface LoginService {
	public Optional<LoginResponseDto> login(LoginRequestDto loginRequestdto) throws GeneralException;

}

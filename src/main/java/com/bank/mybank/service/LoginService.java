package com.bank.mybank.service;

import java.util.Optional;

import com.bank.mybank.dto.LoginRequestDto;
import com.bank.mybank.dto.LoginResponsedto;
import com.bank.mybank.exception.GeneralException;

public interface LoginService {
	public Optional<LoginResponsedto> login(LoginRequestDto loginRequestdto) throws GeneralException;
}

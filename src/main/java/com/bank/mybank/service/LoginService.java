package com.bank.mybank.service;

import java.util.Optional;

import com.bank.mybank.dto.LoginRequestdto;
import com.bank.mybank.dto.LoginResponsedto;
import com.bank.mybank.exception.GeneralException;

public interface LoginService {
	public Optional<LoginResponsedto> login(LoginRequestdto loginRequestdto) throws GeneralException;
}

package com.bank.mybank.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.LoginRequestDto;
import com.bank.mybank.dto.LoginResponsedto;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.service.LoginService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author Chethana M
 * @Description This class is used to perform all the customer related
 *              authentication operations
 *
 */
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping
@Slf4j
public class LoginController {

	
	@Autowired
	LoginService loginService;
	
	@PostMapping
	public ResponseEntity<Optional<LoginResponsedto>> login(@RequestBody LoginRequestDto loginRequestdto)
			throws GeneralException {
		log.info("Entering into login method of LoginController");
		Optional<LoginResponsedto> loginResponsedto = loginService.login(loginRequestdto);
		if (!loginResponsedto.isPresent()) {
			LoginResponsedto loginResponse= new LoginResponsedto();
			loginResponse.setMessage(ApplicationConstants.LOGIN_ERROR);
			loginResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			return new ResponseEntity<>(Optional.of(loginResponse), HttpStatus.NOT_FOUND);
		}
		loginResponsedto.get().setMessage(ApplicationConstants.LOGIN_SUCCESS);
		loginResponsedto.get().setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(loginResponsedto, HttpStatus.OK);
		
	}

}

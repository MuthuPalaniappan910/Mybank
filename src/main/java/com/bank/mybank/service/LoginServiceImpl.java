package com.bank.mybank.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.LoginRequestDto;
import com.bank.mybank.dto.LoginResponseDto;
import com.bank.mybank.entity.Customer;
import com.bank.mybank.exception.GeneralException;
import com.bank.mybank.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author Chethana M
 *  This class is used to perform all the customer related
 *              authentication operations
 *
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

	@Autowired
	CustomerRepository customerRepository;

	/**
	 * @author Chethana
	 * @Description This method is used for user to login with valid credentials
	 * @param loginRequestdto
	 * @return LoginResponsedto
	 * @exception LOGIN_ERROR
	 */
	public Optional<LoginResponseDto> login(LoginRequestDto loginRequestdto) throws GeneralException {
		log.info("Entering into login() method of LoginServiceImpl");
		Optional<Customer> customerResponse = customerRepository
				.findByCustomerIdAndPassword(loginRequestdto.getCustomerId(), loginRequestdto.getPassword());
		if (!customerResponse.isPresent()) {
			log.error("Exception occured in login() method of LoginServiceImpl");
			throw new GeneralException(ApplicationConstants.LOGIN_ERROR);
		}
		LoginResponseDto loginResponsedto = new LoginResponseDto();
		loginResponsedto.setCustomerID(customerResponse.get().getCustomerId());
		loginResponsedto.setCustomerName(customerResponse.get().getCustomerName());
		return Optional.of(loginResponsedto);
	}
}

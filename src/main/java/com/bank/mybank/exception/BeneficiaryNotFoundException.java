package com.bank.mybank.exception;

public class BeneficiaryNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public BeneficiaryNotFoundException(String exception) {
		super(exception);
	}
}
package com.bank.mybank.exception;

public class NoAccountListException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoAccountListException(String exception) {
		super(exception);
	}
}
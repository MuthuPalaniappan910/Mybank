package com.bank.mybank.exception;

public class IFSCNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public IFSCNotFoundException(String exception) {
		super(exception);
	}
}
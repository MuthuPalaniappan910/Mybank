package com.bank.mybank.constants;

public class ApplicationConstants {
	private ApplicationConstants() {

	}

	public static final String BENEFICIARY_ADDED_SUCCESSFULLY = "Beneficiary details saved successfully";

	public static final Integer SUCCESS_CODE = 200;

	public static final String STATUS_OF_ACTIVE_ACCOUNT = "active";

	public static final String IFSC_SUCCESS_MESSAGE = "You have a correct branch name";
	public static final  String IFSC_FAILURE_MESSAGE = "Invalid IFSC Code, Please enter valid IFSC code to save the beneficiary details";
	public static final Integer FAILURE_CODE = 404;
	

	public static final String LOGIN_ERROR = "Invalid customer credentials, Please try with valid Customer Id and Password";
	public static final String LOGIN_SUCCESS = "Login Success";

	public static final String STATUS_OF_INACTIVE_CODE = "inactive";

	public static final String BENEFICIARY_DELETED_SUCCESSFULLY = "The beneficiary account has been deleted successfully";
	public static final Integer FAVOURITE_ACCOUNT_SUCCESS_CODE = 200;
	public static final String FAVOURITE_ACCOUNT_SUCCESS_MESSAGE = "You favourite Account LISt";
	public static final Integer FAVOURITE_ACCOUNT_FAILURE_CODE = 200;
	public static final String FAVOURITE_ACCOUNT_FAILURE_MESSAGE = "You favourite Account LISt";
	
	public static final String INVALID_CUSTOMER = "Invalid Customer";
	public static final String INVALID_ACCOUNT_NUMBER = "Invalid Account Number";
	
}

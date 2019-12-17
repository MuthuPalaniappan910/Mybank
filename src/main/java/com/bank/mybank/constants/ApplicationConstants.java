package com.bank.mybank.constants;

public class ApplicationConstants {
	private ApplicationConstants() {

	}

	public static String IFSC_SUCCESSMESSAGE = "You have a correct branch name";
	public static String IFSC_FAILUREMESSAGE = "Invalid IFSC Code, Please enter valid IFSC code to save the beneficiary details";
	public static Integer SUCCESSCODE = 200;
	public static Integer FAILURECODE = 404;

	public static final String BENEFICIARY_ADDED_SUCCESSFULLY = "Beneficiary details saved successfully";

	public static final Integer SUCCESS_CODE = 200;

	public static final String STATUS_OF_ACTIVE_ACCOUNT = "active";

	public static final String LOGIN_ERROR = "Invalid customer credentials, Please try with valid Customer Id and Password";
	public static final String LOGIN_SUCCESS = "Login Success";

	public static final String BENEFICIARY_INVALID = "There is no such beneficiary";
	public static final String BENEFICIARY_VALID = "There is a beneficiary";
	public static final String CUSTOMER_INVALID = "There is no such account";
	public static final String ACCOUNT_BENEFICIARY_MISMATCH = "There is no such beneficiary added by you for your account";
}

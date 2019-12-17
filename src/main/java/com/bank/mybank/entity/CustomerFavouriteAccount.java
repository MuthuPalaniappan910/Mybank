package com.bank.mybank.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
@SequenceGenerator(name = "customerfavouritesequence", initialValue = 765756, allocationSize = 1)
public class CustomerFavouriteAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerfavouritesequence")
	private Long customerFavouriteAccountId;
	private Long customerAccountNumber;
	private Long beneficiaryAccountNumber;
	private String beneficiaryAccountName;
	private String ifscCode;
	private String customerFavouriteAccountStatus;
}

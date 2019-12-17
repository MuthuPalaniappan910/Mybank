package com.bank.mybank.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
public class CustomerAccount {
	@Id
	private Long customerAccountNumber;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerId", nullable = false)
	private Customer customerId;

	private String accoutnType;
	private String accountStatus;
}

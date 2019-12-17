package com.bank.mybank.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
public class Customer {
	@Id
	private Long customerId;
	private String customerName;
	private String password;
	private String email;
	private Long phoneNumber;
}

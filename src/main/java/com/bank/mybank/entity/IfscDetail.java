package com.bank.mybank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity

@Getter
@Setter
public class IfscDetail {
	@Id
	private Long ifscDetailId;
	@Column(unique = true)
	private String ifscCode;
	private String branchName;
	private String bankName;
	private Long pincode;
	private String branchStatus;
}

package com.bank.mybank.service;

import java.util.Optional;

import com.bank.mybank.dto.BranchResponseDto;
import com.bank.mybank.exception.IFSCNotFoundException;

public interface BranchService {

	Optional<BranchResponseDto> getBankDetails(String ifscCode) throws IFSCNotFoundException;

}

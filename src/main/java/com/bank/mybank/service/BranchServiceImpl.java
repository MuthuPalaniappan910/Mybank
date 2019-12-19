package com.bank.mybank.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.mybank.constants.ApplicationConstants;
import com.bank.mybank.dto.BranchResponseDto;
import com.bank.mybank.entity.IfscDetail;
import com.bank.mybank.exception.IFSCNotFoundException;
import com.bank.mybank.repository.IfscDetailRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Muthu This class is used to perform the branch details related
 *         operations
 **/
@Service
@Slf4j
public class BranchServiceImpl implements BranchService {
	@Autowired
	IfscDetailRepository ifscDetailRepository;

	/**
	 * This method is used to get the bank details by using ifsc code
	 * 
	 * @author Muthu
	 * @param ifscCode
	 * @return BranchResponseDto on success returns bank details/on failure returns
	 *         failure message and status
	 * @throws IFSCNotFoundException
	 */

	@Override
	public Optional<BranchResponseDto> getBankDetails(String ifscCode) throws IFSCNotFoundException {
		log.info("entering into get bankDetails");
		BranchResponseDto branchResponseDto = new BranchResponseDto();
		IfscDetail ifscDetail = ifscDetailRepository.findByIfscCode(ifscCode);
		if (ifscDetail == null) {
			throw new IFSCNotFoundException(ApplicationConstants.IFSC_FAILURE_MESSAGE);
		}
		BeanUtils.copyProperties(ifscDetail, branchResponseDto);
		return Optional.of(branchResponseDto);
	}

}

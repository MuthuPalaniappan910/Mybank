package com.bank.mybank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.mybank.entity.IfscDetail;

@Repository
public interface IfscDetailRepository extends JpaRepository<IfscDetail, Long> {

	IfscDetail findByIfscCode(String ifscCode);

}

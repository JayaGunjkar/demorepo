package com.jsg.DocumentCreation.app.service;

import org.springframework.stereotype.Service;

import com.jsg.DocumentCreation.app.model.LoanApplication;

public interface HomeService {

	public LoanApplication saveLoanAppDetails(LoanApplication loan);

	public void sendEmail(String fromEmail, int loanAppid);

	

}

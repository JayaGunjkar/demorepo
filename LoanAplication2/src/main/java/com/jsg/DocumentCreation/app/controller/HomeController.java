package com.jsg.DocumentCreation.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsg.DocumentCreation.app.model.LoanApplication;
import com.jsg.DocumentCreation.app.model.SanctionDetails;
import com.jsg.DocumentCreation.app.service.HomeService;
import com.jsg.DocumentCreation.app.service.SanctionDetailsServicr;
import com.lowagie.text.DocumentException;

@RestController
public class HomeController {

	@Autowired
	HomeService hs;
	
	@Autowired
	SanctionDetailsServicr sds;
	
	@Value(value="${spring.mail.username}")
	String fromEmail;
	
	@PostMapping("/saveLoanApplicationDetails")
	public LoanApplication saveDetails(@RequestBody LoanApplication loan) {
		
		return hs.saveLoanAppDetails(loan);
	}
	
	@GetMapping("/getPdf/{sanctionId}")
	public String getById(@PathVariable("sanctionId") int sid,@RequestBody SanctionDetails sd) throws DocumentException{
		
		sd.setFromEmail(fromEmail);
		//sds.sendEmail(fromEmail,sid);
		try {
		sds.getById(sid,sd,fromEmail);
		}
		catch(Exception e) {
			System.out.println("email cannot send");
		}
		return "Saved successfully";
	}
	
	
//	@PostMapping("/sendEmail/{loanAppid}")
//	public String sendEmail(@PathVariable("loanAppid") int loanAppid
//			,@RequestBody LoanApplication la) {
//		
//		
//		la.setFromEmail(fromEmail);
//		
//		hs.sendEmail(fromEmail,loanAppid);
//		
//		return "Email Send Successfully";
//		
//		
//	}
}

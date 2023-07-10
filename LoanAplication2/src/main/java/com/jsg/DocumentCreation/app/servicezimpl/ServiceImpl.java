package com.jsg.DocumentCreation.app.servicezimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import com.jsg.DocumentCreation.app.model.LoanApplication;
import com.jsg.DocumentCreation.app.repository.LoanRepository;
import com.jsg.DocumentCreation.app.service.HomeService;

@Service
public class ServiceImpl implements HomeService{

	@Autowired
	LoanRepository lr;
	@Autowired
	JavaMailSender sender;
	@Override
	public LoanApplication saveLoanAppDetails(LoanApplication loan) {
		
	return lr.save(loan);
	}

	@Override
	public void sendEmail(String fromEmail, int loanAppid) {
		Optional<LoanApplication> op=lr.findById(loanAppid);
		LoanApplication la=op.get();
		MimeMessage m=sender.createMimeMessage();
		
		try {
			MimeMessageHelper helper=new MimeMessageHelper(m,true);
			helper.setTo(la.getCustEmailId());
			helper.setFrom(fromEmail);
			//helper.addAttachment("Sanction letter", null);
			sender.send(m);
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
		
	}

}

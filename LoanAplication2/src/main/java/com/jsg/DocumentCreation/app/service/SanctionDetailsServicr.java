package com.jsg.DocumentCreation.app.service;

import org.springframework.stereotype.Service;

import com.jsg.DocumentCreation.app.model.SanctionDetails;
import com.lowagie.text.DocumentException;


public interface SanctionDetailsServicr {

	public void getById(int sid, SanctionDetails sd, String fromEmail) throws DocumentException;

	//public void sendEmail(String fromEmail, int sid);

}

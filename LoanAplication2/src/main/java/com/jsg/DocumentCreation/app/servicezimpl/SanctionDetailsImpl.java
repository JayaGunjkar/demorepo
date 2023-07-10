package com.jsg.DocumentCreation.app.servicezimpl;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.hibernate.result.Output;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.jsg.DocumentCreation.app.model.LoanApplication;
import com.jsg.DocumentCreation.app.model.SanctionDetails;
import com.jsg.DocumentCreation.app.repository.SanctionDetailsRepository;
import com.jsg.DocumentCreation.app.service.SanctionDetailsServicr;
@Service
public class SanctionDetailsImpl implements SanctionDetailsServicr{

	@Autowired
	SanctionDetailsRepository sdr;
	
	@Autowired
	MailSender ms;
	
	@Autowired
	JavaMailSender jms;
	
	@Override
	public void getById(int sid,SanctionDetails sd,String fromEmail) throws DocumentException {
		
String title="Sanction Letter";
		
		
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		Document document=new Document();
		
		PdfWriter.getInstance(document, out);
		document.open();
		Font titleFont=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		titleFont.setColor(CMYKColor.RED);
		Paragraph titlePara=new Paragraph(title,titleFont);
		titlePara.setAlignment(Element.ALIGN_CENTER);
		document.add(titlePara);
		
		Date date=new Date();
		DateFormat dateFormat=new SimpleDateFormat("dd-MM-YYYY");
		String formatedDate=dateFormat.format(date);
		String to="Date:-"+formatedDate+"\n To Admin";
		String text="   This is Sanction letter for your Loan Proposal";
		
		Optional<SanctionDetails> sanctionedData=sdr.findById(sid);
				SanctionDetails sdetails=sanctionedData.get();
				
				
		Font toFont=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		Paragraph toPara=new Paragraph(to,toFont);
		toPara.setSpacingBefore(20);
		document.add(toPara);
		
		Paragraph textPara=new Paragraph(text);
		textPara.setSpacingBefore(10);
		document.add(textPara);
		//--------------Table---------------
		
		PdfPTable table=new PdfPTable(4);
		table.setWidths(new int[] {1,3,3,3});
		table.setWidthPercentage(100F);
		table.setSpacingBefore(20);
		
		//-----------Cell-----------
		PdfPCell headCell=new PdfPCell();
		headCell.setPadding(5);
		headCell.setPaddingLeft(10);
		Font headCellFont=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		headCellFont.setColor(CMYKColor.RED);
		
		//-----------Adding Phrase-------------
		headCell.setPhrase(new Phrase("SanctionId",headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase("Loan Amount Sanctioned",headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase("Loan Type",headCellFont));
		table.addCell(headCell);
		headCell.setPhrase(new Phrase("Tenure in year sanctioned",headCellFont));
		table.addCell(headCell);
		
		PdfPCell dataCell=new PdfPCell();
		dataCell.setPadding(3);
		dataCell.setBackgroundColor(CMYKColor.CYAN);
		dataCell.setPhrase(new Phrase(String.valueOf(sdetails)));
		dataCell.setPhrase(new Phrase(String.valueOf(sdetails.getSanctionId())));
		table.addCell(dataCell);
		dataCell.setPhrase(new Phrase(String.valueOf(sdetails.getLoanAmountSanctioned())));
		table.addCell(dataCell);
		dataCell.setPhrase(new Phrase(String.valueOf(sdetails.getLoanType())));
		table.addCell(dataCell);
		dataCell.setPhrase(new Phrase(String.valueOf(sdetails.getTenureInYearSanctioned())));
		table.addCell(dataCell);
		

		document.add(table);
		
		document.close();
byte[] mailPdfBytes=out.toByteArray();
sdetails.setSanctionPdf(out.toByteArray());
		sdr.save(sdetails);
		
//-----------------------mail sending------------------------------------		

		
//		SimpleMailMessage message=new SimpleMailMessage();
//		message.setTo(sd.getCustEmailId());
//		message.setFrom(fromEmail);
//		message.setSubject(sdetails.getLoanType());
//		message.setText(String.valueOf(sdetails.getSanctionId()));
//		message.setText(String.valueOf(sdetails.getLoanAmountSanctioned()));
//		//message.setText(text);
//		message.setSentDate(date);
//		ms.send(message);
		
		
		
		
//----------------------MailWithMime---------------------------		

		MimeMessage m=jms.createMimeMessage();
		try {
			File f=new File("SanctionFile.pdf");
			f.createNewFile();
			//FileWriter fw=new FileWriter("SanctionFile.txt",true);
			
			OutputStream fileOutputStram=new FileOutputStream(f);
			fileOutputStram.write(mailPdfBytes);
			
			fileOutputStram.close();
			MimeMessageHelper helper=new MimeMessageHelper(m,true);
			helper.setTo(sd.getCustEmailId());
			helper.setFrom(fromEmail);
			
	        helper.setText("fadsf");    
			
	        helper.setSubject("Sanction Letter for Loan Application");    
			helper.addAttachment("pdf.pdf", f.getAbsoluteFile());
			jms.send(m);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
//		
//		
		//LoanApplication la=customer.get();
		//customer.get().getSanctionDetails().setSanctionPdf(out.toByteArray());
		//hr.save(la);
		//return new ByteArrayInputStream(out.toByteArray());
	}
//	@Override
//	public void sendEmail(String fromEmail, int sid) {
//		// TODO Auto-generated method stub
//		
//	}

}

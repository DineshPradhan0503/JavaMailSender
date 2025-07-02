package com.bluepal.service;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.bluepal.BootMailProj01Application;
import jakarta.mail.internet.MimeMessage;

@Service

public class PurchageMgmtServiceImpl implements IPurchaseMgmtService {

    private final BootMailProj01Application bootMailProj01Application;
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String fromEmailId;

    PurchageMgmtServiceImpl(BootMailProj01Application bootMailProj01Application) {
        this.bootMailProj01Application = bootMailProj01Application;
    }

	@Override
	public String shopping(String[] items, Double[] price, String[] toEmailIds) throws Exception {
		//calculate the bill amount
		double totalAmt = 0.0;
			for (Double p : price) {
			totalAmt += p;
		}
		String msg1=Arrays.toString(items)+"are purchased having price "+Arrays.toString(price)+" with the bill amount "+totalAmt;
		
		//trigger the email message
		String msg2=sendMail(msg1, toEmailIds, fromEmailId);
		
		return msg1+" and "+msg2;
	}
	
	private String sendMail(String msg,String[]toEmailIds,String fromEmailId) throws Exception {
		//create and sent MultiPart MimeMessage 
		MimeMessage message = mailSender.createMimeMessage();//represents email message
		MimeMessageHelper helper = new MimeMessageHelper(message, true);//represents email message with attachment
		//setting the header values
		helper.setSentDate(new Date());
		helper.setFrom(fromEmailId);
		helper.setTo(toEmailIds);
		helper.setSubject("Purchase Details");
		
		//setting the multiPart body of the email
		helper.setText(msg);//text part of the email
		helper.addAttachment("logo1.png", new ClassPathResource("logo1.png"));//image part of the email
		//send the message
		mailSender.send(message);
		
		return "Mail sent successfully";
		
	}
	
}

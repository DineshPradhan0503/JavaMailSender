package com.bluepal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.bluepal.dto.EmailRequest;
import com.bluepal.service.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping("/send")
	public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String message,
			@RequestParam(defaultValue = "false") boolean html,
			@RequestParam(required = false) MultipartFile attachment,
			@RequestParam(required = false) MultipartFile inlineImage) {
		try {
			EmailRequest email = new EmailRequest();
			email.to = to;
			email.subject = subject;
			email.message = message;
			email.html = html;
			email.attachment = attachment;
			email.inlineImage = inlineImage;

			emailService.sendEmail(email);
			return "Email sent successfully!";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to send email: " + e.getMessage();
		}
	}
}

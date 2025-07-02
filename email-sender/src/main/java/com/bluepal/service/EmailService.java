package com.bluepal.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import com.bluepal.dto.EmailRequest;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(EmailRequest request) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(request.to);
        helper.setSubject(request.subject);

        if (request.html) {
            String content = request.message;

            if (request.inlineImage != null && !request.inlineImage.isEmpty()) {
                String contentId = "image001";
                content = content.replace("{image}", "<img src='cid:" + contentId + "'/>");
                helper.setText(content, true);
                helper.addInline(contentId,
                        new ByteArrayResource(request.inlineImage.getBytes()), request.inlineImage.getContentType());
            } else {
                helper.setText(content, true);
            }
        } else {
            helper.setText(request.message, false);
        }

        if (request.attachment != null && !request.attachment.isEmpty()) {
            helper.addAttachment(request.attachment.getOriginalFilename(),
                    new ByteArrayResource(request.attachment.getBytes()));
        }

        mailSender.send(message);
    }
}

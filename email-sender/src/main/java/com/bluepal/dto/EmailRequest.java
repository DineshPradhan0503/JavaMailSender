package com.bluepal.dto;

import org.springframework.web.multipart.MultipartFile;

public class EmailRequest {
	public String to;
    public String subject;
    public String message;
    public boolean html;
    public MultipartFile attachment;
    public MultipartFile inlineImage;
}

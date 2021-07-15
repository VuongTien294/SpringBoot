package com.trungtamjava.service.impl;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.trungtamjava.model.MailDTO;
import com.trungtamjava.service.MailService;

@Service
public class MailServiceIplm implements MailService{
	
	@Autowired
	JavaMailSender javaMailSender;
	
	public void sendEmail(MailDTO mailDTO) {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			 
            mimeMessageHelper.setSubject(mailDTO.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mailDTO.getMailFrom(), "Shop Giay TienDepTrai"));
            mimeMessageHelper.setTo(mailDTO.getMailTo());
            mimeMessageHelper.setText(mailDTO.getMailContent());
 
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
		} catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		
	}

}

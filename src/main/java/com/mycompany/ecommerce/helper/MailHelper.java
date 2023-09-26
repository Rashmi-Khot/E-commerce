package com.mycompany.ecommerce.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mycompany.ecommerce.dto.MerchantDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailHelper {
	@Autowired
	JavaMailSender mailSender;

	public void sendOtp(MerchantDto merchantDto) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

		try {
			mimeMessageHelper.setTo(merchantDto.getEmail());
			mimeMessageHelper.setFrom("E-Commerce");
			mimeMessageHelper.setSubject("OTP Verification");
			String body = "<h1 style='color:blue'>Hello " + merchantDto.getName() + ",<br>Your Otp is : "
					+ merchantDto.getOtp() + "</h1>";
			mimeMessageHelper.setText(body, true);
			mailSender.send(mimeMessage);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

package com.sample.email.controller;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.sample.email.model.MailModel;
import com.sample.email.service.MailService;

import freemarker.template.TemplateException;

@CrossOrigin(allowCredentials = "true", origins = "*", allowedHeaders = "*")
@RestController
public class SendEmailController {

	@Autowired
	private MailService mailService;

	/**
	 * 发送简单邮件
	 */
	@PostMapping("/sendSimpleMail")
	public ResponseEntity<?> sendSimpleMail(@RequestBody MailModel mailModel) {
		try {
			mailService.sendSimpleMail(mailModel);
			return ResponseEntity.ok().body(mailModel.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(e.getMessage());
		}
	}

	/**
	 * 发送复杂邮件（文本+图片+附件）
	 */
	@PostMapping("/sendMimeMail")
	public ResponseEntity<String> sendMimeMail(@RequestBody MailModel mailModel) {
		try {
			mailService.sendMimeMail(mailModel);
			return ResponseEntity.ok().body(mailModel.toString());
		} catch (MessagingException e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(e.getMessage());
		}
	}

	/**
	 * 发送模板邮件
	 *
	 * @param
	 * @return
	 */
	@PostMapping("/sendTemplateMail")
	public ResponseEntity<String> sendTemplateMail(@RequestBody MailModel mailModel) {
		try {
			Context context = new Context();
			context.setVariable("username", "发件人");
			return mailService.sendTemplateMail(mailModel, context);
		} catch (MessagingException e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(e.getMessage());
		}
	}
	
	/**
	 * freemarker 模板
	 * @param mailModel
	 * @return
	 */
	@PostMapping("/sendmail")
	public ResponseEntity<?> restPostLoanRequest(@RequestBody MailModel mailModel) {
		try {
			mailService.sendEmail(mailModel);
			return ResponseEntity.ok().body(mailModel.toString());
		} catch (MessagingException e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(e.getMessage());
		} catch (TemplateException e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(e.getMessage());
		}
	}
}

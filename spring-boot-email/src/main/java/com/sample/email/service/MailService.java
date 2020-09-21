package com.sample.email.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sample.email.model.MailModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;
    
    @Autowired
    @Qualifier("emailConfigBean")
    private Configuration emailConfig;

    /**
     * 发送简单邮件
     *
     * @param from
     * @param to
     * @param subject
     * @param text
     */
    public void sendSimpleMail(MailModel mailModel) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 发件人
        simpleMailMessage.setFrom(mailModel.getFrom());
        // 收件人
        simpleMailMessage.setTo(mailModel.getTo());
        // 邮件主题
        simpleMailMessage.setSubject(mailModel.getSubject());
        // 邮件内容
        simpleMailMessage.setText(mailModel.getContent());
        javaMailSender.send(simpleMailMessage);
    }

    /**
     * 发送复杂邮件
     *
     * @param from
     * @param to
     * @param subject
     * @param text
     * @return
     * @throws MessagingException 
     */
    public void sendMimeMail(MailModel mailModel) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(mailModel.getFrom());
        helper.setTo(mailModel.getTo());
        helper.setSubject(mailModel.getSubject());
        // 设置邮件内容，第二个参数设置是否支持 text/html 类型
        helper.setText("<h3>文本内容</h3><br>" +
                "文本内容<br>" +
                "<img src='cid:logo'>", true);
        //这里是发送文件，读者可以在resources文件夹下自定义一个文件夹加入文件进行测试
        helper.addInline("logo", new ClassPathResource("logo.jpg"));
        helper.addAttachment("logo.jpg", new ClassPathResource("logo.jpg"));
        javaMailSender.send(mimeMessage);
    }

    /**
     * 发送模板邮件
     *
     * @param from
     * @param to
     * @param subject
     * @param context
     * @return
     * @throws MessagingException 
     */
    public ResponseEntity<String> sendTemplateMail(MailModel mailModel, Context context) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(mailModel.getFrom());
        helper.setTo(mailModel.getTo());
        helper.setSubject(mailModel.getSubject());
        // 解析邮件模板
        String text = templateEngine.process("mailTemplate", context);
        helper.setText(text, true);
        javaMailSender.send(message);
        return ResponseEntity.status(HttpStatus.CREATED).body("发送成功");
    }
    
    /**
     * freemarker模板
     * @param mailModel
     * @throws MessagingException
     * @throws IOException
     * @throws TemplateException
     */
    public void sendEmail(MailModel mailModel) throws MessagingException, IOException, TemplateException {

        Map<String, String> model = new HashMap<>();
        model.put("name", mailModel.getName());
        model.put("location", "china shanghai");
        model.put("signature", "");
        model.put("content", mailModel.getContent());

        /**
         * Add below line if you need to create a token to verification emails and uncomment line:32 in "email.ftl"
         * model.put("token",UUID.randomUUID().toString());
         */

        mailModel.setModel(model);

        Template template = emailConfig.getTemplate("email.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, mailModel.getModel());

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
        mimeMessageHelper.addInline("logo", new ClassPathResource("logo.jpg"));
        mimeMessageHelper.setTo(mailModel.getTo());
        mimeMessageHelper.setText(html, true);
        mimeMessageHelper.setSubject(mailModel.getSubject());
        mimeMessageHelper.setFrom(mailModel.getFrom());

        javaMailSender.send(message);
    }
}

package com.ssafy.camping.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService (JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendTempPasswordEmail(String code, String email) throws Exception {
        try {
            MimeMessage mimeMessage = createTempPasswordMessage(code, email);
            javaMailSender.send(mimeMessage);
        } catch (MailException mailException) {
            mailException.printStackTrace();
            throw new IllegalAccessException();
        }
    }

    private MimeMessage createTempPasswordMessage(String tempPassword, String email) throws Exception {

        MimeMessage message = javaMailSender.createMimeMessage();

        message.addRecipients(Message.RecipientType.TO, email);
        message.setSubject("Camping 임시비밀번호가 발급되었습니다.");
        message.setText("임시 비밀번호 : " + tempPassword);
        message.setFrom("tjdfkr011@naver.com");

        return message;
    }


}